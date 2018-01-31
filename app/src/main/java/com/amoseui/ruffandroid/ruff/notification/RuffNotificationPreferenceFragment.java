package com.amoseui.ruffandroid.ruff.notification;

import android.os.Bundle;
import android.support.v14.preference.PreferenceFragment;

import com.amoseui.ruffandroid.R;

public class RuffNotificationPreferenceFragment extends PreferenceFragment {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref_ruff_notification, rootKey);
    }
}
