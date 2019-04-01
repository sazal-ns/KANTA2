package com.brosolved.siddiqui.kanta.utils;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

/*
 * com.brosolved.siddiqui.kanta.utils is created by Noor Nabiul Alam Siddiqui on 2/16/2019
 *
 * BroSolved (c) 2019.
 */
public class CommonTask {

    private static AlertDialog alertDialog;

    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static boolean checkInput(String input, int length){
        boolean isOk = true;

        if (input.isEmpty() || input.length() != length)
            isOk = false;

        return isOk;
    }

    public static boolean checkInput(String input, EditText editText){
        boolean isOk = true;

        if (input.isEmpty()) {
            isOk = false;
            editText.setError("required");
        }else editText.setError(null);

        return isOk;
    }

    public static boolean checkInput(String input){
        boolean isOk = true;

        if (input.isEmpty())
            isOk = false;

        return isOk;
    }

    public static  void dialogShow(Context context, String message){
        alertDialog = new AlertDialog.Builder(context)
                .setMessage(message)
                .setCancelable(true)

                .show();
    }

    public static void dialogDistroy(){
        alertDialog.dismiss();
    }
}
