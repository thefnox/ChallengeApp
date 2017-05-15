package team10.hkr.challengeapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import team10.hkr.challengeapp.Controllers.ProfileActivity;
import team10.hkr.challengeapp.Models.Comment;
import team10.hkr.challengeapp.Models.Post;

/**
 * Created by Charlie on 14.05.2017.
 */

public class PostListAdapter extends ArrayAdapter<Post> {

    private PopupWindow popComments;
    private ArrayList<Comment> comments = new ArrayList<Comment>();
    private AppSingleton sessionManager = AppSingleton.getInstance();
    private ImageView profilePhoto;
    private TextView username;
    private TextView description;
    private ImageView content;
    private TextView views;
    private TextView likes;
    private ImageView commentButton;
    private ListView commentListView;
    private TextView firstCommentView;
    private TextView secondCommentView;

    public PostListAdapter(Context context, ArrayList<Post> posts) {
        super(context, R.layout.single_comment, posts);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable  View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View customView = inflater.inflate(R.layout.single_post, parent, false);

        profilePhoto = (ImageView) customView.findViewById(R.id.profile_photo_post);
        username = (TextView) customView.findViewById(R.id.username_post);
        description = (TextView) customView.findViewById(R.id.description_post);
        content = (ImageView) customView.findViewById(R.id.content_view_post);
        commentButton = (ImageView) customView.findViewById(R.id.comment_post);
        views = (TextView) customView.findViewById(R.id.views_post);
        likes = (TextView) customView.findViewById(R.id.likes_post);
        profilePhoto.setImageResource(R.drawable.com_facebook_profile_picture_blank_portrait);

        content.setImageResource(R.drawable.com_facebook_profile_picture_blank_portrait);

        username.setText(sessionManager.getUser().getUserName());
        description.setText(getItem(position).getDescription());

        //for the profile photo
//        try {
//            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL().getContent());
//            profilePhoto.setImageBitmap(bitmap);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(customView.getContext(), CommentActivity.class);
                mIntent.putExtra("post_id", getItem(position).getUUID());
                getContext().startActivity(mIntent);
            }
        });
        return customView;
    }

}

