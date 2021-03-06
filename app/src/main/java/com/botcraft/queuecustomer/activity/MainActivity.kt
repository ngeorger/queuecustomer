package com.botcraft.queuecustomer.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.botcraft.queuecustomer.R
import com.botcraft.queuecustomer.fragment.AccountFragment
import com.botcraft.queuecustomer.fragment.BookingFragment
import com.botcraft.queuecustomer.fragment.LiveTrackFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.nav_book -> {
                val fragment = BookingFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_live -> {
                val fragment = LiveTrackFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_account -> {
                val fragment = AccountFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigationView.selectedItemId = navigationView.menu.getItem(0).itemId
    }
}
