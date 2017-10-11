package se.slotte.ludwig.redditpicturereader.favourite_pictures

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_favourite_pictures.*
import kotlinx.android.synthetic.main.content_favourite_pictures.*
import kotlinx.android.synthetic.main.content_favourite_pictures.view.*
import se.slotte.ludwig.redditpicturereader.R
import se.slotte.ludwig.redditpicturereader.favourite_pictures.adapter.MyFavouritePictureRecyclerViewAdapter
import se.slotte.ludwig.redditpicturereader.shared.realm.model.RealmSavedFavouritePicture

class FavouritePicturesActivity : AppCompatActivity(), FavouritePictureView {

    private lateinit var myFavouritePictureRecyclerViewAdapter: MyFavouritePictureRecyclerViewAdapter
    private lateinit var presenter: FavouritePicturePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_pictures)
        setToolbarAndEnableBackButton()
        initFabAndOnClickLogic()
        initAdapter()
        initLayoutManager()
        initPresenter()
    }

    private fun initLayoutManager() {
        with(favourite_list) {
            setEmptyView(favourite_pictures_list_empty_view)
            adapter = (myFavouritePictureRecyclerViewAdapter)
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
        }
    }

    private fun initPresenter() {
        presenter = FavouritePicturePresenter(this)
        presenter.loadFromRealmDatabase()
    }

    private fun setToolbarAndEnableBackButton() {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
    }

    private fun initFabAndOnClickLogic() {
        fab.setOnClickListener {
            presenter.loadFromRealmDatabase()
            Snackbar.make(favourite_list, R.string.snackbar_refresh_database, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun initAdapter() {
        myFavouritePictureRecyclerViewAdapter = MyFavouritePictureRecyclerViewAdapter()
    }

    override fun loadFromDatabase(realmResults: RealmResults<RealmSavedFavouritePicture>) {
        myFavouritePictureRecyclerViewAdapter.photoList = realmResults
    }
}
