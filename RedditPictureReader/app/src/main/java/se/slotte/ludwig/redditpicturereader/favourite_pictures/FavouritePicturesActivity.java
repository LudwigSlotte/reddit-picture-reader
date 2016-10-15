package se.slotte.ludwig.redditpicturereader.favourite_pictures;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import se.slotte.ludwig.redditpicturereader.R;

public class FavouritePicturesActivity extends AppCompatActivity {

    private static final String TAG = FavouritePicturesActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_pictures);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//        Realm realm = Realm.getDefaultInstance();
//        List<RealmDataInChildren> list=  RealmController.getInstance().getDataInChildrens();
//        Log.d(TAG, "onCreate: " + list.get(0));
    }
}
