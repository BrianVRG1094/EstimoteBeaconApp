package com.androidfinal.brian.beaconapp;

import android.annotation.TargetApi;
import android.app.Application;

/**
 * Created by Brian on 6/12/2016.
 */
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import java.util.List;
import java.util.UUID;

public class BeaconMonitoringApplication extends Application{

    private BeaconManager manager;
    private SharedPreferences preferences;

    @Override
    public void onCreate(){
        super.onCreate();

        manager = new BeaconManager(getApplicationContext());
        preferences = this.getSharedPreferences("BeaconSettings", Context.MODE_PRIVATE);
        final MainActivity ma = new MainActivity();

        manager.setBackgroundScanPeriod(5000, 2000);

        manager.setMonitoringListener(new BeaconManager.MonitoringListener(){
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list){
                //check which beacon region is passed here and get current setting
                String setting = preferences.getString(ma.getPreferenceName(region.getMajor()),
                        "No setting found");
                if(!setting.equals("No Setting")){
                    String title = "Region Entered";
                    String message = "Do you want to open ";
                    showNotification(title, message, preferences.getString(ma.getPreferenceName(region.getMajor()),
                            "No setting found"), region.getMajor());
                }
            }

            @Override
            public void onExitedRegion(Region region) {
                //ToDo: Maybe remove notification from list?
            }
        });

        manager.connect(new BeaconManager.ServiceReadyCallback(){
            @Override
            public void onServiceReady(){
                manager.startMonitoring(new Region(
                        "Ice Beacon Region",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                        45089, 53500
                ));

                manager.startMonitoring(new Region(
                        "Mint Beacon Region",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                        65159, 25684
                ));

                manager.startMonitoring(new Region(
                        "Blueberry Beacon Region",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                        25338, 17103
                ));
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void showNotification(String title, String message, String appName, int notificationID) {
        //Create intent for notification and set properties
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        //Create pending intent
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);

        //Create new notification and set properties
        StringBuilder sb = new StringBuilder(message);
        sb.append(appName);
        sb.append("?");
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(sb.toString())
                .setAutoCancel(false)
                .setContentIntent(pendingIntent);

        //Check notificationID (which is beacon.Major) and setIcon to correct image
        if(notificationID == 25338){
            //Blueberyy
            builder.setSmallIcon(R.drawable.purplebeacon);
        }
        else if(notificationID == 65159){
            //Mint
            builder.setSmallIcon(R.drawable.greenbeacon);
        }
        else if(notificationID == 45089){
            //Ice
            builder.setSmallIcon(R.drawable.bluebeacon);
        }

        //Add actions to the notification
        //Yes intent
        Intent yesReceive = new Intent();
        yesReceive.setAction(AppConstant.YES_ACTION);
        yesReceive.putExtra("appName", appName);
        PendingIntent pendingIntentYes = PendingIntent.getBroadcast(this, 12345, yesReceive,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Action action = new Notification.Action(android.R.drawable
                .button_onoff_indicator_on,
                "Yes",
                pendingIntentYes);
        builder.addAction(action);

        //No intent
        Intent noReceive = new Intent();
        noReceive.setAction(AppConstant.NO_ACTION);
        noReceive.putExtra("notificationID", notificationID);
        PendingIntent pendingIntentNo = PendingIntent.getBroadcast(this, 12345, noReceive,
                PendingIntent.FLAG_UPDATE_CURRENT);
        action = new Notification.Action(android.R.drawable.button_onoff_indicator_off, "No",
                pendingIntentNo);
        builder.addAction(action);

        Notification notification = builder.build();

        //Set notification sound and noClear flag
        notification.defaults = Notification.DEFAULT_SOUND;
        notification.priority = Notification.PRIORITY_MAX;

        //Pass to notificationManager to show
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID, notification);
    }
}
