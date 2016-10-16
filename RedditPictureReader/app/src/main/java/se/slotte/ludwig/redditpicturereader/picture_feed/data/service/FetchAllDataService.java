package se.slotte.ludwig.redditpicturereader.picture_feed.data.service;

import retrofit2.http.GET;
import rx.Observable;
import se.slotte.ludwig.redditpicturereader.picture_feed.data.model.Pics;

public interface FetchAllDataService {
    @GET("/r/pics.json")
    Observable<Pics> getPicturesFromApi();
}