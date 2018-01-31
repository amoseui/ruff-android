package com.amoseui.ruffandroid.ruff.notification;

import android.content.Context;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.amoseui.ruffandroid.R;

public class RuffNotificationPreference extends Preference {

    private Context mContext;
    private int mProgress = 0;

    private View.OnClickListener mStartOnClickLister = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startService();
        }
    };

    private View.OnClickListener mUpdateOnClickLister = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            updateService();
        }
    };

    public RuffNotificationPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        RuffNotificationManager.getInstance().setContext(mContext);
        setLayoutResource(R.layout.notification_layout);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        Button startButton =
                holder.itemView.findViewById(R.id.notification_start_button);
        Button updateButton =
                holder.itemView.findViewById(R.id.notification_update_button);
        startButton.setOnClickListener(mStartOnClickLister);
        updateButton.setOnClickListener(mUpdateOnClickLister);
    }

    private void startService() {
        RuffNotificationManager.getInstance().bindService(mContext.getApplicationContext());
    }

    private void updateService() {
        mProgress += 20;
        RuffNotificationManager.getInstance().update(mContext.getApplicationContext(), mProgress);
    }
}
