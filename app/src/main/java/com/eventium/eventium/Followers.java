package com.eventium.eventium;

/**
 * Created by MacBookProAlvaro on 2/1/17.
 */
public class Followers {
    private Integer follower;
    private Boolean subscribed;

    public Followers(Integer follower, Boolean subscribed){
        this.setFollower(follower);
        this.setSubscribed(subscribed);
    }

    public Integer getFollower() {
        return follower;
    }

    public void setFollower(Integer follower) {
        this.follower = follower;
    }

    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }
}
