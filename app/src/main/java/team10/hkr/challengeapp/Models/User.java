package team10.hkr.challengeapp.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Martin on 01/05/2017.
 */

public class User {

    private String UUID;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String facebookLink;
    private String twitterHandle;
    private String profileDescription;
    private String city;
    private String country;
    private String profilePictureURL;
    private JSONArray posts;
    private int stars;
    private int champions;
    private int postCount;
    private int commentCount;
    private boolean isAdmin;

    public User(JSONObject jsonObject) throws JSONException {
        this.posts = jsonObject.has("posts") ? jsonObject.getJSONArray("posts") : null;
        this.isAdmin = jsonObject.has("isAdmin") ? jsonObject.getBoolean("isAdmin") : false;
        this.commentCount = jsonObject.has("commentCount") ? jsonObject.getInt("commentCount") : 0;
        this.champions = jsonObject.has("champions") ? jsonObject.getInt("champions") : 0;
        this.stars = jsonObject.has("stars") ? jsonObject.getInt("stars") : 0;
        this.UUID = jsonObject.has("_id") ? jsonObject.getString("_id") : "";
        this.city = jsonObject.has("city") ? jsonObject.getString("city") : "";
        this.userName = jsonObject.has("username") ? jsonObject.getString("username") : "";
        this.country = jsonObject.has("country") ? jsonObject.getString("country") : "";
        this.firstName = jsonObject.has("firstName") ? jsonObject.getString("firstName") : "";
        this.lastName = jsonObject.has("lastName") ? jsonObject.getString("lastName") : "";
        this.email = jsonObject.has("email") ? jsonObject.getString("email") : "";
        this.password = jsonObject.has("password") ? jsonObject.getString("password") : "";
        this.facebookLink = jsonObject.has("facebookLink") ? jsonObject.getString("facebookLink") : "";
        this.profileDescription = jsonObject.has("profileDescription") ? jsonObject.getString("profileDescription") : "";
        this.twitterHandle = jsonObject.has("twitterHandle") ? jsonObject.getString("twitterHandle") : "";
        this.profilePictureURL = jsonObject.has("profileImageURL") ? jsonObject.getString("profileImageURL") : "";
    }

    public String getUUID() { return UUID; }

    public String getUserName() {
        return userName;
    }

    public String getRealName() { return firstName; }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFacebookLink() { return facebookLink; }

    public String getTwitterHandle() { return twitterHandle; }

    public String getProfileDescription() { return profileDescription; }

    public String getCity() { return city; }

    public String getCountry() {return country; }

    public int getStars() {
        return stars;
    }

    public int getChampions() { return champions; }

    public int getPostCount() {
        return postCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setRealName(String realName) { this.firstName = realName; }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public void setFacebookLink(String facebookLink) { this.facebookLink=facebookLink; }

    public void setTwitterHandle(String twitterHandle) { this.twitterHandle = twitterHandle; }

    public void setProfileDescription(String profileDescription) { this.profileDescription = profileDescription; }

    public void setCity(String city) { this.city = city; }

    public void setCountry(String country ) { this.country = country; }

    public void setChampions(int champions) { this.champions = champions; }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfilePictureURL() { return profilePictureURL; }

    public String getLastName() {

        return lastName;
    }

    public JSONArray getPosts() {
        return posts;
    }
}
