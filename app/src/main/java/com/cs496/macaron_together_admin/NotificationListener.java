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

    private static final String TAG = "NotificationListener";
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate() called");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind...");
        return super.onBind(intent);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (sbn == null) return;


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
