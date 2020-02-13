package com.botcraft.queuecustomer.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.botcraft.queuecustomer.R

class SplashActivity : AppCompatActivity() {

    private var PRIVATE_MODE = 0
    private val PREF_NAME = "queue_app"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()!!.hide();
        setContentView(R.layout.activity_splash)

        redirectScreen()
    }

    private fun redirectScreen(){
        Handler().postDelayed({
            val sharedPref: SharedPreferences = getSharedPreferences(PREF_NAME, PRIVATE_MODE)

            if (sharedPref.getString("name", null) == null) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

        }, 1500)
    }
}