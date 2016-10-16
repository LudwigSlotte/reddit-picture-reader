package se.slotte.ludwig.redditpicturereader.picture_feed.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.LikeButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import se.slotte.ludwig.redditpicturereader.R;
import se.slotte.ludwig.redditpicturereader.picture_feed.data.model.Children;
import se.slotte.ludwig.redditpicturereader.picture_feed.data.model.DataInChildren;


public class MyPictureRecyclerViewAdapter extends RecyclerView.Adapter<MyPictureRecyclerViewAdapter.ViewHolder> {
    private List<Children> photoList;
    private Context context;

    public MyPictureRecyclerViewAdapter() {
        photoList = new ArrayList<>();

    }

    public void setItems(List<Children> list) {
        photoList = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.picture_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final DataInChildren data = photoList.get(position).getData();
        final Realm realm = Realm.getDefaultInstance();


        String title = data.getTitle();
        String author = data.getAuthor();

        holder.textViewTitle.setText(title);
        holder.textViewUser.setText(author);

        PictureAdapterHelper helper = new PictureAdapterHelper(holder, realm, context, data);
        helper.showOrHideImageView();
    }


    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView textViewTitle;
        @BindView(R.id.user)
        TextView textViewUser;
        @BindView(R.id.thumbnail)
        ImageView imageView;
        @BindView(R.id.star_button)
        LikeButton starButton;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + textViewUser.getText() + "'";
        }
    }
}
