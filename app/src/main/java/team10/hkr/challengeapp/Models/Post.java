package team10.hkr.challengeapp.Models;

import java.util.Date;

/**
 * Created by Martin on 01/05/2017.
 */

public class Post {

    private String UUID;
    private String description;
    private Date creationDate;
    private Date updateDate;
    private boolean deleted;
    private int likes;
    private int views;
    private int dailyLikes;
    private int dailyViews;

    public Post(String UUID, String description, Date creationDate, Date updateDate,
                boolean deleted, int likes, int views, int dailyLikes, int dailyViews)

    {
        this.UUID = UUID;
        this.description = description;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.deleted = deleted;
        this.likes = likes;
        this.views = views;
        this.dailyLikes = dailyLikes;
        this.dailyViews = dailyViews;
    }
}
