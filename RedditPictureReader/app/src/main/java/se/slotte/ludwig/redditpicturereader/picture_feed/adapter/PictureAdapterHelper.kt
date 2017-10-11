package se.slotte.ludwig.redditpicturereader.picture_feed.adapter

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.like.LikeButton
import com.like.OnLikeListener
import io.realm.Realm
import se.slotte.ludwig.redditpicturereader.R
import se.slotte.ludwig.redditpicturereader.picture_feed.data.model.DataInChildren
import se.slotte.ludwig.redditpicturereader.shared.realm.model.RealmSavedFavouritePicture

const val ID = "id"

internal class PictureAdapterHelper(private val holder: MyPictureRecyclerViewAdapter.ViewHolder, private val realm: Realm, private val context: Context, private val data: DataInChildren) {

    fun showOrHideImageView() {
        if (data.preview == null || data.thumbnail == context.getString(R.string.reddit_self_tag)) {
            shouldShowImageAndStarButton(false)
        } else {
            handlePicture()
        }
    }

    private fun handlePicture() {
        val url = data.preview?.images?.firstOrNull()?.source?.url
        val id = data.id

        handleImageUrl(url)
        handleFavorite(id)

        shouldShowImageAndStarButton(true)
    }

    private fun handleFavorite(id: String?) {
        id?.let {
            isPictureFavourite(it)
        }
    }

    private fun handleImageUrl(url: String?) {
        url?.let {
            loadGlide(it)
            starButtonLogic(it)
        }
    }

    private fun loadGlide(url: String) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_error_no_image_24dp)
                .into(holder.imageView)
    }

    private fun shouldShowImageAndStarButton(visible: Boolean) {
        holder.imageView?.visibility = if (visible) View.VISIBLE else View.GONE
        holder.starButton?.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun isPictureFavourite(id: String) {
        val result = realm.where(RealmSavedFavouritePicture::class.java).equalTo(ID, id).findAll()

        if (result.size > 0) {
            holder.starButton?.setLiked(true)
        } else {
            holder.starButton?.setLiked(false)
        }
    }

    private fun starButtonLogic(url: String) {
        holder.starButton?.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton) {
                realm.executeTransaction { realm ->
                    val realmData = RealmSavedFavouritePicture()
                    realmData.id = data.id
                    realmData.url = url
                    realm.copyToRealm(realmData)
                }
            }

            override fun unLiked(likeButton: LikeButton) {
                val result = realm.where(RealmSavedFavouritePicture::class.java).equalTo(ID, data.id).findAll()
                realm.executeTransaction { result.deleteFromRealm(0) }
            }
        })
    }

}
