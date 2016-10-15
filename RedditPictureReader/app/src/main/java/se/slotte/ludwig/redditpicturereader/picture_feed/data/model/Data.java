package se.slotte.ludwig.redditpicturereader.picture_feed.data.model;

import java.util.List;

public class Data  {

    private String modhash;
    private List<Children> children;

    public List<Children> getChildren() {
        return children;
    }

    public void setChildren(List<Children> children) {
        this.children = children;
    }

    public String getModhash() {
        return modhash;
    }

    public void setModhash(String modhash) {
        this.modhash = modhash;
    }





}