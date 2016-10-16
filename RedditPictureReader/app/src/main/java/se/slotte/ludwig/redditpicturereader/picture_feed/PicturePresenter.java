package se.slotte.ludwig.redditpicturereader.picture_feed;

import java.util.List;

import se.slotte.ludwig.redditpicturereader.picture_feed.data.FetchAllDataPresenter;
import se.slotte.ludwig.redditpicturereader.picture_feed.data.model.Children;

/**
 * Created by ludwigslotte on 16/10/16.
 */

class PicturePresenter implements FetchAllDataPresenter.FetchDataCallback {
    private PictureView view;
    private FetchAllDataPresenter fetchAllDataPresenter = new FetchAllDataPresenter();

    PicturePresenter(PictureView view) {
        this.view = view;
    }

    void fetchPhotos() {
        view.loadingAnimation();
        fetchAllDataPresenter.fetchPhotos(this);
    }

    @Override
    public void onComplete(List<Children> success) {
        view.onSuccess(success);
    }

    @Override
    public void onNetworkError() {
        view.onNetworkError();
    }
}
