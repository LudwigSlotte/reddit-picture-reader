package se.slotte.ludwig.redditpicturereader.picture_feed

import se.slotte.ludwig.redditpicturereader.picture_feed.data.model.Children

/**
 * Created by ludwigslotte on 16/10/16.
 */

interface PictureView {
    fun onNetworkError()

    fun onSuccess(success: List<Children>)

    fun startLoading()

    fun onGeneralError()

    fun setOnErrorClickListener()
}
