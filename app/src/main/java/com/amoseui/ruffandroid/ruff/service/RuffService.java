package com.amoseui.ruffandroid.ruff.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.amoseui.ruffandroid.IRuffAidlInterface;

public class RuffService extends Service {

    private final IRuffAidlInterface.Stub mBinder =
            new IRuffAidlInterface.Stub() {
        @Override
        public int getSum(int a, int b) throws RemoteException {
            return a + b;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
