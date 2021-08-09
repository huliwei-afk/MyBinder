package com.example.mybinder;

import android.app.ActivityManager;
import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {
    private static final String TAG = "TAG";

    @Override
    public void onCreate() {
        super.onCreate();
        int pid = android.os.Process.myPid();
        Log.i(TAG, "MyApplication is onCreate====" + "pid = " + pid);
        String processNameString = " ";
        ActivityManager activityManager = (ActivityManager)this.getSystemService(getApplicationContext().ACTIVITY_SERVICE);
        for(ActivityManager.RunningAppProcessInfo appProcessInfo : activityManager.getRunningAppProcesses()){
            if(appProcessInfo.pid == pid){
                processNameString = appProcessInfo.processName;
            }
        }
        if("com.example.mybinder".equals(processNameString)){
            Log.i(TAG, "processName = " + processNameString + "----work");
        }else{
            Log.i(TAG, "processName = " + processNameString + "----work");
        }
    }
}
