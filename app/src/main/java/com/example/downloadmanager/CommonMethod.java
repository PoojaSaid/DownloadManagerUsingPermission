package com.example.downloadmanager;

import android.os.Environment;
import android.util.Log;
import android.os.Build;

import java.io.File;
import java.util.UUID;


public class CommonMethod {
    public String getFilename() {
        final File source = new File(Environment.getDataDirectory().getAbsolutePath(), "Pictures/D2K/");
        if (!source.exists()) {
            source.mkdirs();
        }
        return source+ UUID.randomUUID().toString() + System.currentTimeMillis();
    }
}
