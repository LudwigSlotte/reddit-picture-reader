package se.slotte.ludwig.redditpicturereader.picture_feed

import se.slotte.ludwig.redditpicturereader.picture_feed.data.FetchAllData
import se.slotte.ludwig.redditpicturereader.picture_feed.data.FetchDataCallback
import se.slotte.ludwig.redditpicturereader.picture_feed.data.model.Children

class PicturePresenter(val view: PictureView) : FetchDataCallback {
    private val fetchAllDataPresenter = FetchAllData()

    fun init(){
        view.setOnErrorClickListener()
        fetchPhotos()
    }

    fun fetchPhotos() {
        view.startLoading()
        fetchAllDataPresenter.fetchPhotos(this)
    }

    override fun onComplete(success: List<Children>?) {
        view.onSuccess(success)
    }

    override fun onNetworkError() {
        view.onNetworkError()
    }

    override fun onGeneralError() {
        view.onGeneralError()
    }
}
