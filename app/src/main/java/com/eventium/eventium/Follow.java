package com.eventium.eventium;

/**
 * Created by MacBookProAlvaro on 2/1/17.
 */
public class Follow {
    private Integer followed;
    private Boolean subscribed;

    public Follow (Integer followed, Boolean subscribed){
        this.setFollowed(followed);
        this.setSubscribed(subscribed);
    }

    public Integer getFollowed() {
        return followed;
    }

    public void setFollowed(Integer followed) {
        this.followed = followed;
    }

    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }
}
