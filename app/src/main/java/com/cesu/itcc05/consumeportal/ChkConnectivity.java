package com.cesu.itcc05.consumeportal;

import android.app.Application;

/**
 * Created by ITCC05 on 26-09-2019.
 */
public class ChkConnectivity extends Application {

    // Gloabl declaration of variable to use in whole app

    public static boolean activityVisible; // Variable that will check the
    // current activity state

    public static boolean isActivityVisible() {
        return activityVisible; // return true or false
    }

    public static void activityResumed() {
        activityVisible = true;// this will set true when activity resumed

    }

    public static void activityPaused() {
        activityVisible = false;// this will set false when activity paused

    }

}