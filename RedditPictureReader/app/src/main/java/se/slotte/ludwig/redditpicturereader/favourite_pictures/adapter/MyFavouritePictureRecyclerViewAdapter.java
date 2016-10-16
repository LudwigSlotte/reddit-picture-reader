package se.slotte.ludwig.redditpicturereader.favourite_pictures.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.like.LikeButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import se.slotte.ludwig.redditpicturereader.R;
import se.slotte.ludwig.redditpicturereader.shared.realm.model.RealmSavedFavouritePicture;

/**
 * Created by ludwigslotte on 15/10/16.
 */

public class MyFavouritePictureRecyclerViewAdapter extends RecyclerView.Adapter<MyFavouritePictureRecyclerViewAdapter.ViewHolder> {
    private List<RealmSavedFavouritePicture> photoList;
    private Context context;

    public MyFavouritePictureRecyclerViewAdapter() {
        photoList = new ArrayList<>();
    }

    public void setItems(List<RealmSavedFavouritePicture> list) {
        photoList = list;
        notifyDataSetChanged();
    }

    @Override
    public MyFavouritePictureRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favourite_picture_item, parent, false);
        context = parent.getContext();
        return new MyFavouritePictureRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyFavouritePictureRecyclerViewAdapter.ViewHolder holder, final int position) {
        final Realm realm = Realm.getDefaultInstance();
        final RealmSavedFavouritePicture picture = photoList.get(position);

        holder.thumbnail.setVisibility(View.VISIBLE);
        holder.starButton.setVisibility(View.VISIBLE);
        holder.starButton.setLiked(true);

        FavouritePictureAdapterHelper helper = new FavouritePictureAdapterHelper(holder, picture);
        helper.loadGlide(context);
        helper.starButtonLogic(this, photoList, position, realm);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        @BindView(R.id.thumbnail)
        ImageView thumbnail;
        @BindView(R.id.star_button)
        LikeButton starButton;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;
        }

    }
}