package team10.hkr.challengeapp.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Charlie on 14.05.2017.
 */

public class Comment {

    private JSONObject author;
    private String text;
    private String _id;
    private String updated;
    private String created;

    public Comment(JSONObject jsonObject) throws JSONException {
        this.author = jsonObject.has("author") ? jsonObject.getJSONObject("author") : null;
        this.text = jsonObject.has("text")? jsonObject.getString("text") : "";
        this._id = jsonObject.has("_id")? jsonObject.getString("_id") : "";
        this.updated = jsonObject.has("updated")? jsonObject.getString("updated") : "";
        this.created = jsonObject.has("created")? jsonObject.getString("created") : "";
    }

    public JSONObject getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public String get_id() {
        return _id;
    }

    public String getUpdated() {
        return updated;
    }

    public String getCreated() {
        return created;
    }
}
