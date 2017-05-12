package team10.hkr.challengeapp.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Charlie on 12.05.2017.
 */

public class Pokemon {
    private int id;
    private String name;
    private int base_experience;
    private int height;
    private boolean is_default;
    private int order;
    private int weight;
    private JSONArray abilities;

    public Pokemon(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.has("id") ? jsonObject.getInt("id") : 0;
        this.name = jsonObject.has("name") ? jsonObject.getString("name") : "";
        this.base_experience = jsonObject.has("base_experience") ? jsonObject.getInt("base_experience") : 0;
        this.height = jsonObject.has("height") ? jsonObject.getInt("height") : 0;
        this.is_default = jsonObject.has("is_default") ? jsonObject.getBoolean("is_default") : false;
        this.order = jsonObject.has("order") ? jsonObject.getInt("order") : 0;
        this.weight = jsonObject.has("weight") ? jsonObject.getInt("weight") : 0;
        this.abilities = jsonObject.has("abilities") ? jsonObject.getJSONArray("abilities") : null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBase_experience() {
        return base_experience;
    }

    public int getHeight() {
        return height;
    }

    public boolean is_default() {
        return is_default;
    }

    public int getOrder() {
        return order;
    }

    public int getWeight() {
        return weight;
    }

    public JSONArray getAbilities() {
        return abilities;
    }
}
