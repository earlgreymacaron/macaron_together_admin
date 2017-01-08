package com.cs496.macaron_together_admin;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;

import java.util.List;

public class ServiceMonitor extends Service {

    /**
     * {@link Log#isLoggable(String, int)}
     * <p>
     * IllegalArgumentException is thrown if the tag.length() > 23.
     */
    private static final String TAG = "NotifiCollectorMonitor";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() called");

        ComponentName cc = new ComponentName(this, NotificationListener.class);
        Log.v(TAG, "checkListening collectorComponent: " + cc);
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        boolean listening = false;
        List<ActivityManager.RunningServiceInfo> runningServices
                = manager.getRunningServices(Integer.MAX_VALUE);
        if (runningServices == null ) {
            Log.w(TAG, "checkListening runningServices is NULL");
            return;
        }
        for (ActivityManager.RunningServiceInfo service : runningServices) {
            if (service.service.equals(cc)) {
                Log.w(TAG, "checkListening service - pid: " + service.pid +
                        ", currentPID: " + Process.myPid() +
                        ", clientPackage: " + service.clientPackage +
                        ", clientCount: " + service.clientCount
                        + ", clientLabel: " + ((service.clientLabel == 0) ? "0" : "(" + getResources().getString(service.clientLabel) + ")"));
                if (service.pid == Process.myPid() /*&& service.clientCount > 0 && !TextUtils.isEmpty(service.clientPackage)*/) {
                    listening = true;
                }
            }
        }

        if (listening) {return;}

        Log.d(TAG, "reset Listener");
        ComponentName thisComponent = new ComponentName(this, NotificationListener.class);
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(thisComponent,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        pm.setComponentEnabledSetting(thisComponent,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}