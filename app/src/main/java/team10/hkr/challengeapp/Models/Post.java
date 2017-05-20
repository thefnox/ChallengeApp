package team10.hkr.challengeapp.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Martin on 01/05/2017.
 */

public class Post {

    private JSONArray tags;
    private JSONObject content;
    private String UUID;
    private String description;
    private String creationDate;
    private String updateDate;
    private JSONArray comments;
    private boolean deleted;
    private JSONArray likesUsers;
    private int likes;
    private int views;
    private int dailyLikes;
    private int dailyViews;
    private JSONObject author;
    private boolean isSelected;

    public Post(JSONObject jsonobj) throws JSONException

    {
        this.isSelected = false;
        this.author = jsonobj.has("author") ? jsonobj.getJSONObject("author") : null;
        this.comments = jsonobj.has("comments") ? jsonobj.getJSONArray("comments") : null;
        this.tags = jsonobj.has("tags")? jsonobj.getJSONArray("tags") : null;
        this.content = jsonobj.has("content")? jsonobj.getJSONObject("content") : null;
        this.UUID = jsonobj.has("_id")? jsonobj.getString("_id") : "";
        this.description = jsonobj.has("description")? jsonobj.getString("description") : "";
        this.creationDate =  jsonobj.has("creationDate") ? jsonobj.getString("creationDate") : "";
        this.updateDate = jsonobj.has("updateDate") ? jsonobj.getString("updateDate") : "";
        this.deleted = jsonobj.has("deleted") && jsonobj.getBoolean("deleted");
        this.likesUsers = jsonobj.has("likes") ? jsonobj.getJSONArray("likes") : null;
        this.likes = jsonobj.has("likes") ? jsonobj.getJSONArray("likes").length() : 0;
        this.views = jsonobj.has("views") ? jsonobj.getInt("views") : 0;
        this.dailyLikes = jsonobj.has("dailyLikes") ? jsonobj.getInt("dailyLikes") : 0;
        this.dailyViews = jsonobj.has("dailyViews") ? jsonobj.getInt("dailyViews") : 0;
    }

    public String getUUID() {
        return UUID;
    }

    public String getDescription() {
        return description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public int getLikes() {
        return likes;
    }

    public JSONArray getLikesUsers() { return likesUsers; }

    public int getViews() {
        return views;
    }

    public int getDailyLikes() {
        return dailyLikes;
    }

    public int getDailyViews() {
        return dailyViews;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void setDailyLikes(int dailyLikes) {
        this.dailyLikes = dailyLikes;
    }

    public void setDailyViews(int dailyViews) {
        this.dailyViews = dailyViews;
    }

    public JSONArray getTags() {
        return tags;
    }

    public JSONObject getContent() {
        return content;
    }

    public JSONArray getComments() {
        return comments;
    }

    public JSONObject getAuthor() {
        return author;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String[] getLikesUsersArrayList(JSONArray jsonArray){

        if(jsonArray != null){
            int length = jsonArray.length();
            String[] likesUsersArray = new String[length];

            for (int i = 0; i<jsonArray.length(); i++){
                likesUsersArray[i] = jsonArray.optString(i);

            }
            return likesUsersArray;
        }
        else return null;
    }
}
