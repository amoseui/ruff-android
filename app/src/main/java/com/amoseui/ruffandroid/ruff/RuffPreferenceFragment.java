package com.amoseui.ruffandroid.ruff;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.v4.content.LocalBroadcastManager;
import android.view.MenuItem;

import com.amoseui.ruffandroid.R;
import com.amoseui.ruffandroid.SettingsActivity;

import java.util.ArrayList;

public class RuffPreferenceFragment extends PreferenceFragment {

    private RuffDescriptionPreference mDescriptionPreference;
    private ArrayList<String> mPackageList = new ArrayList<>();
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String packageName = intent.getStringExtra(RuffConstants.EXTRA_PACKAGE_NAME);
            mPackageList.add(packageName);
            updatePreferenceScreen();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_ruff);
        mDescriptionPreference =
                (RuffDescriptionPreference) findPreference("description_preference");
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterRceiver();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updatePreferenceScreen() {
        getPreferenceScreen().removeAll();

        getPreferenceScreen().addPreference(mDescriptionPreference);
        if (mPackageList.size() == 0) {
            mDescriptionPreference.setNoItemLayout(true);
            return;
        }
        mDescriptionPreference.setNoItemLayout(false);
        for (String packageName : mPackageList) {
            SwitchPreference preference = new SwitchPreference(getActivity());
            preference.setTitle(packageName);
            getPreferenceScreen().addPreference(preference);
        }
    }

    private void registerReceiver() {
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(mReceiver, new IntentFilter(RuffConstants.INTENT_FILTER_ACTION));
    }

    private void unregisterRceiver() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mReceiver);
    }
}
