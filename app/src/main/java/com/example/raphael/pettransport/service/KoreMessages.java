package com.example.raphael.pettransport.service;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;

public class KoreMessages {
    public static void showMessage(Context context, String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
