package com.example.whatsappstorysaver.Utils;

import android.os.Build;
import android.os.Environment;

import java.io.File;

public class MyConstants {

    // new File(File.separator+"WhatsApp/Media/.Statuses");
    //       new File(Environment.getExternalStorageDirectory() + File.separator+"Android/Media/com.whatsapp/WhatsApp/.Statuses");
    //      new File(Environment.getExternalStorageDirectory()+File.separator+"Android/Media/com.whatsapp/WhatsApp/.Statuses");


    public static final File STATUS_DIRECTORY = Build.VERSION.SDK_INT < 30 ?
            new File(Environment.getExternalStorageDirectory() + File.separator + "WhatsApp/Media/.Statuses") :
            new File(Environment.getExternalStorageDirectory() + File.separator +
                    "Android/media/com.whatsapp/WhatsApp/Media/.Statuses");


    public static final File APP_DIR = new File(Environment.getExternalStorageDirectory() + File.separator + "WhatsappStatusSaver");
    public static final int THUMBSIZE = 128;
    public static int ImagePosition = 0;
    public static int VideoPosition = 0;
}
