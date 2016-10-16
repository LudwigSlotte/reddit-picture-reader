package se.slotte.ludwig.redditpicturereader.picture_feed;

import java.util.List;

import se.slotte.ludwig.redditpicturereader.picture_feed.data.model.Children;

/**
 * Created by ludwigslotte on 16/10/16.
 */

public interface PictureView {
    void onNetworkError();

    void onSuccess(List<Children> success);

    void loadPictures();
}
