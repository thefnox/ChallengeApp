package team10.hkr.challengeapp.Controllers;

import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.Intent;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import team10.hkr.challengeapp.Controllers.UploadPostActivity.CameraActivity;
import team10.hkr.challengeapp.R;

import static android.R.color.white;


public class PrimaryActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //Where we add the logo
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setIcon(R.drawable.medal_with_shadow);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //This is where I add the Icons. They should be bigger I know.
        tabLayout.getTabAt(0).setIcon(R.drawable.medal_with_shadow);
        tabLayout.getTabAt(1).setIcon(R.drawable.medal_icon_white);
        tabLayout.getTabAt(2).setIcon(R.drawable.medal_with_shadow);

        //testing the profile button for the app bar
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setIcon(R.drawable.medal_with_shadow);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent cameraIntent = new Intent(PrimaryActivity.this, CameraActivity.class);
                startActivity(cameraIntent);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_primary, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            //for having it as a fragment
//            getFragmentManager().beginTransaction().replace(R.id.main_content,
//                    new SettingsFragment()).commit();

            Intent mIntent = new Intent(PrimaryActivity.this, SettingsActivity.class);
            startActivity(mIntent);
            return true;

        } else if (id == R.id.profile_button) {

            Intent intent = new Intent(PrimaryActivity.this, ProfileActivity.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.search_button) {

            Intent mIntent = new Intent(PrimaryActivity.this, SearchActivity.class);
            startActivity(mIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Tab1Winning tab1 = new Tab1Winning();
                    return tab1;
                case 1:
                    Tab2Fresh tab2 = new Tab2Fresh();
                    return tab2;
                case 2:
                    Tab3CustomFeed tab3 = new Tab3CustomFeed();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Winning";
                case 1:
                    return "Fresh";
                case 2:
                    return "Subs";
            }
            return null;
        }
    }


        //for having Settings as a fragment
//    public static class SettingsFragment extends PreferenceActivity {
//
//        public void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//
//            // Load the preferences from an XML resource
//            addPreferencesFromResource(R.xml.settings_main_preferences);
//        }
//
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//            View view = super.onCreateView(inflater, container, savedInstanceState);
//            view.setBackgroundColor(getResources().getColor(white));
//
//            return view;
//        }
//  }


    }
