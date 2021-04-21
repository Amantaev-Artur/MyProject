package com.example.a123;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a123.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;

public class Options extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.options);

        Preference btn_exit = findPreference(getString(R.string.exit));
        btn_exit.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(getApplicationContext(),"Заходите снова", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            }
        });

        Preference btn_exit_account = findPreference(getString(R.string.exit_account));
        btn_exit_account.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(getApplicationContext(),"Вы вышли из аккаунта", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Options.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
        });

        Load_setting();
    }

    private void Load_setting() {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        boolean chk_night = sp.getBoolean("NIGHT", false);
        if (chk_night) {
            getListView().setBackgroundColor(Color.parseColor("#3d3d3d"));
        } else {
            getListView().setBackgroundColor(Color.parseColor("#ffffff"));
        }


        CheckBoxPreference chk_night_instant = (CheckBoxPreference) findPreference("NIGHT");
        chk_night_instant.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(android.preference.Preference prefs, Object obj) {

                boolean yes = (boolean) obj;

                if (yes) {
                    getListView().setBackgroundColor(Color.parseColor("#3d3d3d"));
                } else {
                    getListView().setBackgroundColor(Color.parseColor("#ffffff"));
                }

                return true;
            }
        });


        ListPreference LP = (ListPreference) findPreference("ORIENTATION");

        String orien = sp.getString("ORIENTATION", "false");
        if ("1".equals(orien)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
            LP.setSummary(LP.getEntry());
        } else if ("2".equals(orien)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            LP.setSummary(LP.getEntry());
        } else if ("3".equals(orien)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            LP.setSummary(LP.getEntry());
        }

        LP.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(android.preference.Preference prefs, Object obj) {

                String items = (String) obj;
                if (prefs.getKey().equals("ORIENTATION")) {
                    switch (items) {
                        case "1":
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
                            break;
                        case "2":
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            break;
                        case "3":
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            break;
                    }

                    ListPreference LPP = (ListPreference) prefs;
                    LPP.setSummary(LPP.getEntries()[LPP.findIndexOfValue(items)]);

                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        Load_setting();
        super.onResume();
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Options.this, Activity.class);
        startActivity(intent);
        finish();
    }
}
