package se.slotte.ludwig.redditpicturereader.favourite_pictures;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;
import se.slotte.ludwig.redditpicturereader.R;
import se.slotte.ludwig.redditpicturereader.favourite_pictures.adapter.MyFavouritePictureRecyclerViewAdapter;
import se.slotte.ludwig.redditpicturereader.shared.realm.model.RealmSavedFavouritePicture;
import se.slotte.ludwig.redditpicturereader.util.EmptyRecyclerView;

public class FavouritePicturesActivity extends AppCompatActivity implements FavouritePictureView {
    @BindView(R.id.favourite_list)
    EmptyRecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.favourite_pictures_list_empty_view)
    RelativeLayout emptyViewForList;

    MyFavouritePictureRecyclerViewAdapter myFavouritePictureRecyclerViewAdapter;
    FavouritePicturePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_pictures);
        ButterKnife.bind(this);
        setToolbarAndEnableBackButton();
        initFabAndOnClickLogic();
        initAdapterAndSetLayoutManager();
        presenter = new FavouritePicturePresenter(this);
        presenter.loadFromRealmDatabase();
    }

    private void setToolbarAndEnableBackButton() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void initFabAndOnClickLogic() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.loadFromRealmDatabase();
                Snackbar.make(recyclerView, R.string.snackbar_refresh_database, Snackbar.LENGTH_SHORT).show();
            }
        });
    }


    private void initAdapterAndSetLayoutManager() {
        myFavouritePictureRecyclerViewAdapter = new MyFavouritePictureRecyclerViewAdapter();
        recyclerView.setEmptyView(emptyViewForList);
        recyclerView.setAdapter(myFavouritePictureRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void loadFromDatabase(RealmResults<RealmSavedFavouritePicture> realmResults) {
        myFavouritePictureRecyclerViewAdapter.setItems(realmResults);
    }
}
