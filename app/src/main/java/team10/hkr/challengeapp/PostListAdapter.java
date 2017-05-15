package team10.hkr.challengeapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;

import team10.hkr.challengeapp.Models.Comment;
import team10.hkr.challengeapp.Models.Post;

/**
 * Created by Charlie on 14.05.2017.
 */

public class PostListAdapter extends ArrayAdapter<Post> {

    private ArrayList<Comment> comments = new ArrayList<Comment>();
    private AppSingleton sessionManager = AppSingleton.getInstance();
    private ImageView profilePhoto;
    private TextView username;
    private TextView description;
    private ImageView content;
    private TextView views;
    private TextView likes;
    private ListView commentList;
    private TextView firstCommentView;
    private TextView secondCommentView;

    public PostListAdapter(Context context, ArrayList<Post> posts) {
        super(context, R.layout.single_comment, posts);
    }

    @NonNull
    @Override
    public View getView(int position,@Nullable  View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.single_post, parent, false);

        profilePhoto = (ImageView) customView.findViewById(R.id.profile_photo_post);
        username = (TextView) customView.findViewById(R.id.username_post);
        description = (TextView) customView.findViewById(R.id.description_post);
        content = (ImageView) customView.findViewById(R.id.content_view_post);
        views = (TextView) customView.findViewById(R.id.views_post);
        likes = (TextView) customView.findViewById(R.id.likes_post);
        firstCommentView = (TextView) customView.findViewById(R.id.username_comment_first);
        secondCommentView = (TextView) customView.findViewById(R.id.username_comment_second);
       //ListView commentList = (ListView) customView.findViewById(R.id.comment_list_post);

        profilePhoto.setImageResource(R.drawable.com_facebook_profile_picture_blank_portrait);
        //description.setText(mPostObject.getDescription());
        content.setImageResource(R.drawable.com_facebook_profile_picture_blank_portrait);



        username.setText(sessionManager.getUser().getUserName());
        description.setText(getItem(position).getDescription());
        //WHY ARE IS THIS NULL GOD DAMN IT
        //views.setText(getItem(position).toString());
        //likes.setText(getItem(position).getLikes());

        try {
            for(int i = 0; i < getItem(position).getComments().length(); i++) {
                    comments.add(new Comment(getItem(position).getComments().getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Toast.makeText(getContext(), String.valueOf(getItem(position).getComments().length()), Toast.LENGTH_LONG).show();

//        CommentListAdapter adapter = new CommentListAdapter(getContext(), comments);
//        commentList.setAdapter(adapter);

        final StyleSpan boldStyle = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
        final StyleSpan italicStyle = new StyleSpan(android.graphics.Typeface.ITALIC); //Span to make text italic

        String holderUsernameFirst = "Mr.Falafelface";
        String holderUsernameSecond = "SwedishMan";

        String string = "";
        String second_string = "";

        if(comments.size() > 0) {
            string = holderUsernameFirst + " " + comments.get(0).getText();
            second_string = holderUsernameSecond + " " + comments.get(1).getText();
        } else {
            string = "000000000000000000000000000";
            second_string = "222222222222222222222222";
        }

        SpannableString spannableStringFirst = new SpannableString(string);
        SpannableString spannableStringSecond = new SpannableString(second_string);
        spannableStringFirst.setSpan(boldStyle, 0, holderUsernameFirst.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableStringSecond.setSpan(boldStyle, 2, 5, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        firstCommentView.setText(spannableStringFirst);
        secondCommentView.setText(spannableStringSecond);

        return customView;
    }
}
