package se.slotte.ludwig.redditpicturereader.picture_feed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.slotte.ludwig.redditpicturereader.R;
import se.slotte.ludwig.redditpicturereader.picture_feed.data.model.Children;
import se.slotte.ludwig.redditpicturereader.picture_feed.data.model.DataInChildren;


public class MyPictureRecyclerViewAdapter extends RecyclerView.Adapter<MyPictureRecyclerViewAdapter.ViewHolder> {
    public static final String TAG = MyPictureRecyclerViewAdapter.class.getName();
    private List<Children> mValues;
    private Context context;

    public MyPictureRecyclerViewAdapter() {
        mValues = new ArrayList<>();
    }

    public void setItems(List<Children> list) {
        Log.d(TAG, "setItems: " + list.size());
        mValues = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_picture_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        DataInChildren data = mValues.get(position).getData();
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(data.getTitle());
        holder.mContentView.setText(data.getAuthor());
        if (data.getUrl().isEmpty() || data.getThumbnail().equals(context.getString(R.string.reddit_self_tag))) {
            holder.thumbnail.setVisibility(View.GONE);
        }
        Glide.with(context)
                .load(data.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_error_no_image_24dp)
                .into(holder.thumbnail);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + mValues.size());
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Children mItem;
        @BindView(R.id.thumbnail)
        ImageView thumbnail;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
