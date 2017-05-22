package team10.hkr.challengeapp.Controllers.SettingsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import team10.hkr.challengeapp.AppSingleton;
import team10.hkr.challengeapp.Models.User;
import team10.hkr.challengeapp.R;

/**
 * Created by Charlie on 7.05.2017.
 */

public class SettingsActivityMainView extends PreferenceActivity {

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_main_preferences);

        User user = AppSingleton.getInstance().getUser();

        final Preference changeNamePref = findPreference("editNameButton");
        final Preference changeEmailPref = findPreference("changeEmailButton");
        final Preference changePasswordPref = findPreference("changePasswordButton");
        final Preference changeProfilePicPref = findPreference("changeProfilePictureButton");
        final Preference closeAccountPref = findPreference("closeAccountPreference");

        changeEmailPref.setSummary("Current Email:  " + user.getEmail());
        changeNamePref.setSummary("Current Name: " + user.getFirstName() + " " + user.getLastName());

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

                Intent intent = new Intent(SettingsActivityMainView.this, ChangeProfilePictureActivity.class);
                startActivity(intent);

                return true;
            }
        });

        closeAccountPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                Intent intent = new Intent(SettingsActivityMainView.this, CloseAccountActivity.class);
                startActivity(intent);

                return true;
            }
        });
    }
}