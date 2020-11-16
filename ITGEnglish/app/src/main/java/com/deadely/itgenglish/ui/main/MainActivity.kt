package com.deadely.itgenglish.ui.main

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.deadely.itgenglish.R
import com.deadely.itgenglish.base.BaseActivity
import com.deadely.itgenglish.extensions.snack
import com.deadely.itgenglish.ui.account.AccountActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : BaseActivity(R.layout.activity_main) {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun initView() {
        setSupportActionBar(toolbar)
    }

    override fun setListeners() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.navView)
        val navController = findNavController(R.id.navController)

        fab.setOnClickListener { view ->
            snack(view, R.string.support_doesnt_work)
        }
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_education, R.id.nav_dictionary, R.id.nav_grammar, R.id.nav_favorite
            ),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_personal_account -> {
//                snack(fab, R.string.personal_account)
                startActivity(Intent(this, AccountActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navController)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun initObserver() {}

    override fun getExtras() {}
}
