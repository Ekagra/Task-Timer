package com.ekagra.tasktimer

import android.app.Activity
import android.content.Context
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat

class count() {

    fun isOn(context: Context) : Boolean {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val on: Boolean = sharedPref.getBoolean("count", false)
        return on;
    }
}