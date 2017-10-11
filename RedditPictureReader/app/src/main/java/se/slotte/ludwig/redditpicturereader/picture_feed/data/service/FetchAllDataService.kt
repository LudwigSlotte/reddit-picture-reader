package se.slotte.ludwig.redditpicturereader.picture_feed.data.service

import retrofit2.http.GET
import rx.Observable
import se.slotte.ludwig.redditpicturereader.picture_feed.data.model.Pics

interface FetchAllDataService {
    @get:GET("/r/pics.json")
    val picturesFromApi: Observable<Pics>
}