package team10.hkr.challengeapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import de.hdodenhof.circleimageview.CircleImageView;
import team10.hkr.challengeapp.Controllers.CommentActivity;
import team10.hkr.challengeapp.Controllers.EditPostActivity;
import team10.hkr.challengeapp.Controllers.ProfileActivity;
import team10.hkr.challengeapp.Controllers.ReportActivity;
import team10.hkr.challengeapp.Controllers.VideoActivity;
import team10.hkr.challengeapp.Models.Post;
import team10.hkr.challengeapp.Models.Tag;
import team10.hkr.challengeapp.Models.User;

/**
 * Created by Charlie on 18.05.2017.
 */

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.PostHolder> {

    private AppSingleton sessionManager = AppSingleton.getInstance();
    private ArrayList<Post> postList = new ArrayList<>();
    private LayoutInflater inflater;
    private String CONTENT_URL = "";
    private ArrayList<Tag> tags;
    private String stringTagsHolder;

    public PostRecyclerAdapter(Context context, ArrayList<Post> postList) {
        this.inflater = LayoutInflater.from(context);
        this.postList = postList;
    }

    @Override
    public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_post, parent, false);
        stringTagsHolder = "";
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(final PostHolder holder, int position) {

        final Activity activity = (Activity) holder.itemView.getContext();
        final Post post = postList.get(position);
        sessionManager.updateFollowingUsers(activity);
        tags = new ArrayList<Tag>();
        post.setSelected(true);
        if(post.isSelected()) {
            tags.clear();
            try {
                for(int i=0; i< post.getTags().length(); i++) {
                    tags.add(new Tag(post.getTags().getJSONObject(i)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //holder.profilePhotoView.setImageResource(R.drawable.com_facebook_profile_picture_blank_portrait);
            boolean isSameUser = false;
            try {
                Log.d("PPIMURL", post.getAuthor().getString("profileImageURL"));
                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL("http://95.85.16.177:3000" + post.getAuthor().getString("profileImageURL")).getContent());
                    holder.profilePhotoView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                holder.userNameTextView.setText(post.getAuthor().getString("username"));
                Log.d("authorID:", (post.getAuthor().get("_id").toString()));
                if(post.getAuthor().get("_id").toString().equals(sessionManager.getUser().getUUID())) {
                    isSameUser = true;
                    holder.followButtonTextView.setVisibility(View.GONE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(!isSameUser) {
                Log.d("AmI", "Here");
                try {
                    if(isFollowing(post.getAuthor().getString("_id"))) {
                        //This does not work for some reason // check it later
                        holder.followButtonTextView.setText(String.valueOf("Unfollow"));
                        Log.d("AMI", "Here4");

                    } else if(!isFollowing(post.getAuthor().getString("_id"))) {

                        Log.d("AmI", "Here2");
                        holder.followButtonTextView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String author_id = "";
                                try {
                                    author_id = post.getAuthor().getString("_id");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                String url_follow = "http://95.85.16.177:3000/api/user/" + author_id + "/follow";
                                StringRequest stringRequest = new StringRequest(Request.Method.GET, url_follow, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(holder.followButtonTextView.getText().equals("Follow")) {
                                            holder.followButtonTextView.setText(String.valueOf("Unfollow"));
                                        } else {
                                            holder.followButtonTextView.setText(String.valueOf("Follow"));
                                        }
                                        sessionManager.updateFollowingUsers(activity);
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(activity, "Something went wrong", Toast.LENGTH_LONG).show();
                                    }
                                });
                                RequestQueueSingleton.getInstance(activity).addToRequestQueue(stringRequest);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if(holder.itemView.getContext().getClass() == ProfileActivity.class) {

                holder.editButtonImageButton.setVisibility(View.VISIBLE);
                holder.editButtonImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(activity, EditPostActivity.class);
                        intent.putExtra("postUUID", post.getUUID());
                        intent.putExtra("postDescription", post.getDescription());
                        try {
                            intent.putExtra("contentURL", "http://95.85.16.177:3000" + post.getContent().getString("staticURL"));
                            intent.putExtra("staticURL", post.getContent().getString("staticURL"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        activity.startActivity(intent);
                    }
                });

                holder.deleteButtonImageButton.setVisibility(View.VISIBLE);
                holder.deleteButtonImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AlertDialog.Builder(activity)

                                .setTitle("Delete Post")
                                .setMessage("Are you sure you want to delete this post?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        final String URL = "http://95.85.16.177:3000/api/post/"+post.getUUID();

                                        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, URL, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Toast.makeText(activity,"Post Deleted", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(activity, ProfileActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                activity.startActivity(intent);

                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_LONG).show();
                                            }
                                        });

                                        RequestQueueSingleton.getInstance(activity).addToRequestQueue(stringRequest);


                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                });
            }

            //       Post Tags
            stringTagsHolder = "";
            for(int i = 0; i < tags.size(); i++) {
                stringTagsHolder = stringTagsHolder + tags.get(i).getName() + " ";
            }
            holder.challengeTagTextView.setText(stringTagsHolder);
            //       Post description
            holder.challengeDescriptionTextView.setText(post.getDescription());

            //              CONTENT             //

            try {
                CONTENT_URL = "http://95.85.16.177:3000" + post.getContent().getString("staticURL");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            try {
                if (isJpg(CONTENT_URL)) {
                    //Images here
                    Log.d("halala", post.getContent().getString("staticURL"));
                    Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(CONTENT_URL).getContent());
                    holder.contentIfPhotoView.setImageBitmap(bitmap);
                    holder.videoStartClickTextView.setVisibility(View.GONE);
                    holder.contentIfPhotoView.setVisibility(View.VISIBLE);

                } else if (CONTENT_URL.endsWith(".mp4")) {
                    //Videos here
                    holder.contentIfPhotoView.setVisibility(View.GONE);
                    holder.videoStartClickTextView.setVisibility(View.VISIBLE);
                    holder.videoThumbnail.setVisibility(View.VISIBLE);
                    try {
                        holder.videoThumbnail.setImageBitmap(GetVideoFrame.retriveVideoFrameFromVideo(CONTENT_URL));
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                    holder.videoStartClickTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(activity, VideoActivity.class);
                            intent.putExtra("VideoURL", CONTENT_URL);
                            activity.startActivity(intent);
                        }
                    });
                } else {
                    holder.contentIfPhotoView.setVisibility(View.GONE);
                    holder.videoStartClickTextView.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            //              LIKES AND VIEWS COUNTERS                //
            //Views is not implemented yet
            //holder.viewsTextView.setText(String.valueOf(post.getViews())  + " Views");
            holder.likesTextView.setText(String.valueOf(post.getLikes())  + " Likes");
            Log.d("LIKES:", String.valueOf(post.getLikes()));

            //££##££##      CLICK_LISTENERS     ##££##££\\

            holder.commentImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(activity, CommentActivity.class);
                    mIntent.putExtra("post_id", post.getUUID());
                    activity.startActivity(mIntent);
                }
            });
            holder.flagImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(activity, ReportActivity.class);
                    mIntent.putExtra("post_id", post.getUUID());
                    try {
                        mIntent.putExtra("username", post.getAuthor().getString("username"));
                        mIntent.putExtra("contentURL", "http://95.85.16.177:3000" + post.getContent().getString("staticURL"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mIntent.putExtra("tags", holder.challengeTagTextView.getText());
                    activity.startActivity(mIntent);
                }
            });

            if (Arrays.asList(post.getLikesUsersArrayList(post.getLikesUsers())).contains(AppSingleton.getInstance().getUser().getUUID())) {

                holder.thumbsUpImageButton.setBackgroundColor(0xffffbb33);

            }

            holder.thumbsUpImageButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    final User you = AppSingleton.getInstance().getUser();

                    try {

                        if (post.getAuthor().getString("_id").equals(you.getUUID())){

                            Toast.makeText(activity, "You cannot like your own posts", Toast.LENGTH_SHORT).show();
                        }

                        else if (Arrays.asList(post.getLikesUsersArrayList(post.getLikesUsers())).contains(you.getUUID())) {

                            final String URL = "http://95.85.16.177:3000/api/post/" + post.getUUID() + "/like";

                            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(activity, "Like Removed", Toast.LENGTH_LONG).show();
                                    holder.thumbsUpImageButton.setBackgroundColor(0x00000000);

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(activity, "Something went wrong", Toast.LENGTH_LONG).show();
                                }

                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {

                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("post_id", post.getUUID());
                                    return params;
                                }
                            };

                            RequestQueueSingleton.getInstance(activity).addToRequestQueue(stringRequest);
                        }

                        else {

                            final String URL = "http://95.85.16.177:3000/api/post/" + post.getUUID() + "/like";

                            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(activity, "Post Liked", Toast.LENGTH_LONG).show();
                                    holder.thumbsUpImageButton.setBackgroundColor(0xffffbb33);

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(activity, "Something went wrong", Toast.LENGTH_LONG).show();
                                }

                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {

                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("post_id", post.getUUID());
                                    return params;
                                }
                            };

                            RequestQueueSingleton.getInstance(activity).addToRequestQueue(stringRequest);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    class PostHolder extends RecyclerView.ViewHolder {

            View container;
            LinearLayout videoStartClickTextView;
            ImageView videoThumbnail;
            CircleImageView profilePhotoView;
            TextView userNameTextView;
            TextView followButtonTextView;
            ImageButton deleteButtonImageButton;
            ImageButton editButtonImageButton;
            TextView challengeTagTextView;
            TextView challengeDescriptionTextView;
            ImageView contentIfPhotoView;
            ImageButton thumbsUpImageButton;
            ImageButton commentImageButton;
            ImageButton shareImageButton;
            ImageButton flagImageButton;
            TextView viewsTextView;
            TextView likesTextView;

            public PostHolder(View itemView) {
                super(itemView);
                videoThumbnail = (ImageView) itemView.findViewById(R.id.video_thumbnail_post);
                videoStartClickTextView = (LinearLayout) itemView.findViewById(R.id.video_start_text);
                profilePhotoView = (CircleImageView) itemView.findViewById(R.id.profile_photo_post);
                userNameTextView = (TextView) itemView.findViewById(R.id.username_post);
                followButtonTextView = (TextView) itemView.findViewById(R.id.follow_button_post);
                deleteButtonImageButton = (ImageButton) itemView.findViewById(R.id.delete_button_post);
                editButtonImageButton = (ImageButton) itemView.findViewById(R.id.edit_button_post);
                challengeTagTextView = (TextView) itemView.findViewById(R.id.challenge_tag_post);
                challengeDescriptionTextView = (TextView) itemView.findViewById(R.id.description_post);
                contentIfPhotoView = (ImageView) itemView.findViewById(R.id.content_view_post);
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

    public boolean isFollowing(String id) {

        for(int i = 0; i < sessionManager.getFollowingUsers().size(); i++) {
            if (sessionManager.getFollowingUsers().get(i).equals(id)) {
                return true;
            }
        }
        return false;
    }

}
