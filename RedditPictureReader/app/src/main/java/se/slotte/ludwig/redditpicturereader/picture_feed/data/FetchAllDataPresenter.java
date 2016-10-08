package se.slotte.ludwig.redditpicturereader.picture_feed.data;

import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import se.slotte.ludwig.redditpicturereader.picture_feed.data.model.Children;
import se.slotte.ludwig.redditpicturereader.picture_feed.data.model.Pics;
import se.slotte.ludwig.redditpicturereader.picture_feed.data.service.FetchAllDataService;
import se.slotte.ludwig.redditpicturereader.picture_feed.data.service.ServiceFactory;

/**
 * Created by ludwigslotte on 08/10/16.
 */

public class FetchAllDataPresenter {
    private FetchAllDataService service;
    public interface FetchDataCallback {
        void onComplete(List<Children> success);
    }

    public void fetchCategories(@Nullable final FetchDataCallback callback) {
        service = ServiceFactory.createRetrofitService(ServiceFactory.BASE_URL).create(FetchAllDataService.class);

        service.getPicturesFromApi()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Pics>() {
                    @Override
                    public final void onCompleted() {

                    }

                    @Override
                    public final void onError(Throwable e) {
                        Log.e("MServicesFragment", e.getMessage());
                        if (callback != null) {
                        }
                    }

                    @Override
                    public final void onNext(Pics response) {
                        if (callback != null) {
                            callback.onComplete(response.getData().getChildren());
                        }

                    }
                });
    }

}
