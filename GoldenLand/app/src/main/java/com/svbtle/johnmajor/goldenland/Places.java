package com.svbtle.johnmajor.goldenland;

import java.util.ArrayList;

/**
 * Created by johnmajor on 7/27/15.
 */
public class Places  {

    private String id;
    private String name;
    private String about;
    private String details;
    private String link;
    private int recommend;
    private double lat;
    private double lgt;
    private ArrayList<String> images;

    //Constructor
    public Places(String id, String name, String about, String link, ArrayList<String> images, String details, int recommend, double lat, double lgt) {
        this.id = id;
        this.name = name;
        this.about = about;
        this.link = link;
        this.images = images;
        this.details = details;
        this.recommend = recommend;
        this.lat = lat;
        this.lgt = lgt;
    }

    //Getter Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLgt() {
        return lgt;
    }

    public void setLgt(double lgt) {
        this.lgt = lgt;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

}

