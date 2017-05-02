package team10.hkr.challengeapp.Controllers;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team10.hkr.challengeapp.R;

/**
 * Created by Charlie on 25.04.2017.
 */

public class Tab2Fresh extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2_primary_fresh, container, false);
        return rootView;
    }
}
