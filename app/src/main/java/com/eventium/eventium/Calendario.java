package com.eventium.eventium;

/**
 * Created by MacBookProAlvaro on 2/12/16.
 */
public class Calendario {
    private Integer eventid;
    private Integer userid;

    public Calendario (Integer eventid, Integer userid){
        this.setEventid(eventid);
        this.setUserid(userid);
    }

    public Integer getEventid() {
        return eventid;
    }

    public void setEventid(Integer eventid) {
        this.eventid = eventid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
