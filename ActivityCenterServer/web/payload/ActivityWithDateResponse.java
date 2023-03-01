package com.ActivityCenterServer.web.payload;

import com.ActivityCenterServer.model.Activity;

public class ActivityWithDateResponse {

    private String date;
    private String time;

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Integer idEvent) {
        this.idEvent = idEvent;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    private Integer idEvent;
    private Activity activity;

    public ActivityWithDateResponse(String date, String time, Integer idEvent, Activity activity) {
        this.time = time;
        this.date = date;
        this.idEvent = idEvent;
        this.activity = activity;
    }

}
