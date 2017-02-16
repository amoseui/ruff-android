package com.amoseui.ruffandroid.ruff.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amoseui.ruffandroid.IRuffAidlInterface;
import com.amoseui.ruffandroid.R;

public class RuffServicePreference extends Preference {

    private Button mUpdateButton;

    private TextView mUpdateTextView;

    private IRuffAidlInterface mIRuffAidl;

    private View.OnClickListener mOnClickLister = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            updateFromService();
        }
    };

    public RuffServicePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutResource(R.layout.service_layout);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        mUpdateTextView = (TextView)
                holder.itemView.findViewById(R.id.service_update_text);
        mUpdateButton = (Button)
                holder.itemView.findViewById(R.id.service_update_button);
        mUpdateButton.setOnClickListener(mOnClickLister);
    }

    private void updateFromService() {
        Intent intent = new Intent(getContext(), RuffService.class);
        getContext().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIRuffAidl = IRuffAidlInterface.Stub.asInterface(service);
            try {
                int sum = mIRuffAidl.getSum(1, 2);
                mUpdateTextView.setText(Integer.toString(sum));
            } catch (RemoteException e) {
                mUpdateTextView.setText("error");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIRuffAidl = null;
        }
    };
}
