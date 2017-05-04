package team10.hkr.challengeapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import team10.hkr.challengeapp.Controllers.Tab1Winning;

/**
 * Created by Charlie on 4.05.2017.
 */

public class WinningListAdapter extends BaseAdapter {

    private Context context;
    private List<String> posts;
    private static LayoutInflater inflater = null;

    public WinningListAdapter(Tab1Winning a, List<String> p) {

        posts = p;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return posts.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(convertView == null) {
            v = inflater.inflate(R.layout.winnig_list_row, parent, false);
        }
        ImageView thmbnail = (ImageView)v.findViewById(R.id.challenge_thumbnail);
        TextView tag = (TextView)v.findViewById(R.id.challenge_tag);
        ImageView ply = (ImageView)v.findViewById(R.id.challenge_play);


        tag.setText(posts.get(position));
        thmbnail.setImageResource(R.drawable.medal_with_shadow);
        ply.setImageResource(R.drawable.com_facebook_send_button_icon);

        return v;
    }
}
