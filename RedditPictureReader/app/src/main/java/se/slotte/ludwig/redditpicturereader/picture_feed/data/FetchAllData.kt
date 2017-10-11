package se.slotte.ludwig.redditpicturereader.picture_feed.data

import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import se.slotte.ludwig.redditpicturereader.picture_feed.data.model.Children
import se.slotte.ludwig.redditpicturereader.picture_feed.data.model.Pics
import se.slotte.ludwig.redditpicturereader.picture_feed.data.service.BASE_URL
import se.slotte.ludwig.redditpicturereader.picture_feed.data.service.FetchAllDataService
import se.slotte.ludwig.redditpicturereader.picture_feed.data.service.ServiceFactory
import java.io.IOException

class FetchAllData {

    fun fetchPhotos(callback: FetchDataCallback) {
        val service = ServiceFactory.createRetrofitService(BASE_URL).create(FetchAllDataService::class.java)
        handlePicturesFromApi(service, callback)
    }

    private fun handlePicturesFromApi(service: FetchAllDataService, callback: FetchDataCallback) {
        service.picturesFromApi
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<Pics>() {
                    override fun onCompleted() {}

                    override fun onError(e: Throwable) {
                        if (e is IOException) {
                            callback.onNetworkError()
                        } else {
                            callback.onGeneralError()
                        }
                    }

                    override fun onNext(response: Pics) {
                        callback.onComplete(response.data?.children)
                    }
                })
    }

}

interface FetchDataCallback {
    fun onComplete(success: List<Children>?)
    fun onNetworkError()
    fun onGeneralError()
}