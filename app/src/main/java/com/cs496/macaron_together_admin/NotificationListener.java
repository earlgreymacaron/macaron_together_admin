package com.cs496.macaron_together_admin;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by q on 2017-01-07.
 */
public class NotificationListener extends NotificationListenerService {

    private static final String TAG = "NotifiCollectorMonitor";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate() called");
        //ensureCollectorRunning();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind...");
        return super.onBind(intent);
    }
/*
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    private void ensureCollectorRunning() {
        ComponentName collectorComponent = new ComponentName(this, NotificationListenerService.class);
        Log.e(TAG, "ensureCollectorRunning collectorComponent: " + collectorComponent);
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        boolean collectorRunning = false;
        List<ActivityManager.RunningServiceInfo> runningServices = manager.getRunningServices(Integer.MAX_VALUE);
        if (runningServices == null ) {
            Log.w(TAG, "ensureCollectorRunning() runningServices is NULL");
            return;
        }
        for (ActivityManager.RunningServiceInfo service : runningServices) {
            if (service.service.equals(collectorComponent)) {
                Log.e(TAG, "ensureCollectorRunning service - pid: " + service.pid + ", currentPID: " + android.os.Process.myPid() + ", clientPackage: " + service.clientPackage + ", clientCount: " + service.clientCount
                        + ", clientLabel: " + ((service.clientLabel == 0) ? "0" : "(" + getResources().getString(service.clientLabel) + ")"));
                if (service.pid == android.os.Process.myPid() /*&& service.clientCount > 0 && !TextUtils.isEmpty(service.clientPackage)) {
                    collectorRunning = true;
                }
            }
        }
        if (collectorRunning) {
            Log.e(TAG, "ensureCollectorRunning: collector is running");
            return;
        }
        Log.e(TAG, "ensureCollectorRunning: collector not running, reviving...");
        toggleNotificationListenerService();
    }


    private void toggleNotificationListenerService() {
        Log.e(TAG, "toggleNotificationListenerService() called");
        ComponentName thisComponent = new ComponentName(this, NotificationListenerService.class);
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(thisComponent, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        pm.setComponentEnabledSetting(thisComponent, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
  */

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (sbn == null) return;

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        Notification mNotification=sbn.getNotification();
        Bundle extras = mNotification.extras;
        String from=null;
        String text=null;

       // if(sbn.getPackageName().equalsIgnoreCase("com.wr.alrim")) {
            String alarm = sbn.getNotification().tickerText.toString();
            databaseReference.child("alarms").push().setValue(alarm);
            alarm = sbn.getNotification().actions.toString();
            databaseReference.child("alarms").push().setValue(alarm);
            //alarm = sbn.getNotification().bigContentView.toString();
            //databaseReference.child("alarms").push().setValue(alarm);
            //alarm = sbn.getNotification().contentView.toString();
            //databaseReference.child("alarms").push().setValue(alarm);
            alarm = sbn.getNotification().extras.toString();
            databaseReference.child("alarms").push().setValue(alarm);
            //alarm = sbn.getNotification().headsUpContentView.toString();
            //databaseReference.child("alarms").push().setValue(alarm);
        //}


        Log.e(TAG, "Notification Posted:\n");
        Log.e(TAG, "Notification=" );

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.e(TAG, "Notification Removed:\n");
    }


}
