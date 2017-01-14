package com.amoseui.ruffandroid.ruff;

import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.amoseui.ruffandroid.R;

import static com.amoseui.ruffandroid.ruff.RuffConstants.INTENT_FILTER_ACTION;

public class RuffDescriptionPreference extends Preference {

    private boolean mIsNoItem;
    private View mView;
    private View mParent;
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

    ViewTreeObserver mViewTreeObserver;
    ViewTreeObserver.OnPreDrawListener mOnPreDrawListener =
            new ViewTreeObserver.OnPreDrawListener() {
        @Override

        public boolean onPreDraw() {
            mViewTreeObserver.removeOnPreDrawListener(this);
            if (!mIsNoItem) return false;

            int padding = (mParent.getHeight() - mView.getHeight()) / 2;
            mView.setPadding(0, padding, 0, padding);
            return true;
        }
    };

    public RuffDescriptionPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutResource(R.layout.description_no_item_layout);
        mIsNoItem = true;
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        mParent = parent;
        mView = super.onCreateView(parent);
        mViewTreeObserver = mParent.getViewTreeObserver();
        mViewTreeObserver.addOnPreDrawListener(mOnPreDrawListener);
        mDownloadButton = (Button) mView.findViewById(R.id.download_button);
        mDownloadButton.setOnClickListener(mOnClickLister);
        return mView;
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        setLayoutResource(mIsNoItem
                ? R.layout.description_no_item_layout
                : R.layout.description_item_layout);
    }

    public void setNoItemLayout(boolean isNoItem) {
        mIsNoItem = isNoItem;
        notifyChanged();
    }
}
