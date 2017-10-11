package se.slotte.ludwig.redditpicturereader.favourite_pictures.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.like.LikeButton
import io.realm.Realm
import kotlinx.android.synthetic.main.favourite_picture_item.view.*
import se.slotte.ludwig.redditpicturereader.R
import se.slotte.ludwig.redditpicturereader.didChange
import se.slotte.ludwig.redditpicturereader.favourite_pictures.adapter.MyFavouritePictureRecyclerViewAdapter.ViewHolder
import se.slotte.ludwig.redditpicturereader.shared.realm.model.RealmSavedFavouritePicture
import kotlin.properties.Delegates


class MyFavouritePictureRecyclerViewAdapter : RecyclerView.Adapter<ViewHolder>() {
    private lateinit var context: Context
    var photoList by Delegates.didChange(emptyList<RealmSavedFavouritePicture>()) { notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favourite_picture_item, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val realm = Realm.getDefaultInstance()
        val picture = photoList[position]

        holder.apply {
            thumbnail?.visibility = View.VISIBLE
            starButton?.visibility = View.VISIBLE
            starButton?.setLiked(true)
        }

        FavouritePictureAdapterHelper(holder, picture).apply {
            loadGlide(context)
            starButtonLogic(this@MyFavouritePictureRecyclerViewAdapter, photoList, position, realm)
        }
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var thumbnail: ImageView? = view.thumbnail
        internal var starButton: LikeButton? = view.star_button
    }
}