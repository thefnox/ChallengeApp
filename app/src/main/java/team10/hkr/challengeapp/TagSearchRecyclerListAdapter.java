package team10.hkr.challengeapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import org.json.JSONException;

import java.util.ArrayList;
import team10.hkr.challengeapp.Models.Tag;

/**
 * Created by Charlie on 14.05.2017.
 */

public class TagSearchRecyclerListAdapter extends RecyclerView.Adapter<TagSearchRecyclerListAdapter.TagHolder> {

    private ArrayList<Tag> tagArrayList = new ArrayList<>();
    private LayoutInflater inflater;

    public TagSearchRecyclerListAdapter(Context context, ArrayList<Tag> tagArrayList) {
        this.inflater = LayoutInflater.from(context);
        this.tagArrayList = tagArrayList;
    }

    @Override
    public TagHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_search_tag, parent, false);
        return new TagHolder(view);
    }


    @Override
    public void onBindViewHolder(TagSearchRecyclerListAdapter.TagHolder holder, int position) {
        final Activity activity = (Activity) holder.itemView.getContext();
        final Tag tag = tagArrayList.get(position);
        //holder.tagNameTextView.setText(tag.getName());
        Log.d("TagName", tag.getUUID());
        Log.d("Whtthe", "1234");
        holder.tagNameTextView.setText(tag.getUUID());
        holder.likesTextView.setText("3520 Likes");

    }

    @Override
    public int getItemCount() {
        return tagArrayList.size();
    }

    class TagHolder extends RecyclerView.ViewHolder {

        TextView tagNameTextView;
        TextView likesTextView;

        public TagHolder(View itemView) {
            super(itemView);
            tagNameTextView = (TextView) itemView.findViewById(R.id.tag_name_search);
            likesTextView = (TextView) itemView.findViewById(R.id.tag_likes_search);
        }
    }
}
