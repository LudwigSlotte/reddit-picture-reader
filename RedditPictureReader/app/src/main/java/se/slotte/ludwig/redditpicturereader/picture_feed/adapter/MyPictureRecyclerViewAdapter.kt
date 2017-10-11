package se.slotte.ludwig.redditpicturereader.picture_feed.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.like.LikeButton
import io.realm.Realm
import kotlinx.android.synthetic.main.picture_item.view.*
import se.slotte.ludwig.redditpicturereader.R
import se.slotte.ludwig.redditpicturereader.didChange
import se.slotte.ludwig.redditpicturereader.picture_feed.data.model.Children
import kotlin.properties.Delegates


class MyPictureRecyclerViewAdapter : RecyclerView.Adapter<MyPictureRecyclerViewAdapter.ViewHolder>() {
    var photoList by Delegates.didChange(emptyList<Children>()) { notifyDataSetChanged() }

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.picture_item, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = photoList[position].data
        val realm = Realm.getDefaultInstance()

        data?.let {
            holder.textViewTitle?.text = it.title
            holder.textViewUser?.text = it.author
            PictureAdapterHelper(holder, realm, context, it).showOrHideImageView()
        }
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        var textViewTitle: TextView? = view.title
        var textViewUser: TextView? = view.user
        var imageView: ImageView? = view.thumbnail
        var starButton: LikeButton? = view.star_button
    }
}
