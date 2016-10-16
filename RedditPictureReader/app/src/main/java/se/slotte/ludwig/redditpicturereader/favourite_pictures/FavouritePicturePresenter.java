package se.slotte.ludwig.redditpicturereader.favourite_pictures;

import io.realm.Realm;
import io.realm.RealmResults;
import se.slotte.ludwig.redditpicturereader.shared.realm.model.RealmSavedFavouritePicture;

/**
 * Created by ludwigslotte on 16/10/16.
 */

class FavouritePicturePresenter {
    private FavouritePictureView view;

    FavouritePicturePresenter(FavouritePictureView view) {
        this.view = view;
    }

    void loadFromRealmDatabase() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmSavedFavouritePicture> realmResults = realm.where(RealmSavedFavouritePicture.class).findAll();
        view.loadFromDatabase(realmResults);
    }

}
