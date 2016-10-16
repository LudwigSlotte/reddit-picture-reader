package se.slotte.ludwig.redditpicturereader.favourite_pictures;

import io.realm.RealmResults;
import se.slotte.ludwig.redditpicturereader.shared.realm.model.RealmSavedFavouritePicture;

/**
 * Created by ludwigslotte on 16/10/16.
 */

interface FavouritePictureView {
    void loadFromDatabase(RealmResults<RealmSavedFavouritePicture> realmResults);
}
