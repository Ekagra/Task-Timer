package com.phishing.example.all

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.preference.PreferenceManager
import android.view.View
import android.view.Window
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.ekagra.tasktimer.R
import com.ekagra.tasktimer.Util

class Create_Timer {
    fun showDialog(activity: Activity) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.create_timer)

        val day = dialog.findViewById<View>(R.id.day) as EditText
        val hour = dialog.findViewById<View>(R.id.hour) as EditText
        val min = dialog.findViewById<View>(R.id.min) as EditText
        val dialogButton = dialog.findViewById<View>(R.id.btn) as Button


               dialogButton.setOnClickListener {

                val u  = Util()
                val t :String = u.getTimer(hour.text.toString().toInt(),day.text.toString().toInt(),min.text.toString().toInt())
                val sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)
                val sharedPreferencesEditor = sharedPref.edit()

                sharedPreferencesEditor.putString("timer", t).apply()

                   //restart app
                   val i = activity.baseContext.packageManager.getLaunchIntentForPackage(activity.baseContext.packageName)
                   i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                   i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                   activity.startActivity(i)
                   activity.finish()

                dialog.dismiss()
                 }

        dialog.show()
    }
}