package team10.hkr.challengeapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

import team10.hkr.challengeapp.Models.Comment;
import team10.hkr.challengeapp.Models.Post;

/**
 * Created by Charlie on 14.05.2017.
 */

public class PostListAdapter extends ArrayAdapter<Post> {

    private ArrayList<Comment> comments;
    AppSingleton sessionManager = AppSingleton.getInstance();

    public PostListAdapter(Context context, ArrayList<Post> posts) {
        super(context, R.layout.single_comment, posts);
    }

    @NonNull
    @Override
    public View getView(int position,@Nullable  View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.single_post, parent, false);

        ImageView profilePhoto = (ImageView) customView.findViewById(R.id.profile_photo_post);
        TextView username = (TextView) customView.findViewById(R.id.username_post);
        TextView description = (TextView) customView.findViewById(R.id.description_post);
        ImageView content = (ImageView) customView.findViewById(R.id.content_view_post);
        TextView views = (TextView) customView.findViewById(R.id.views_post);
        TextView likes = (TextView) customView.findViewById(R.id.likes_post);
        ListView commentList = (ListView) customView.findViewById(R.id.comment_list_post);

        profilePhoto.setImageResource(R.drawable.com_facebook_profile_picture_blank_portrait);
        //description.setText(mPostObject.getDescription());
        content.setImageResource(R.drawable.com_facebook_profile_picture_blank_portrait);

        username.setText(sessionManager.getUser().getUserName());
        description.setText(getItem(position).getDescription());
        //WHY ARE IS THIS NULL GOD DAMN IT
       // views.setText(getItem(position).toString());
        //likes.setText(getItem(position).getLikes());

        if(comments != null) {
            for(int i = 0; i < getItem(position).getComments().length(); i++) {
                try {
                    comments.add(new Comment(getItem(position).getComments().getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            CommentListAdapter adapter = new CommentListAdapter(getContext(), comments);
            commentList.setAdapter(adapter);
        }

        return customView;
    }
}
