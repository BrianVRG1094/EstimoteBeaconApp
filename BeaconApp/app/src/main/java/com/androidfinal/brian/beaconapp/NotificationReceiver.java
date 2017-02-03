package com.androidfinal.brian.beaconapp;

import android.app.Application;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

/**
 * Created by Brian on 6/12/2016.
 */

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (AppConstant.YES_ACTION.equals(action)) {
            //Get app packageName
            String appName = intent.getExtras().getString("appName");
            String packageName = GetPackageName(appName);

            //Check if packageName was not found
            if(packageName == null){
                Toast.makeText(context, "Unable to open app", Toast.LENGTH_SHORT).show();
            }

            else {
                //Create intent for packageName
                Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);

                //Remove notification
                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancelAll();

                //Close notification drawer
                Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
                context.sendBroadcast(it);

                //Open app
                context.startActivity(launchIntent);
            }
        }

        else  if (AppConstant.NO_ACTION.equals(action)) {
            //Remove notification using notificationID from intent extras
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancelAll();

            //Close notification drawer
            Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            context.sendBroadcast(it);
        }
    }

    //This method will return the package name of an app when given the app name (the name the user sees on their
    // device)
    public String GetPackageName(String appName){
        switch (appName){
            case "Spotify":
                return "com.spotify.music";
            case "Google Maps":
                return "com.google.android.apps.maps";
            case "Twitter":
                return "com.twitter.android";
            case "Starbucks":
                return "com.starbucks.mobilecard";
            case "Snapchat":
                return "com.snapchat.android";
        }
        return null;
    }
}
