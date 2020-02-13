package com.botcraft.queuecustomer.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.botcraft.queuecustomer.R
import com.botcraft.queuecustomer.modal.User
import com.botcraft.queuecustomer.utils.Utils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login.setOnClickListener {
            if (!name.text.toString().isEmpty() &&
                !mobile_number.text.toString().isEmpty() &&
                !age.text.toString().isEmpty() &&
                !gender.text.toString().isEmpty()
            ) {
                addUser(name.text.toString(), mobile_number.text.toString(),age.text.toString(),gender.text.toString())
            } else {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addUser(name: String, mobile: String, age :String, gender :String) {
        val user = User(name, mobile)
        FirebaseDatabase.getInstance().reference.child("users").child(mobile).setValue(user).addOnCompleteListener(
            OnCompleteListener {
                Utils.saveLoginDetails(this@LoginActivity,name,mobile,age,gender)
                gointoApp()
            })
    }

    private fun gointoApp(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
