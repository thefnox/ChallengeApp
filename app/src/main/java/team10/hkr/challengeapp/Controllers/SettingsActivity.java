package team10.hkr.challengeapp.Controllers;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import team10.hkr.challengeapp.R;

/**
 * Created by Charlie on 7.05.2017.
 */

public class SettingsActivity extends PreferenceActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings_main_preferences);
    }
}