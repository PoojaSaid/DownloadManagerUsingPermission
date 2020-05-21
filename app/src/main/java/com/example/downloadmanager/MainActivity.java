package com.example.downloadmanager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity  {

    public static final int PERMISSION_EXTERNALSTORAGE_CODE = 1000;
    public static final int PERMISSION_INTERNALSTORAGE_CODE = 1001;
    public static final int STORAGE_PERMISSION_CODE = 1;
    Button mDownloadExternalBtn, mRequestPermission;
    EditText mUrlEt;
    private long downloadID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDownloadExternalBtn = findViewById(R.id.btn_download);

        mUrlEt = findViewById(R.id.et_url);
        mRequestPermission = findViewById(R.id.btn_requestPermission);


        //For grant permission
        mRequestPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "You have already granted permission!", Toast.LENGTH_SHORT).show();
                } else {
                    requestStoragePermission();
                }

            }
        });
//        registerReceiver(onDownloadComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        mDownloadExternalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        //Permission denied, request it
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

                        //Show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_EXTERNALSTORAGE_CODE);

                    } else {
                        //Permission already granted it then perform download
                        startDownloadingInExternal(mUrlEt);
                    }
                } else {
                    //OS is less than the marshmellow then perform download
                    startDownloadingInExternal(mUrlEt);
                }

            }
        });

    }

    public void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("This permission is needed")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_EXTERNALSTORAGE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownloadingInExternal(mUrlEt);
                } else {
                    //Permission denied from popup, show error message
                    Toast.makeText(this, "Permisssion denied......", Toast.LENGTH_SHORT).show();
                }
            }
           /* case PERMISSION_INTERNALSTORAGE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownloadingInInternal(mUrlEt);
                } else {
                    //Permission denied from popup, show error message
                    Toast.makeText(this, "Permisssion denied......", Toast.LENGTH_SHORT).show();
                }
            }*/
            case STORAGE_PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
                } else {
                    //Permission denied from popup, show error message
                    Toast.makeText(this, "Permisssion denied......", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void startDownloadingInExternal(EditText ed_url) {

        String url = ed_url.getText().toString().trim();

        if (url.length() > 0) {
            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

            String sourcePath = Environment.getDataDirectory().getAbsolutePath();
            final File source  = new File(sourcePath,"Test");
            //File TEST = new File(Environment.getDataDirectory().getAbsolutePath(), "TEST");
            source.mkdir(); // make directory
            String path = source.getAbsolutePath(); // get absolute path

             final File destination = new File(Environment.getDownloadCacheDirectory().getAbsoluteFile(), "DownloadManager");
            try {
                if (manager != null) {
                    final DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                            .setTitle("Download") //Set title in download notification
                            .setDescription("Downloading file.....")
                            .setAllowedOverRoaming(true)//Set description in download notification
                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                            .setMimeType(getMimeType(Uri.parse(url)))
                            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, path);
                    manager.enqueue(request);
                    try{
                        copy(source,destination);
                    }catch(Exception e){
                        e.getMessage();
                    }
                    /*copy(source,destination);
                  registerReceiver(new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                            //Checking if the received broadcast is for our enqueued download by matching download id
                            if (downloadID == id) {

                                try {
                                    source.getPath();
                                    FileUtils.copy(new FileInputStream(source), new FileOutputStream(destination));
                                    copy(source,destination);
                                    copy(source,destination);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(MainActivity.this, "Download Completed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
*/
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
            } catch (Exception e) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.e("ERROR:MAIN", "E: " + e.getMessage());
            }
        } else {
            Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
        }

    }

  /*  public void startDownloadingInInternal(EditText ed_url) {

        String url = ed_url.getText().toString().trim();

        if (url.length() == 0) {
            Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show();

        } else {
            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            File folder = new File(Environment.getDownloadCacheDirectory() + File.separator + "DownloadManager");
            try {
                if (manager != null) {
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                            .setTitle("Download") //Set title in download notification
                            .setDescription("Downloading file.....")
                            .setAllowedOverRoaming(true)//Set description in download notification
                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                            .setMimeType(getMimeType(Uri.parse(url)));

                    request.setDestinationInExternalPublicDir(Environment.getExternalStorageState(), String.valueOf(folder));
                    manager.enqueue(request);
                    Toast.makeText(this, "Download started", Toast.LENGTH_SHORT).show();

                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
            } catch (Exception e) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.e("ERROR:MAIN", "E: " + e.getMessage());
            }
        }

    }*/

    public BroadcastReceiver onDownloadComplete = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadID == id) {
                Toast.makeText(MainActivity.this, "Download Completed", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private String getMimeType(Uri uri) {
        ContentResolver resolver = getContentResolver();
        MimeTypeMap mimeTypemap = MimeTypeMap.getSingleton();
        return mimeTypemap.getExtensionFromMimeType(resolver.getType(uri));
    }

    public static void copy(File src, File dst) throws IOException {
        if (!dst.exists()) {
            dst.mkdirs();
        }
        if (!src.exists()) {
            throw new IllegalArgumentException("sourceDir does not exist");
        }
        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }
}