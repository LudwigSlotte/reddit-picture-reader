package se.slotte.ludwig.redditpicturereader.favourite_pictures

import io.realm.RealmResults
import se.slotte.ludwig.redditpicturereader.shared.realm.model.RealmSavedFavouritePicture

 interface FavouritePictureView {
    fun loadFromDatabase(realmResults: RealmResults<RealmSavedFavouritePicture>)
}
