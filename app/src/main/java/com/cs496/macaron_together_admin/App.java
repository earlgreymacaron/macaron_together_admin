package com.cs496.macaron_together_admin;

import android.app.Application;
import android.graphics.Typeface;


/**
 * Created by q on 2016-12-30.
 */

public class App extends Application {

    public static Typeface myFont;


    @Override
    public void onCreate() {

        myFont = Typeface.createFromAsset(getAssets(), "fonts/hongcha.ttf");
        super.onCreate();

    }


}
