package team10.hkr.challengeapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import team10.hkr.challengeapp.Models.Post;

/**
 * Created by Charlie on 18.05.2017.
 */

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.PostHolder> {

    private ArrayList<Post> postList = new ArrayList<>();
    private LayoutInflater inflater;

    public PostRecyclerAdapter(Context context, ArrayList<Post> postList) {
        this.inflater = LayoutInflater.from(context);
        this.postList = postList;
    }

    @Override
    public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_post, parent, false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(PostHolder holder, int position) {
        Post post = postList.get(position);
        holder.profilePhotoView.setImageResource(R.drawable.com_facebook_profile_picture_blank_portrait);
        holder.userNameTextView.setText("SakuraChan");
        holder.challengeTagTextView.setText("#Not Implemented Yet");
        holder.challengeDescriptionTextView.setText(post.getDescription());
        holder.contentIfPhotoView.setImageResource(R.drawable.invalid_content);
        holder.contentIfPhotoView.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    class PostHolder extends RecyclerView.ViewHolder {

            View container;
            CircleImageView profilePhotoView;
            TextView userNameTextView;
            TextView challengeTagTextView;
            TextView challengeDescriptionTextView;
            ImageView contentIfPhotoView;
            VideoView contentIfVideoView;
            ImageButton thumbsUpImageButton;
            ImageButton commentImageButton;
            ImageButton shareImageButton;
            ImageButton flagImageButton;
            TextView viewsTextView;
            TextView likesTextView;

            public PostHolder(View itemView) {
                super(itemView);

                profilePhotoView = (CircleImageView) itemView.findViewById(R.id.profile_photo_post);
                userNameTextView = (TextView) itemView.findViewById(R.id.username_post);
                challengeTagTextView = (TextView) itemView.findViewById(R.id.challenge_tag_post);
                challengeDescriptionTextView = (TextView) itemView.findViewById(R.id.description_post);
                contentIfPhotoView = (ImageView) itemView.findViewById(R.id.content_view_post);
                contentIfVideoView = (VideoView) itemView.findViewById(R.id.content_video_view_post);
                thumbsUpImageButton = (ImageButton) itemView.findViewById(R.id.thumbsup_post);
                commentImageButton = (ImageButton) itemView.findViewById(R.id.comment_post);
                shareImageButton = (ImageButton) itemView.findViewById(R.id.share_post);
                flagImageButton = (ImageButton) itemView.findViewById(R.id.flag_post);
                viewsTextView = (TextView) itemView.findViewById(R.id.views_post);
                likesTextView = (TextView) itemView.findViewById(R.id.likes_post);


            }
        }
}
