package team10.hkr.challengeapp.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Charlie on 21.05.2017.
 */

public class SearchTag {
    private String name;
    private int count;

    public SearchTag(JSONObject jsonObject) throws JSONException {
        name = jsonObject.has("_id") ? jsonObject.getString("_id") : "";
        count = jsonObject.has("count") ? jsonObject.getInt("count") : 0;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }
}
