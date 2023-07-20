package com.bango.bangoLive.ModelClasses.Banner;

import java.io.Serializable;

public class BannerImagesModel implements Serializable {

    public String id;
    public String banner;
    public String hyperlink;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBanner() {
        return banner;
    }

    public String getHyperlink() {
        return hyperlink;
    }

    public void setHyperlink(String hyperlink) {
        this.hyperlink = hyperlink;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
