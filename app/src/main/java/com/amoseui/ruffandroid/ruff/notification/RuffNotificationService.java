package com.amoseui.ruffandroid.ruff.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.amoseui.ruffandroid.R;

public class RuffNotificationService extends Service {

    private static final String TAG = "RuffNotificationService";
    public static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "RUFF_CHANNEL";

    NotificationManager mNotificationManager;

    public RuffNotificationService() {
        super();
    }

    public class LocalBinder extends Binder {
        RuffNotificationService getService() {
            return RuffNotificationService.this;
        }
    }
    private final IBinder mBinder = new LocalBinder();

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "ruff channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            mNotificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        startForeground(NOTIFICATION_ID, createNotification());
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        // Notification notification = createNotification();
        // mNotificationManager.notify(NOTIFICATION_ID, notification);
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d(TAG, "onTaskRemoved");
        super.onTaskRemoved(rootIntent);
    }

    public Notification createNotification() {
        return new NotificationCompat.Builder(
                getApplicationContext(), CHANNEL_ID)
                .setAutoCancel(false)
                .setContentTitle("START NOTIFICATION")
                .setContentText("It's ready.")
                .setLocalOnly(true)
                .setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .build();
    }

    public Notification progressNotification(int progress) {
        return new NotificationCompat.Builder(
                getApplicationContext(), CHANNEL_ID)
                .setAutoCancel(false)
                .setContentTitle("PROGRESS NOTIFICATION")
                .setContentText("It's in progress.")
                .setLocalOnly(true)
                .setOngoing(true)
                .setProgress(100, progress,false)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .build();
    }
}
