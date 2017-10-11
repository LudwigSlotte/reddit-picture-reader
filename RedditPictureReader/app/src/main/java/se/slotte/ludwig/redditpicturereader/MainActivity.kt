package se.slotte.ludwig.redditpicturereader

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import se.slotte.ludwig.redditpicturereader.favourite_pictures.FavouritePicturesActivity
import se.slotte.ludwig.redditpicturereader.picture_feed.PictureFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        initDrawerToggle()
        initNavigationView()
        initFragment()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun initNavigationView() {
        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun initDrawerToggle() {
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout?.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun initFragment() {
        val fragment = PictureFragment()
        fragment.retainInstance = true

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.content_main, fragment)
        }.commit()

    }

    override fun onBackPressed() {
        drawer_layout?.let {
            if (it.isDrawerOpen(GravityCompat.START)) {
                it.closeDrawer(GravityCompat.START)
            } else {
                super.onBackPressed()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.navigation_favourite_pictures) {
            val myIntent = Intent(this@MainActivity, FavouritePicturesActivity::class.java)
            this@MainActivity.startActivity(myIntent)
        } else if (id == R.id.nav_send) {
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse(getString(R.string.mail_to_data))
            startActivity(Intent.createChooser(emailIntent, getString(R.string.menu_send_feedback)))
        }

        drawer_layout?.closeDrawer(GravityCompat.START)
        return true
    }
}
