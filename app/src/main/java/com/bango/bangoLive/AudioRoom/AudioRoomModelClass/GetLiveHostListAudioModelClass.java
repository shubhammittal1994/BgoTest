package com.bango.bangoLive.AudioRoom.AudioRoomModelClass;

import java.io.Serializable;
import java.util.ArrayList;

public class GetLiveHostListAudioModelClass implements Serializable {

    public int status;
    public String message;
    public ArrayList<Detail> details;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Detail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<Detail> details) {
        this.details = details;
    }

    public class Detail{
        public String id;
        public String userId;
        public String liveTitle;
        public String signature;
        public String status;
        public String start_date;
        public String start_time;
        public String start_date_time;
        public String end_date;
        public String end_time;
        public String end_date_time;
        public String total_time;
        public String name;
        public String username;
        public String talent_level;
        public String my_level;
        public String image;
        public String posterImage;
        public StarCount star_count;
        public Object followstatus;
        public Object star_status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getLiveTitle() {
            return liveTitle;
        }

        public void setLiveTitle(String liveTitle) {
            this.liveTitle = liveTitle;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getStart_date_time() {
            return start_date_time;
        }

        public void setStart_date_time(String start_date_time) {
            this.start_date_time = start_date_time;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getEnd_date_time() {
            return end_date_time;
        }

        public void setEnd_date_time(String end_date_time) {
            this.end_date_time = end_date_time;
        }

        public String getTotal_time() {
            return total_time;
        }

        public void setTotal_time(String total_time) {
            this.total_time = total_time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getTalent_level() {
            return talent_level;
        }

        public void setTalent_level(String talent_level) {
            this.talent_level = talent_level;
        }

        public String getMy_level() {
            return my_level;
        }

        public void setMy_level(String my_level) {
            this.my_level = my_level;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPosterImage() {
            return posterImage;
        }

        public void setPosterImage(String posterImage) {
            this.posterImage = posterImage;
        }

        public StarCount getStar_count() {
            return star_count;
        }

        public void setStar_count(StarCount star_count) {
            this.star_count = star_count;
        }

        public Object getFollowstatus() {
            return followstatus;
        }

        public void setFollowstatus(Object followstatus) {
            this.followstatus = followstatus;
        }

        public Object getStar_status() {
            return star_status;
        }

        public void setStar_status(Object star_status) {
            this.star_status = star_status;
        }
    }

    public class StarCount{
        public Object coin;

        public Object getCoin() {
            return coin;
        }

        public void setCoin(Object coin) {
            this.coin = coin;
        }
    }


}
