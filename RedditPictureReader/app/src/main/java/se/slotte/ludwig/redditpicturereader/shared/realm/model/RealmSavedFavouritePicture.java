package se.slotte.ludwig.redditpicturereader.shared.realm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ludwigslotte on 09/10/16.
 */

public class RealmSavedFavouritePicture extends RealmObject {
    @PrimaryKey
    private String id;
    private String url;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
