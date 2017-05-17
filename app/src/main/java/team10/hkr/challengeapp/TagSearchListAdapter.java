package team10.hkr.challengeapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import org.json.JSONException;

import java.util.ArrayList;
import team10.hkr.challengeapp.Models.Tag;

/**
 * Created by Charlie on 14.05.2017.
 */

public class TagSearchListAdapter extends ArrayAdapter<Tag> {

    ArrayList<Tag> tags;

    public TagSearchListAdapter(Context context, ArrayList<Tag> tags) {
        super(context, R.layout.single_search_tag, tags);
        this.tags = tags;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.single_comment, parent, false);

        if (tags != null) {
            Tag mTagObject = getItem(position);
            TextView tagName = (TextView) customView.findViewById(R.id.tag_name_search);
            TextView tagLikes = (TextView) customView.findViewById(R.id.tag_likes_search);

            //Do the magic

        }
        return customView;
    }
}
