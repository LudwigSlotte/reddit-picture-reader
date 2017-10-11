package se.slotte.ludwig.redditpicturereader.favourite_pictures.adapter

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.like.LikeButton
import com.like.OnLikeListener
import io.realm.Realm
import se.slotte.ludwig.redditpicturereader.R
import se.slotte.ludwig.redditpicturereader.picture_feed.adapter.ID
import se.slotte.ludwig.redditpicturereader.shared.realm.model.RealmSavedFavouritePicture


internal class FavouritePictureAdapterHelper(private val holder: MyFavouritePictureRecyclerViewAdapter.ViewHolder, private val picture: RealmSavedFavouritePicture) {


    fun starButtonLogic(myFavouritePictureRecyclerViewAdapter: MyFavouritePictureRecyclerViewAdapter, photoList: List<RealmSavedFavouritePicture>, position: Int, realm: Realm) {
        holder.starButton?.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton) {}

            override fun unLiked(likeButton: LikeButton) {
                removeImageFromRealm()
                updateAdapter()
            }

            private fun removeImageFromRealm() {
                val result = realm.where(RealmSavedFavouritePicture::class.java).equalTo(ID, picture.id).findAll()
                realm.executeTransaction { result.deleteFromRealm(0) }
            }

            private fun updateAdapter() {
                myFavouritePictureRecyclerViewAdapter.apply {
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(holder.adapterPosition, photoList.size)
                }
            }
        })
    }

    fun loadGlide(context: Context) {
        Glide.with(context)
                .load(picture.url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_error_no_image_24dp)
                .into(holder.thumbnail)
    }
}
