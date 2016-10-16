package se.slotte.ludwig.redditpicturereader.picture_feed;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    MyPictureRecyclerViewAdapter myPictureRecyclerViewAdapter;
    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

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
        fetchPhotos();
        swipeRefreshListener();
        initAdapterAndSetLayoutManager();

        return view;
    }


    private void initAdapterAndSetLayoutManager() {
        myPictureRecyclerViewAdapter = new MyPictureRecyclerViewAdapter();
        recyclerView.setAdapter(myPictureRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void fetchPhotos() {
        FetchAllDataPresenter fetchAllDataPresenter = new FetchAllDataPresenter();
        fetchAllDataPresenter.fetchPhotos(this);
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
        myPictureRecyclerViewAdapter.setItems(success);
        swipeRefreshLayout.setRefreshing(false);
    }


}
