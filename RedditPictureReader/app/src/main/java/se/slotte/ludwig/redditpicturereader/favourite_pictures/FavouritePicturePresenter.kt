package se.slotte.ludwig.redditpicturereader.favourite_pictures

import io.realm.Realm
import se.slotte.ludwig.redditpicturereader.shared.realm.model.RealmSavedFavouritePicture

class FavouritePicturePresenter(private val view: FavouritePictureView) {

    fun loadFromRealmDatabase() {
        val realm = Realm.getDefaultInstance()
        val realmResults = realm.where(RealmSavedFavouritePicture::class.java).findAll()
        view.loadFromDatabase(realmResults)
    }
}
