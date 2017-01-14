package com.amoseui.ruffandroid.ruff;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.amoseui.ruffandroid.R;

import static com.amoseui.ruffandroid.ruff.RuffConstants.INTENT_FILTER_ACTION;


public class RuffDescriptionPreferenceCompat extends Preference {

    private boolean mIsNoItem;
    private Button mDownloadButton;
    private View.OnClickListener mOnClickLister = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent newIntent = new Intent();
            newIntent.setAction(INTENT_FILTER_ACTION);
            newIntent.putExtra(RuffConstants.EXTRA_PACKAGE_NAME, "New Application");
            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(newIntent);
        }
    };

    public RuffDescriptionPreferenceCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutResource(R.layout.description_no_item_layout);
        mIsNoItem = true;
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        setLayoutResource(mIsNoItem
                ? R.layout.description_no_item_layout
                : R.layout.description_item_layout);
        mDownloadButton = (Button) holder.itemView.findViewById(R.id.download_button);
        mDownloadButton.setOnClickListener(mOnClickLister);
    }

    public void setNoItemLayout(boolean isNoItem) {
        mIsNoItem = isNoItem;
        notifyChanged();
    }
}
