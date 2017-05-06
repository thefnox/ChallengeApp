package team10.hkr.challengeapp.Controllers;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import team10.hkr.challengeapp.CustomListAdapter;
import team10.hkr.challengeapp.R;

/**
 * Created by Charlie on 25.04.2017.
 */

public class Tab1Winning extends Fragment {

    private ListView listView;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab1_primary_winning, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Instead of this we will be gettin our data from the server as an hashmap
            //where we can separate the information as 'thumbnail' 'tag' 'username' or
                // as whatever else we want.
        ArrayList<Object> taglist = new ArrayList<>();
        taglist.add("#Bucket");
        taglist.add("#OneCupTwoGirls");
        taglist.add("#OneFinger");
        taglist.add("#Planking");
        taglist.add("#Mannequin");
        taglist.add("#DontLaugh");
        taglist.add("#CantThinkOFAnyOther");
        taglist.add("#IStillNeedMore");
        taglist.add("#Dummies");
        taglist.add("#ToFill");
        taglist.add("#ThisList");

        //instead of getting 'this' I used getActivity() to get the activity of this fragment
        //If this was a regular activity we would use a simple 'this' or 'Tab1Winning.this'
        CustomListAdapter adapter = new CustomListAdapter(getActivity(), taglist);
        //We found our ListView which is in the tab1_primary_winning layout
        listView = (ListView) view.findViewById(R.id.winning_list);
        listView.setAdapter(adapter);
        //To add action for the list rows on click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int myPosition = position;
                String itemClickedId = listView.getItemAtPosition(myPosition).toString();
                Toast.makeText(getActivity().getApplicationContext(), "ID Clicked: " + itemClickedId, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
