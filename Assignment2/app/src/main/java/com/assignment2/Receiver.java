package com.assignment2;

//Renrui Liu 216166456, SIT207 assignment2.

/*
This is a BroadcastReceiver. When it received an alarm, it will send a notification on top.
 *  */

/*
References:

Android notification at specific date
Wajdi Hh
http://stackoverflow.com/questions/9930683/android-notification-at-specific-date
* */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Toast.makeText(context, intent.getStringExtra("Toast"),Toast.LENGTH_SHORT).show();
        createNotification(context);
    }

    //create a notification after receive the alarm
    private void createNotification(Context context) {

        PendingIntent notificIntent = PendingIntent.getActivity(context,0,new Intent(context,MainActivity.class),0);
        NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(context)
                .setContentTitle("Injection date")
                .setContentText("It's time to inject")
                .setTicker("ticker")
                .setSmallIcon(R.mipmap.drop_icon);

        mBuilder.setContentIntent(notificIntent);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager=
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1,mBuilder.build());
    }

}
