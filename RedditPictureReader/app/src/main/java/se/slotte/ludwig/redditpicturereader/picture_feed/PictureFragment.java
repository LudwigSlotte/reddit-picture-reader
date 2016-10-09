package se.slotte.ludwig.redditpicturereader.picture_feed;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.slotte.ludwig.redditpicturereader.R;
import se.slotte.ludwig.redditpicturereader.picture_feed.data.FetchAllDataPresenter;
import se.slotte.ludwig.redditpicturereader.picture_feed.data.model.Children;


public class PictureFragment extends Fragment implements FetchAllDataPresenter.FetchDataCallback {
    public static final String TAG = PictureFragment.class.getName();
    private static final String BUNDLE_RECYCLER_LAYOUT = "bundle_reycler_layout";
    MyPictureRecyclerViewAdapter myPictureRecyclerViewAdapter;
    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private Parcelable savedRecyclerLayoutState;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PictureFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture_list, container, false);
        ButterKnife.bind(this, view);
        Log.d(TAG, "onCreateView: ");
        if(savedInstanceState == null) {
            Log.d(TAG, "onCreateView: saved == Null");
            fetchPhotos();
        } else {
            Log.d(TAG, "onCreateView: saved != null");
            restoreLayoutManagerPosition();
        }
        swipeRefreshListener();
        return view;
    }

    private void fetchPhotos() {
        FetchAllDataPresenter fetchAllDataPresenter = new FetchAllDataPresenter();
        fetchAllDataPresenter.fetchPhotos(this);
    }

    private void swipeRefreshListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                fetchPhotos();
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onComplete(List<Children> success) {
        myPictureRecyclerViewAdapter = new MyPictureRecyclerViewAdapter();
        myPictureRecyclerViewAdapter.setItems(success);
        recyclerView.setAdapter(myPictureRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout.setRefreshing(false);
    }


    /**
     * This is a method for Fragment.
     * You can do the same in onCreate or onRestoreInstanceState
     */
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedInstanceState != null){
            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, recyclerView.getLayoutManager().onSaveInstanceState());
    }

    private void restoreLayoutManagerPosition() {
        if (savedRecyclerLayoutState != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }



}
