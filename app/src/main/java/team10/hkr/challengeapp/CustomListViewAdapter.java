package team10.hkr.challengeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Charlie on 1.05.2017.
 */

public class CustomListViewAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<HashMap<String, String>> books;
    private static LayoutInflater inflater = null;  //Helps us to go and get the layouts and use them in the java code, inflates* them


    public CustomListViewAdapter(Context context, ArrayList<HashMap<String, String>> data) {
        mContext = context;
        books = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if(convertView == null) {
            view = inflater.inflate(R.layout.winnig_list_row, null);

            TextView tag = (TextView) view.findViewById(R.id.challenge_tag);
            ImageView challengeThumbnail = (ImageView) view.findViewById(R.id.challenge_thumbnail);

            HashMap<String, String> mBook = new HashMap<>();
            mBook = books.get(position);

            tag.setText(mBook.get("tag"));
            challengeThumbnail.setImageDrawable(mContext.getResources().getDrawable(R.drawable.medal_with_shadow));

        }

        return null;
    }
}
