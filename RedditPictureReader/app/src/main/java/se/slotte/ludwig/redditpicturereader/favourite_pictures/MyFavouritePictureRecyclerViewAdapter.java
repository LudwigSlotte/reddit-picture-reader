package se.slotte.ludwig.redditpicturereader.favourite_pictures;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import se.slotte.ludwig.redditpicturereader.R;
import se.slotte.ludwig.redditpicturereader.picture_feed.MyPictureRecyclerViewAdapter;
import se.slotte.ludwig.redditpicturereader.shared.realm.model.RealmSavedFavouritePicture;

/**
 * Created by ludwigslotte on 15/10/16.
 */

public class MyFavouritePictureRecyclerViewAdapter extends RecyclerView.Adapter<MyFavouritePictureRecyclerViewAdapter.ViewHolder> {
    public static final String TAG = MyPictureRecyclerViewAdapter.class.getName();
    private List<RealmSavedFavouritePicture> mValues;
    private Context context;

    public MyFavouritePictureRecyclerViewAdapter() {
        mValues = new ArrayList<>();
    }

    public void setItems(List<RealmSavedFavouritePicture> list) {
        Log.d(TAG, "setItems: " + list.size());
        mValues = list;
        notifyDataSetChanged();
    }

    @Override
    public MyFavouritePictureRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favourite_picture_item, parent, false);
        context = parent.getContext();
        return new MyFavouritePictureRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyFavouritePictureRecyclerViewAdapter.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: ");
        final Realm realm = Realm.getDefaultInstance();
        final RealmSavedFavouritePicture picture = mValues.get(position);
        Log.d(TAG, "onBindViewHolder: " + picture.getId() + " , " + picture.getUrl());
        holder.thumbnail.setVisibility(View.VISIBLE);
        holder.starButton.setVisibility(View.VISIBLE);
        Glide.with(context)
                .load(picture.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_error_no_image_24dp)
                .into(holder.thumbnail);

        final RealmResults<RealmSavedFavouritePicture> result =
                realm.where(RealmSavedFavouritePicture.class).equalTo("id", picture.getId()).findAll();

        if (result.size() > 0) {
            holder.starButton.setLiked(true);
        }


        holder.starButton.
                setOnLikeListener(new OnLikeListener() {
                    @Override
                    public void liked(LikeButton likeButton) {


                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                RealmSavedFavouritePicture realmData = new RealmSavedFavouritePicture();
                                realmData.setId(picture.getId());
                                realmData.setUrl(picture.getUrl());
                                realm.createObject(RealmSavedFavouritePicture.class, picture.getId());

                            }
                        });

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
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(holder.getAdapterPosition(),mValues.size());
                    }
                });


    }

    @Override
    public int getItemCount() {
        //Need to call notifyDataSetChanged on the last item, otherwise bug will occur in notifyItemRemoved
//        if(mValues.size() == 1){
//            notifyDataSetChanged();
//        }
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.thumbnail)
        ImageView thumbnail;
        @BindView(R.id.star_button)
        LikeButton starButton;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;
        }

    }
}