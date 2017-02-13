package com.amoseui.ruffandroid.ruff.service;

import android.content.Context;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amoseui.ruffandroid.R;

public class RuffServicePreference extends Preference {

    private Button mUpdateButton;

    private TextView mUpdateTextView;

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
        mUpdateTextView.setText("updated");
    }
}
