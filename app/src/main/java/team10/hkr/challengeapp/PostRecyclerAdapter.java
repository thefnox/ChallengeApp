package team10.hkr.challengeapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import org.json.JSONException;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import team10.hkr.challengeapp.Controllers.CommentActivity;
import team10.hkr.challengeapp.Controllers.ReportActivity;
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

        final Post post = postList.get(position);

        holder.profilePhotoView.setImageResource(R.drawable.com_facebook_profile_picture_blank_portrait);
        holder.userNameTextView.setText("SakuraChan");
        holder.challengeTagTextView.setText("#Not Implemented Yet");
        holder.challengeDescriptionTextView.setText(post.getDescription());

        //              CONTENT             //
        String CONTENT_URL = "";
        try {
            CONTENT_URL = "http://95.85.16.177:3000" + post.getContent().getString("staticURL");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
                if (isJpg(CONTENT_URL)) {
                    Log.d("halala", post.getContent().getString("staticURL"));
                    Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(CONTENT_URL).getContent());
                    holder.contentIfPhotoView.setImageBitmap(bitmap);
                    holder.contentIfPhotoView.setVisibility(View.VISIBLE);

                } else if (CONTENT_URL.endsWith(".mp4")) {
                    //Show video here
                    holder.contentIfVideoView.setVisibility(View.VISIBLE);
                    holder.contentIfVideoView.seekTo(3000);
                    final MediaController mediaController = new MediaController(holder.itemView.getContext());
                    mediaController.setAnchorView(holder.contentIfVideoView);
                    Uri video_uri = Uri.parse(CONTENT_URL);
                    holder.contentIfVideoView.setVideoURI(video_uri);
                    holder.contentIfVideoView.setMediaController(mediaController);
                    Log.d("CheckURLifValid", CONTENT_URL);

                } else {
                    holder.contentIfPhotoView.setImageResource(R.drawable.invalid_content);
                    holder.contentIfPhotoView.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        //              LIKES AND VIEWS COUNTERS                //
        holder.viewsTextView.setText(post.getViews() + R.string.views);
        holder.likesTextView.setText(post.getLikes() + R.string.likes);

                        //££##££##      CLICK_LISTENERS     ##££##££\\
//        holder.commentImageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent mIntent = new Intent(holder.itemView.getContext(), CommentActivity.class);
//                mIntent.putExtra("post_id", post.getUUID());
//                holder.itemView.getContext().startActivity(mIntent);
//            }
//        });
//        holder.flagImageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent mIntent = new Intent(, ReportActivity.class);
//                mIntent.putExtra("post_id", post.getUUID());
//                getContext().startActivity(mIntent);
//            }
//        });


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
