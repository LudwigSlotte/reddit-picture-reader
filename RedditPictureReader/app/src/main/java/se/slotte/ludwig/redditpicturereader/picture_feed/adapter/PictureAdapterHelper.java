package se.slotte.ludwig.redditpicturereader.picture_feed.adapter;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.like.LikeButton;
import com.like.OnLikeListener;

import io.realm.Realm;
import io.realm.RealmResults;
import se.slotte.ludwig.redditpicturereader.R;
import se.slotte.ludwig.redditpicturereader.picture_feed.data.model.DataInChildren;
import se.slotte.ludwig.redditpicturereader.shared.realm.model.RealmSavedFavouritePicture;

/**
 * Created by ludwigslotte on 16/10/16.
 */

class PictureAdapterHelper {
    private MyPictureRecyclerViewAdapter.ViewHolder holder;
    private Realm realm;
    private Context context;
    private DataInChildren data;

    PictureAdapterHelper(MyPictureRecyclerViewAdapter.ViewHolder holder, Realm realm, Context context, DataInChildren data) {
        this.holder = holder;
        this.realm = realm;
        this.context = context;
        this.data = data;
    }

    void showOrHideImageView() {
        if (data.getPreview() == null || data.getThumbnail().equals(context.getString(R.string.reddit_self_tag))) {
            showImageAndStarButton(holder, View.GONE);
        } else {
            initPictureWithLogic();
        }
    }

    private void initPictureWithLogic() {
        String url = data.getPreview().getImages().get(0).getSource().getUrl();
        String id = data.getId();
        showImageAndStarButton(holder, View.VISIBLE);
        loadGlide(url);
        isPictureFavourite(id);
        starButtonLogic(url);
    }

    private void loadGlide(String url) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_error_no_image_24dp)
                .into(holder.imageView);
    }

    private void showImageAndStarButton(MyPictureRecyclerViewAdapter.ViewHolder holder, int visible) {
        holder.imageView.setVisibility(visible);
        holder.starButton.setVisibility(visible);
    }

    private void isPictureFavourite(String id) {
        final RealmResults<RealmSavedFavouritePicture> result =
                realm.where(RealmSavedFavouritePicture.class).equalTo("id", id).findAll();

        if (result.size() > 0) {
            holder.starButton.setLiked(true);
        } else {
            holder.starButton.setLiked(false);
        }
    }

    private void starButtonLogic(final String url) {
        holder.starButton.
                setOnLikeListener(new OnLikeListener() {
                    @Override
                    public void liked(LikeButton likeButton) {

                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                final RealmSavedFavouritePicture realmData = new RealmSavedFavouritePicture();
                                realmData.setId(data.getId());
                                realmData.setUrl(url);
                                realm.copyToRealm(realmData);

                            }
                        });
                    }

                    @Override
                    public void unLiked(LikeButton likeButton) {
                        final RealmResults<RealmSavedFavouritePicture> result =
                                realm.where(RealmSavedFavouritePicture.class).equalTo("id", data.getId()).findAll();
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                result.deleteFromRealm(0);
                            }
                        });
                    }
                });
    }

}
