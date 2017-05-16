package team10.hkr.challengeapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import team10.hkr.challengeapp.Controllers.CommentActivity;
import team10.hkr.challengeapp.Controllers.ReportActivity;
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
    private ImageView flagButton;
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

        // Header
        profilePhoto = (ImageView) customView.findViewById(R.id.profile_photo_post);
        username = (TextView) customView.findViewById(R.id.username_post);
        description = (TextView) customView.findViewById(R.id.description_post);
        // Content
        content = (ImageView) customView.findViewById(R.id.content_view_post);
        // Interaction buttons
        commentButton = (ImageView) customView.findViewById(R.id.comment_post);
        flagButton = (ImageView) customView.findViewById(R.id.flag_post);
        // Statistics
        views = (TextView) customView.findViewById(R.id.views_post);
        likes = (TextView) customView.findViewById(R.id.likes_post);

       // ################ declare #############
        profilePhoto.setImageResource(R.drawable.com_facebook_profile_picture_blank_portrait);
        content.setImageResource(R.drawable.com_facebook_profile_picture_blank_portrait);
        username.setText(sessionManager.getUser().getUserName());
        description.setText(getItem(position).getDescription());
        //return type is problematic
//        views.setText(getItem(position).getViews());
//        likes.setText(getItem(position).getLikes());

        //for the profile photo
//        try {
//            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL().getContent());
//            profilePhoto.setImageBitmap(bitmap);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


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

}

