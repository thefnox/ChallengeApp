package team10.hkr.challengeapp.Controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.net.CookieHandler;

import team10.hkr.challengeapp.AppSingleton;
import team10.hkr.challengeapp.Controllers.SettingsActivity.SettingsActivityMainView;
import team10.hkr.challengeapp.R;
import team10.hkr.challengeapp.RequestQueueSingleton;
import team10.hkr.challengeapp.SharedPref;


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
    private ImageView actionBarLogo;

    AppSingleton sessionManager = AppSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary);
        SharedPref.init(getApplicationContext());
        CookieHandler.setDefault(sessionManager.getCookieManager());


        actionBarLogo = (ImageView) findViewById(R.id.action_bar_logo);
        actionBarLogo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });

        ImageView profileButton = (ImageView)(findViewById(R.id.profile_button));
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrimaryActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //This is where I add the Icons. They should be bigger I know.
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_star);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_coffee);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_filter_list);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            Intent newPostIntent = new Intent(PrimaryActivity.this, UploadPostActivity.class);
            startActivity(newPostIntent);

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

            Intent mIntent = new Intent(PrimaryActivity.this, SettingsActivityMainView.class);
            startActivity(mIntent);
            return true;

        } else if (id == R.id.search_button) {

            Intent mIntent = new Intent(PrimaryActivity.this, SearchActivity.class);
            startActivity(mIntent);
            return true;

        } else if (id == R.id.sign_out_button){

            new AlertDialog.Builder(this)
                    .setTitle("Sign Out")
                    .setMessage("Are you sure you want to sign out?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                            final String SIGN_OUT_URL = "http://95.85.16.177:3000/api/auth/signout";

                            StringRequest stringRequest = new StringRequest(Request.Method.GET, SIGN_OUT_URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(PrimaryActivity.this,"Signed Out", Toast.LENGTH_LONG).show();
                                    Intent mIntent = new Intent(PrimaryActivity.this, LoginActivity.class);

                                    mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(mIntent);
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(PrimaryActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                                }
                            });

                            RequestQueueSingleton.getInstance(PrimaryActivity.this).addToRequestQueue(stringRequest);

                        }})

                    .setNegativeButton(android.R.string.no, null).show();


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

