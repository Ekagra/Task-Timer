package com.ekagra.tasktimer

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat


class mode {

    fun switch(s : SwitchCompat, activity : Activity){

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)
        val isDarkModeOn : Boolean =  sharedPref.getBoolean("isDarkModeOn", false)


        if (isDarkModeOn) {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate.MODE_NIGHT_YES)

            s.isChecked = true

        }

        else {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate.MODE_NIGHT_NO)

            s.isChecked = false
        }
    }

}