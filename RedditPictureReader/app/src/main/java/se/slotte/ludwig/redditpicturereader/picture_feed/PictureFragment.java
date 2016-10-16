package se.slotte.ludwig.redditpicturereader.picture_feed;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import se.slotte.ludwig.redditpicturereader.R;
import se.slotte.ludwig.redditpicturereader.picture_feed.adapter.MyPictureRecyclerViewAdapter;
import se.slotte.ludwig.redditpicturereader.picture_feed.data.model.Children;
import se.slotte.ludwig.redditpicturereader.util.EmptyRecyclerView;


public class PictureFragment extends Fragment implements PictureView {
    @BindView(R.id.list)
    EmptyRecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.pictures_list_empty_view)
    RelativeLayout emptyViewForList;

    MyPictureRecyclerViewAdapter myPictureRecyclerViewAdapter;
    PicturePresenter presenter;
    private Handler mHandler = new Handler();
    private static final long TWO_SECONDS = 2000;

    public PictureFragment() {
    }

    @OnClick(R.id.error_button)
    public void errorOnClick() {
        presenter.fetchPhotos();
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

        swipeRefreshListener();
        initAdapterAndSetLayoutManager();

        presenter = new PicturePresenter(this);
        presenter.fetchPhotos();

        return view;
    }


    private void initAdapterAndSetLayoutManager() {
        myPictureRecyclerViewAdapter = new MyPictureRecyclerViewAdapter();
        recyclerView.setEmptyView(emptyViewForList);
        recyclerView.setAdapter(myPictureRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void swipeRefreshListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.fetchPhotos();
            }
        });
    }


    @Override
    public void onNetworkError() {
        myPictureRecyclerViewAdapter.notifyDataSetChanged();
        Snackbar.make(swipeRefreshLayout, R.string.error_network_error_occurred, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess(List<Children> success) {
        myPictureRecyclerViewAdapter.setItems(success);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void loadingAnimation() {
        swipeRefreshLayout.setRefreshing(true);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, TWO_SECONDS);
    }
}
