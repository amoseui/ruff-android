package com.amoseui.ruffandroid.ruff.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

public class RuffNotificationManager {

    private static final String TAG = "RuffNotificationManager";

    private static RuffNotificationManager sInstance;
    private RuffNotificationService mBoundService;
    private boolean mIsBound;
    private Context mContext;

    private RuffNotificationManager() {
    }

    public static synchronized RuffNotificationManager getInstance(){
        if (sInstance == null){
            sInstance = new RuffNotificationManager();
        }
        return sInstance;
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            mBoundService = ((RuffNotificationService.LocalBinder) service).getService();

            Intent intent = new Intent(mContext, RuffNotificationService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mBoundService.startForegroundService(intent);
            } else {
                mBoundService.startService(intent);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBoundService = null;
        }
    };

    public void setContext(Context context) {
        mContext = context.getApplicationContext();
    }

    public void bindService(Context context) {
        if (mIsBound) {
            Log.d(TAG, "bindService already bounded");
            return;
        }
        Log.d(TAG, "bindService go");
        Intent intent = new Intent(context, RuffNotificationService.class);
        context.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    public void update(Context context, int progress) {
        NotificationManager nm = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = mBoundService.progressNotification(progress);
        nm.notify(RuffNotificationService.NOTIFICATION_ID, notification);
    }

    public void unbindService(Context context) {
        context.unbindService(mConnection);
    }
}
