/*
 * Created By Noor Nabiul Alam Siddiqui on 12/11/17 6:11 PM
 * fb.com/sazal.ns
 *
 * Last modified 12/11/17 6:11 PM
 */

package com.brosolved.siddiqui.kanta.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

/**
 * Created by Noor Nabiul Alam Siddiqui on 12/11/2017.
 * siddiqui.sazal@gmail.com
 */

public class RuntimePermissionHandler {
    private Activity activity;
    private RuntimePermissionListener runtimePermissionListener;
    private int requestCode;

    public RuntimePermissionHandler(Activity activity, @NonNull String[] permissions, int requestCode, RuntimePermissionListener listener) {
        this.activity = activity;
        this.runtimePermissionListener = listener;
        this.requestCode = requestCode;

        if (!needRequestRuntimePermissions()){
            runtimePermissionListener.onAllow();
            return;
        }

        requestToGrantPermissions(permissions, requestCode);
    }

    private boolean needRequestRuntimePermissions() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    private void requestToGrantPermissions(String[] permissions, int requestCode) {
        String[] unGrantedPermissions = findUnGrantPermissions(permissions);

        if (unGrantedPermissions.length == 0)
            runtimePermissionListener.onAllow();
        else ActivityCompat.requestPermissions(activity, unGrantedPermissions, requestCode);

    }

    private String[] findUnGrantPermissions(String[] permissions) {
        List<String> unGrantPermissionList = new ArrayList<>();

        for (String permission: permissions) if (!isPermissionGranted(permission)) unGrantPermissionList.add(permission);

        return unGrantPermissionList.toArray(new String[0]);
    }

    private boolean isPermissionGranted(String permission) {
        return ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if (this.requestCode == requestCode){
            if (grantResults.length > 0){
                for (int grantResult : grantResults){
                    if (grantResult == PackageManager.PERMISSION_DENIED){
                        runtimePermissionListener.onDeny();
                        return;
                    }
                }
                runtimePermissionListener.onAllow();
            }else runtimePermissionListener.onDeny();
        }
    }


    public interface RuntimePermissionListener{
        void onAllow();
        void onDeny();
    }
}
