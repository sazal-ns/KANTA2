package com.brosolved.siddiqui.kanta.utils;

import android.content.Context;
import android.widget.Toast;

/*
 * com.brosolved.siddiqui.kanta.utils is created by Noor Nabiul Alam Siddiqui on 2/16/2019
 *
 * BroSolved (c) 2019.
 */
public class CommonTask {

    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static boolean checkInput(String input, int length){
        boolean isOk = true;

        if (input.isEmpty() || input.length() != length)
            isOk = false;

        return isOk;
    }
}
