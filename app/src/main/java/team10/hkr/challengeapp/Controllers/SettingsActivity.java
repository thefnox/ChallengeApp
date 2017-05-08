package team10.hkr.challengeapp.Controllers;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.CheckBox;

import team10.hkr.challengeapp.R;

/**
 * Created by Charlie on 7.05.2017.
 */

public class SettingsActivity extends PreferenceActivity {

//    to be used when database is implemented
//    final CheckBoxPreference turnOnNotif = (CheckBoxPreference) findPreference("turnOnNotificationsBox");
//    final CheckBoxPreference commentNotif = (CheckBoxPreference) findPreference("commentNotificationBox");
//    final CheckBoxPreference replyNotif = (CheckBoxPreference) findPreference("commentReplyNotificationBox");
//    final CheckBoxPreference shareNotif = (CheckBoxPreference) findPreference("postShareNotificationBox");


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings_main_preferences);



    }
}