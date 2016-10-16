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
import se.slotte.ludwig.redditpicturereader.picture_feed.data.FetchAllDataPresenter;
import se.slotte.ludwig.redditpicturereader.picture_feed.data.model.Children;
import se.slotte.ludwig.redditpicturereader.util.EmptyRecyclerView;


public class PictureFragment extends Fragment implements FetchAllDataPresenter.FetchDataCallback, PictureView {
    public static final String TAG = PictureFragment.class.getName();
    private static final long TWO_SECONDS = 2000;
    MyPictureRecyclerViewAdapter myPictureRecyclerViewAdapter;
    @BindView(R.id.list)
    EmptyRecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.pictures_list_empty_view)
    RelativeLayout emptyViewForList;
    private Handler mHandler = new Handler();
    PicturePresenter presenter;


    @OnClick(R.id.error_button)
    public void errorOnClick(){
        fetchPhotos();
    }

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
        presenter = new PicturePresenter(this);
        swipeRefreshListener();
        initAdapterAndSetLayoutManager();
        fetchPhotos();
        return view;
    }


    private void initAdapterAndSetLayoutManager() {
        recyclerView.setEmptyView(emptyViewForList);
        myPictureRecyclerViewAdapter = new MyPictureRecyclerViewAdapter();
        recyclerView.setAdapter(myPictureRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void fetchPhotos() {

    }

    private void handleRefreshIconOnLayout() {
        swipeRefreshLayout.setRefreshing(true);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, TWO_SECONDS);
    }

    private void swipeRefreshListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPhotos();

            }
        });
    }


    @Override
    public void onComplete(List<Children> success) {

    }


    @Override
    public void onNetworkError() {
        myPictureRecyclerViewAdapter.notifyDataSetChanged();
        Snackbar snackbar = Snackbar.make(swipeRefreshLayout, "A network error occurred.", Snackbar.LENGTH_LONG);
        snackbar.show();    }

    @Override
    public void onSuccess(List<Children> success) {
        myPictureRecyclerViewAdapter.setItems(success);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void loadPictures() {
        handleRefreshIconOnLayout();
        FetchAllDataPresenter fetchAllDataPresenter = new FetchAllDataPresenter();
        fetchAllDataPresenter.fetchPhotos(this);
    }
}
