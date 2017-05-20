package team10.hkr.challengeapp.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Charlie on 17.05.2017.
 */

public class Tag {

    private String name;
    private String UUID;
    private int rank;

    public Tag(JSONObject jsonObject) throws JSONException {
        name = jsonObject.has("name") ? jsonObject.getString("name") : "";
        UUID = jsonObject.has("_id") ? jsonObject.getString("_id") : "";
        rank = jsonObject.has("rank") ? jsonObject.getInt("rank") : 0;
    }

    public String getName() {
        return name;
    }

    public String getUUID() {
        return UUID;
    }

    public int getRank() {
        return rank;
    }
}
