package team10.hkr.challengeapp;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import org.json.JSONException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import team10.hkr.challengeapp.Controllers.CommentActivity;
import team10.hkr.challengeapp.Controllers.ReportActivity;
import team10.hkr.challengeapp.Models.Post;

/**
 * Created by Charlie on 14.05.2017.
 */

public class PostListAdapter extends ArrayAdapter<Post> {

    //private PopupWindow popComments;
    //private ArrayList<Comment> comments = new ArrayList<Comment>();
    private AppSingleton sessionManager = AppSingleton.getInstance();
    private ImageView profilePhoto;
    private TextView username;
    private TextView description;
    private ImageView content;
    private VideoView contentVideo;
    private TextView views;
    private TextView likes;
    private ImageView commentButton;
    private ImageView flagButton;
    private ListView commentListView;
    private TextView firstCommentView;
    private TextView secondCommentView;

    public PostListAdapter(Context context, ArrayList<Post> posts) {
        super(context, R.layout.single_comment, posts);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View customView = inflater.inflate(R.layout.single_post, parent, false);

        // Header
        profilePhoto = (ImageView) customView.findViewById(R.id.profile_photo_post);
        username = (TextView) customView.findViewById(R.id.username_post);
        description = (TextView) customView.findViewById(R.id.description_post);
        // Content
        content = (ImageView) customView.findViewById(R.id.content_view_post);
        contentVideo = (VideoView) customView.findViewById(R.id.content_video_view_post);
        // Interaction buttons
        commentButton = (ImageView) customView.findViewById(R.id.comment_post);
        flagButton = (ImageView) customView.findViewById(R.id.flag_post);
        // Statistics
        views = (TextView) customView.findViewById(R.id.views_post);
        likes = (TextView) customView.findViewById(R.id.likes_post);
        description.setText(getItem(position).getDescription());

       // ################ declare #############

        profilePhoto.setImageResource(R.drawable.com_facebook_profile_picture_blank_portrait);
        likes.setText(String.valueOf(getItem(position).getLikes()) + " Likes");
        views.setText(String.valueOf(getItem(position).getViews()) + " Views");

        //              #user photo#               //
        // profile photos need static ips as well
//        int SDK_INT = android.os.Build.VERSION.SDK_INT;
//        if (SDK_INT > 8)
//        {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
//                    .permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//            try {
//                Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL("http://95.85.16.177:3000" + getItem(position).getAuthor().getString("profileImageURL")).getContent());
//                if (isJpg(getItem(position).getContent().getString("staticURL"))) {
//                    content.setImageBitmap(bitmap);
//                } else {
//                    content.setImageResource(R.drawable.invalid_content);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }

        //                  #username#            //
        //Server needs to be fixed for this one
//        try {
//            username.setText(getItem(position).getAuthor().getString("username"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        //                 #content#             //
        //We do this to avoid the exception that we get for performing a network operation in the main thread
        String CONTENT_URL = "";
        try {
            CONTENT_URL = "http://95.85.16.177:3000" + getItem(position).getContent().getString("staticURL");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                if (isJpg(getItem(position).getContent().getString("staticURL"))) {
                    Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(CONTENT_URL).getContent());
                    content.setImageBitmap(bitmap);
                    content.setVisibility(View.VISIBLE);
                } else if(CONTENT_URL.endsWith(".mp4")) {
                    //Show video here
                    MediaController mediaController = new MediaController(customView.getContext());
                    mediaController.setAnchorView(contentVideo);
                    Uri video_uri = Uri.parse(CONTENT_URL);
                    contentVideo.setMediaController(mediaController);
                    contentVideo.setVideoURI(video_uri);
                    contentVideo.requestFocus();
                    contentVideo.setVisibility(View.VISIBLE);
                    Log.d("CheckURLifValid", CONTENT_URL);

                } else {
                    content.setImageResource(R.drawable.invalid_content);
                    content.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
                e.printStackTrace();
            }
        }

        // ##################  Click listeners ####################

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(customView.getContext(), CommentActivity.class);
                mIntent.putExtra("post_id", getItem(position).getUUID());
                getContext().startActivity(mIntent);
            }
        });
        flagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(customView.getContext(), ReportActivity.class);
                mIntent.putExtra("post_id", getItem(position).getUUID());
                getContext().startActivity(mIntent);
            }
        });

        return customView;
    }

    public boolean isJpg(String url) {
        if (url.length() > 4) {
            String tester = url.split("")[url.length()-2] + url.split("")[url.length()-1] + url.split("")[url.length()];
            Log.d("JPGTESTERURL",url);
            Log.d("JPGTESTER", tester);
            if(tester.equals("jpg")) {
                return true;
            }
        }
        return false;
    }

}

