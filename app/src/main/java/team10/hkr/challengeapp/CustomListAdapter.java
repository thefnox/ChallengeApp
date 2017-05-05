package team10.hkr.challengeapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import team10.hkr.challengeapp.Controllers.Tab1Winning;

/**
 * Created by Charlie on 5.05.2017.
 */

public class CustomListAdapter extends ArrayAdapter {

    public CustomListAdapter(Context context, ArrayList<Object> resource) {
        super(context,R.layout.winnig_list_row, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        //This is going to be changed with a better solution in future.
        View customView = inflater.inflate(R.layout.winnig_list_row, parent, false);


        TextView textView = (TextView) customView.findViewById(R.id.challenge_tag);
        ImageView imageView = (ImageView) customView.findViewById(R.id.challenge_thumbnail);
        ImageView imageView1 = (ImageView) customView.findViewById(R.id.challenge_play);

        Object s = getItem(position);
        textView.setText((String)s);
        imageView.setImageResource(R.drawable.medal_with_shadow);
        imageView1.setImageResource(R.drawable.com_facebook_button_icon_blue);

        return customView;
    }
}
