package com.example.downloadmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    public static final int PERMISSION_STORAGE_CODE =1000 ;
    public static final int STORAGE_PERMISSION_CODE = 1;
    public static final String FOLDER_NAME = "Temp";
    public static final String ID = SimpleDateFormat.getDateInstance().toString();
    private long downloadIdReceivedFromDownloadManager;
    Button mDownloadBtn, mRequestPermission;
    EditText mUrlEt;
    DownloadFile mydownload = new DownloadFile();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDownloadBtn = findViewById(R.id.btn_download);
        mUrlEt = findViewById(R.id.et_url);
        mRequestPermission = findViewById(R.id.btn_requestPermission);
        mRequestPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(MainActivity.this, "You have already granted permission!", Toast.LENGTH_SHORT).show();
                }else {
                    requestStoragePermission();
                }

            }
        });


        mDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //If OS is mashmellow or above than  that please handle it
                if(Build.VERSION.SDK_INT >Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        //Permission denied, request it
                        String [] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

                        //Show popup for runtime permission
                        requestPermissions(permissions,PERMISSION_STORAGE_CODE);

                    }else {
                        //Permission already granted it then perform download
                       startDownloading(mUrlEt);
                    }
                }else{
                    //OS is less than the marshmellow then perform download
                    startDownloading(mUrlEt);
                }

            }
        });
    }

    public  void requestStoragePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("This permission is needed")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,new String []{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }else {
            ActivityCompat.requestPermissions(this,new String []{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_STORAGE_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Permission granted from popup,perfrom downloading
                    startDownloading(mUrlEt);
                }else{
                    //Permission denied from popup, show error message
                    Toast.makeText(this, "Permisssion denied......", Toast.LENGTH_SHORT).show();
                }
            }
            case STORAGE_PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
                }else{
                    //Permission denied from popup, show error message
                    Toast.makeText(this, "Permisssion denied......", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void startDownloading(EditText ed_url) {
        //Get url/text from edit text
        String url = ed_url.getText().toString().trim();

        //Create Download Request
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //Allow types of network to download files
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Download"); //Set title in download notification
        request.setDescription("Downloading file....."); //Set description in download notification
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

//        request.setDestinationInExternalPublicDir( Environment.DIRECTORY_DOWNLOADS, File.separator +FOLDER_NAME + File.separator + "data.pdf");

        //Create folder and pass that path for downloading
        File TEST = new File(Environment.getDataDirectory(), "TEST");
        TEST.mkdir(); // make directory
        String path = TEST.getAbsolutePath(); // get absolute path

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,path);

        //get download service and enque file
        DownloadManager manager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
       manager.enqueue(request);
        //CommonMethod.log(TAG, "Download id " + downloadIdReceivedFromDownloadManager);

    }


}