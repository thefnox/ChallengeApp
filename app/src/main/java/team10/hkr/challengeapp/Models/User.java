package team10.hkr.challengeapp.Models;

/**
 * Created by Martin on 01/05/2017.
 */

public class User {

    private String userName;
    private String realName;
    private String email;
    private String password;
    private String UUID;
    private int stars;
    private int postCount;
    private int commentCount;
    private boolean isAdmin;



    public User(String userName, String realName, String email, String password, String UUID, int stars,
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

    }

    public String getUserName() {
        return userName;
    }

    public String getRealName() {
        return realName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUUID() {
        return UUID;
    }

    public int getStars() {
        return stars;
    }

    public int getPostCount() {
        return postCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

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
