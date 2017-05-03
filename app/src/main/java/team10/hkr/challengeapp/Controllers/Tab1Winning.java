package team10.hkr.challengeapp.Controllers;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import team10.hkr.challengeapp.CustomListViewAdapter;
import team10.hkr.challengeapp.R;

/**
 * Created by Charlie on 25.04.2017.
 */

public class Tab1Winning extends Fragment {

    private CustomListViewAdapter customListViewAdapter;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab1_primary_winning, container, false);

        //       ArrayList<HashMap<String,String>> challengeList = new ArrayList<>();

        String[] tagList = {
                "#Bucket",
                "#Mannequin",
                "#Planking",
                "#Farting",
                "#OneFinger"
        };

//        ArrayList<HashMap<String, String>> tagList = new ArrayList<>();

//        for(int i = 0; i < tags.length; i++) {
//            HashMap<String, String> data = new HashMap<>();
//            data.put("tag", tags[i]);
//
//            tagList.add(data);
//        }

        listView = (ListView) view.findViewById(R.id.winning_list);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                tagList
        );

        listView.setAdapter(listViewAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int myPosition = position;
                String itemClickedId = listView.getItemAtPosition(myPosition).toString();
                Toast.makeText(getActivity().getApplicationContext(), "ID Clicked: " + itemClickedId, Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }

}
