package com.cs496.macaron_together_admin;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;


/**
 * Created by q on 2016-12-30.
 */

public class App extends Application {
    public static Typeface myFont;
    @Override
    public void onCreate() {
        myFont = Typeface.createFromAsset(getAssets(), "fonts/pen.ttf");
        startService(new Intent(this, ServiceMonitor.class));
        super.onCreate();

    }


    public static boolean isContainedInNotificationListeners(Context context)
    {
        String enabledListeners = Settings.Secure.getString(context.getContentResolver(), "enabled_notification_listeners");
        Log.e("pakages enabled ",enabledListeners);
        Boolean b1 = !TextUtils.isEmpty(enabledListeners);
        Boolean b2 = enabledListeners.contains(context.getPackageName());
        return b1 && b2;
    }




}
