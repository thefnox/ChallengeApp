package team10.hkr.challengeapp.Models;

/**
 * Created by Martin on 01/05/2017.
 */

public class User {

    private String UUID;
    private String userName;
    private String realName;
    private String email;
    private String password;
    private String facebookLink;
    private String twitterHandle;
    private String profileDescription;
    private String city;
    private String country;
    private int stars;
    private int champions;
    private int postCount;
    private int commentCount;
    private boolean isAdmin;



    public User(String UUID, String userName, String realName, String email, String password, String facebookLink,
                String twitterHandle, String profileDescription, String city, String country, int stars, int champions,
                int postCount, int commentCount, boolean isAdmin) {

        this.userName = userName;
        this.realName = realName;
        this.email = email;
        this.password = password;
        this.UUID = UUID;
        this.stars = stars;
        this.postCount = postCount;
        this.commentCount = commentCount;
        this.isAdmin = isAdmin;
        this.twitterHandle = twitterHandle;
        this.profileDescription = profileDescription;
        this.champions = champions;
        this.facebookLink = facebookLink;
        this.country = country;
        this.city = city;

    }
    public String getUUID() { return UUID; }

    public String getUserName() {
        return userName;
    }

    public String getRealName() { return realName; }

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

    public void setRealName(String realName) { this.realName = realName; }

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
}
