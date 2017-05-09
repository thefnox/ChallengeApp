package team10.hkr.challengeapp.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import team10.hkr.challengeapp.R;

import static android.R.attr.id;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        String[] comments = {                                                                                                                   //
                "Hello there!",                                                                                                                 //
                "Hello there to you too sir!",                                                                                                  //
                "What a fascinating challenge you got there!"
        };

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, comments);
        ListView listView = (ListView) findViewById(R.id.comment_list);
        listView.setAdapter(itemsAdapter);

        Button reportButton = (Button) findViewById(R.id.reportButton);
                                                                                                                      //


//        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.post_layout);
//
//        ImageView imageView = new ImageView(getApplicationContext());
//        imageView.setLayoutParams(new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//        ));
//        //Instead of this we gonna get the source from the post object
//        imageView.setImageDrawable(getResources().getDrawable(R.drawable.medal_with_shadow));
    }

    public void onReportClick(View view) {

            Intent reportIntent = new Intent(this, ReportActivity.class);
            startActivity(reportIntent);

    }
}
