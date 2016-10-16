package se.slotte.ludwig.redditpicturereader.favourite_pictures.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import se.slotte.ludwig.redditpicturereader.R;
import se.slotte.ludwig.redditpicturereader.shared.realm.model.RealmSavedFavouritePicture;

/**
 * Created by ludwigslotte on 16/10/16.
 */

class FavouritePictureAdapterHelper {

    private MyFavouritePictureRecyclerViewAdapter.ViewHolder holder;
    private RealmSavedFavouritePicture picture;

    FavouritePictureAdapterHelper(MyFavouritePictureRecyclerViewAdapter.ViewHolder holder, RealmSavedFavouritePicture picture) {
        this.holder = holder;
        this.picture = picture;
    }


    void starButtonLogic(final MyFavouritePictureRecyclerViewAdapter myFavouritePictureRecyclerViewAdapter, final List<RealmSavedFavouritePicture> photoList, final int position, final Realm realm) {
        holder.starButton.
                setOnLikeListener(new OnLikeListener() {
                    @Override
                    public void liked(LikeButton likeButton) {
                    }

                    @Override
                    public void unLiked(LikeButton likeButton) {
                        final RealmResults<RealmSavedFavouritePicture> result =
                                realm.where(RealmSavedFavouritePicture.class).equalTo("id", picture.getId()).findAll();
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                result.deleteFromRealm(0);
                            }
                        });
                        myFavouritePictureRecyclerViewAdapter.notifyItemRemoved(position);
                        myFavouritePictureRecyclerViewAdapter.notifyItemRangeChanged(holder.getAdapterPosition(), photoList.size());
                    }
                });
    }

    void loadGlide(Context context) {
        Glide.with(context)
                .load(picture.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_error_no_image_24dp)
                .into(holder.thumbnail);
    }


}
