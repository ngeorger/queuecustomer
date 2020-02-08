package com.botcraft.queuecustomer

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private var PRIVATE_MODE = 0
    private val PREF_NAME = "queue_app"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login.setOnClickListener {

            if (!name.text.toString().isEmpty() &&
                !mobile.text.toString().isEmpty()
            ) {
                addUser(name.text.toString(), mobile.text.toString())
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Please enter name and mobile no", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addUser(name: String, mobile: String) {
        val user = User(name, mobile)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference
        myRef.child("users").child(mobile).setValue(user)

        val sharedPref: SharedPreferences = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        val editor = sharedPref.edit()
        editor.putString("name", name)
        editor.putString("mobile", mobile)
        editor.apply()
    }
}
