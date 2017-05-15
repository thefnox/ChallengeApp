package team10.hkr.challengeapp.Controllers.SettingsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import team10.hkr.challengeapp.R;

/**
 * Created by Charlie on 7.05.2017.
 */

public class SettingsActivityMainView extends PreferenceActivity {




    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_main_preferences);

        final Preference changeNamePref = findPreference("editNameButton");
        final Preference changeEmailPref = findPreference("changeEmailButton");
        final Preference changePasswordPref = findPreference("changePasswordButton");
        final Preference changeProfilePicPref = findPreference("changeProfilePictureButton");


        changeEmailPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                Intent intent = new Intent(SettingsActivityMainView.this, ChangeEmailView.class);
                startActivity(intent);

                return true;
            }
        });

        changeNamePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {

                Intent intent = new Intent(SettingsActivityMainView.this, ChangeNameView.class);
                startActivity(intent);

                return true;
            }
        });

        changePasswordPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                Intent intent = new Intent(SettingsActivityMainView.this, ChangePasswordView.class);
                startActivity(intent);

                return true;
            }
        });

        changeProfilePicPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                //TODO

                return false;
            }
        });
    }
}