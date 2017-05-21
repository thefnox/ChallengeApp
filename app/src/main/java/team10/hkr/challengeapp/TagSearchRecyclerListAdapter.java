package team10.hkr.challengeapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import team10.hkr.challengeapp.Controllers.SearchResultActivity;
import team10.hkr.challengeapp.Models.SearchTag;
import team10.hkr.challengeapp.Models.Tag;

/**
 * Created by Charlie on 14.05.2017.
 */

public class TagSearchRecyclerListAdapter extends RecyclerView.Adapter<TagSearchRecyclerListAdapter.TagHolder> {

    private ArrayList<SearchTag> tagArrayList = new ArrayList<>();
    private LayoutInflater inflater;

    public TagSearchRecyclerListAdapter(Context context, ArrayList<SearchTag> tagArrayList) {
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
        final SearchTag tag = tagArrayList.get(position);
        //holder.tagNameTextView.setText(tag.getName());
        //should've been tag.getName but this was implemented this way in the server by accident I assume
        Log.d("TagName", tag.getName());
        holder.tagNameTextView.setText(tag.getName());
        holder.likesTextView.setText(String.valueOf(tag.getCount() + " Posts"));
        holder.tagNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SearchResultActivity.class);
                intent.putExtra("KEY_WORD", tag.getName().substring(1));
                activity.startActivity(intent);
            }
        });

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
