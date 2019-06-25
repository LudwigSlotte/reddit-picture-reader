package se.slotte.ludwig.redditpicturereader.picture_feed

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_picture_list.*
import se.slotte.ludwig.redditpicturereader.R
import se.slotte.ludwig.redditpicturereader.picture_feed.adapter.MyPictureRecyclerViewAdapter
import se.slotte.ludwig.redditpicturereader.picture_feed.data.model.Children

class PictureFragment : Fragment(), PictureView {
    private val myPictureRecyclerViewAdapter: MyPictureRecyclerViewAdapter by lazy {
        MyPictureRecyclerViewAdapter()
    }
    private val presenter: PicturePresenter by lazy {
        PicturePresenter(this).apply {
            init()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayoutManager()
        swipeRefreshListener()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_picture_list, container, false)
    }

    private fun initLayoutManager() {
        with(list) {
            setEmptyView(pictures_list_empty_view)
            layoutManager = LinearLayoutManager(context)
            adapter = myPictureRecyclerViewAdapter
        }
    }

    private fun swipeRefreshListener() {
        swipeRefreshLayout?.setOnRefreshListener { presenter.fetchPhotos() }
    }

    override fun setOnErrorClickListener() {
        error_button?.setOnClickListener {
            presenter.fetchPhotos()
        }
    }

    override fun onNetworkError() {
        myPictureRecyclerViewAdapter.notifyDataSetChanged()
        swipeRefreshLayout.createSnackbar(R.string.error_network_error_occurred, Snackbar.LENGTH_LONG)
    }

    override fun onSuccess(listOfChildren: List<Children>) {
        myPictureRecyclerViewAdapter.photoList = listOfChildren
        swipeRefreshLayout?.isRefreshing = false
    }

    override fun startLoading() {
        swipeRefreshLayout?.isRefreshing = true
    }

    override fun onGeneralError() {
        myPictureRecyclerViewAdapter.notifyDataSetChanged()
        swipeRefreshLayout.createSnackbar(R.string.error_no_pictures, Snackbar.LENGTH_LONG)
    }
}
