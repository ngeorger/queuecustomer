package com.botcraft.queuecustomer.utils

import android.content.Context
import android.content.SharedPreferences
import android.view.Gravity
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class Utils {

    companion object {

        private var mDatabase: FirebaseDatabase? = null
        private var PRIVATE_MODE = 0
        private val PREF_NAME = "queue_app"

        fun getDatabase(): FirebaseDatabase {
            if (mDatabase == null) {
                mDatabase = FirebaseDatabase.getInstance()
                mDatabase!!.setPersistenceEnabled(true)
            }
            return mDatabase!!
        }

        fun showToast(context: Context?, message: String?) {
            val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.BOTTOM, 0, 0)
            toast.show()
        }

        fun showLongToast(context: Context?, message: String?) {
            val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
            toast.setGravity(Gravity.BOTTOM, 0, 0)
            toast.show()
        }

        fun saveLoginDetails(context: Context?, name: String?,mobile:String?,age:String?,gender:String?){
            val sharedPref: SharedPreferences = context!!.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
            val editor = sharedPref.edit()
            editor.putString("name", name)
            editor.putString("mobile", mobile)
            editor.putString("age", age)
            editor.putString("gender", gender)
            editor.apply()
        }

        fun getPrefValue(context: Context?,key:String):String?{
            val sharedPref: SharedPreferences = context!!.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
            return sharedPref.getString(key, null)
        }
    }
}