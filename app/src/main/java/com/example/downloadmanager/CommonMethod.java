package com.example.downloadmanager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.os.Build;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.util.UUID;


public class CommonMethod extends BroadcastReceiver {
    public static final int STORAGE_PERMISSION_CODE = 1;
    public String getFilename() {
        final File source = new File(Environment.getDataDirectory().getAbsolutePath(), "Pictures/D2K/");
        if (!source.exists()) {
            source.mkdirs();
        }
        return source+ UUID.randomUUID().toString() + System.currentTimeMillis();
    }


    @Override
    public void onReceive(Context context, Intent intent) {

    }
    private static String LOGTAG = CommonMethod.class.getSimpleName();

    public static void log(String TAG, String message) {
        if (CommonMethod.isLibraryLogsDebuggable()) {
            Log.e(TAG, message);
        }
    }
    public static boolean isLibraryLogsDebuggable() {
        boolean appDebuggable = true;
        return appDebuggable;
        /*     //For grant permission
        mRequestPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "You have already granted permission!", Toast.LENGTH_SHORT).show();
                } else {
                    requestStoragePermission();
                }

            }
        });*/
    }
  /*  public void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder( com.example.downloadmanager.MainActivity.class)
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
    }*/

}
