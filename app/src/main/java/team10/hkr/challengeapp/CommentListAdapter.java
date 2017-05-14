package team10.hkr.challengeapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import team10.hkr.challengeapp.Models.Comment;

/**
 * Created by Charlie on 14.05.2017.
 */

public class CommentListAdapter extends ArrayAdapter<Comment> {

    ArrayList<Comment> comments;

    public CommentListAdapter(Context context, ArrayList<Comment> comments) {
        super(context, R.layout.single_comment, comments);
        this.comments = comments;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.single_comment, parent, false);

        if(comments != null) {
            Comment mCommentObject = getItem(position);
            TextView username = (TextView) customView.findViewById(R.id.username_comment);
            TextView comment = (TextView) customView.findViewById(R.id.comment_comment);
            comment.setText(comments.get(position).getText());
        }

        return customView;
    }
}
