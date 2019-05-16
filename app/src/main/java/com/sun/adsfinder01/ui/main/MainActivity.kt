package com.sun.adsfinder01.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.sun.adsfinder01.R
import com.sun.adsfinder01.data.model.User
import com.sun.adsfinder01.ui.home.HomeFragment
import com.sun.adsfinder01.ui.save.PlaceSaveFragment
import com.sun.adsfinder01.ui.search.SearchFragment
import kotlinx.android.synthetic.main.app_bar_home.bottomNavigation
import kotlinx.android.synthetic.main.app_bar_home.root_search
import kotlinx.android.synthetic.main.app_bar_home.toolbar
import kotlinx.android.synthetic.main.app_bar_home.viewPagerHome
import kotlinx.android.synthetic.main.nav_header_home.view.imageViewUserImage
import kotlinx.android.synthetic.main.nav_header_home.view.textUserName

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener, OnClickListener {

    private val user by lazy { intent.getParcelableExtra<User>(EXTRA_USER) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()
        setupUserProfile()
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_like -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.drawer_layout, PlaceSaveFragment())
                    .addToBackStack("")
                    .commit()
            }

            R.id.nav_contract -> {
            }

            R.id.nav_user_profile -> {
            }

            R.id.nav_sign_out -> {
            }

            R.id.nav_share -> {
            }

            R.id.nav_send -> {
            }

            R.id.action_home -> {
                viewPagerHome?.currentItem = 0
                return true
            }

            R.id.action_here -> {
            }

            R.id.action_navigation -> {
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.root_search -> goToSearch()
        }
    }

    private fun initComponents() {
        initNavigationDrawer()
        initBottomNavigation()
        setupViewPager()
        root_search.setOnClickListener(this)
    }

    private fun initNavigationDrawer() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
    }

    private fun initBottomNavigation() {
        bottomNavigation?.setOnNavigationItemSelectedListener(this)
    }

    private fun setupViewPager() {
        val adapter = MainAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment.newInstance(user))
        viewPagerHome?.adapter = adapter
    }

    private fun setupUserProfile() {
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val view = navigationView.inflateHeaderView(R.layout.nav_header_home)
        view.imageViewUserImage?.apply {
            Glide.with(this)
                .load(user.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.image_user)
                .into(this)
        }

        view.textUserName?.text = "${user.firstName} ${user.lastName}"
    }

    private fun goToSearch() {
        supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.drawer_layout, SearchFragment.newInstance(user))
            ?.addToBackStack("")
            ?.commit()
    }

    companion object {

        const val EXTRA_USER = "com.sun.adsfinder01.ui.main.EXTRA_USER"

        fun getMainIntent(context: Context, user: User?): Intent {
            val intent = Intent(context, MainActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_USER, user)
            intent.putExtras(bundle)

            return intent
        }
    }
}
