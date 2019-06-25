package se.slotte.ludwig.redditpicturereader.shared.realm.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


class RealmSavedFavouritePicture : RealmObject() {
    @PrimaryKey
    val id: String? = null
    val url: String? = null
}
