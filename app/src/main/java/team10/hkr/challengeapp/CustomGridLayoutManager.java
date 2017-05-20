package team10.hkr.challengeapp;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by Charlie on 20.05.2017.
 */
public class CustomGridLayoutManager extends LinearLayoutManager {

    private boolean isScrollEnabled = false;

    public CustomGridLayoutManager(Context context) {

        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}