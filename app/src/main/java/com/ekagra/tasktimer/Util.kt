package com.ekagra.tasktimer

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class Util {

    fun deletetask(task: Int, context: Context) {

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val sharedPreferencesEditor = sharedPref.edit()
        val tasks : String? = sharedPref.getString("tasks", "")
        val weights : String? =  sharedPref.getString("weights", "")
        val itemsT = tasks!!.split(",").toTypedArray()
        val itemsW = weights!!.split(",").toTypedArray()

        val ctasks = StringBuilder();
        val cweights = StringBuilder();

        for (i in 0..(itemsT.size - 2)) {
            if (i == task) continue
            ctasks.append(itemsT[i])
            ctasks.append(",")
        }

        for (i in 0..(itemsT.size - 2)) {
            if (i == task) continue
            cweights.append(itemsW[i])
            cweights.append(",")
        }

        sharedPreferencesEditor.putString("tasks", ctasks.toString()).commit()
        sharedPreferencesEditor.putString("weights", cweights.toString()).commit()

        val stasks : String? = sharedPref.getString("stasks" + task.toString(), "")
        val sweights : String? =  sharedPref.getString("sweights" + task.toString(), "")
        sharedPreferencesEditor.putString("stasks" + task.toString(), "").commit()
        sharedPreferencesEditor.putString("sweights" + task.toString(), "").commit()

        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
        (context as Activity).finishAffinity()
    }

    fun getTimestampDifference(time: String?): Int {
        var difference = 0;
        val c = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.CANADA)
        sdf.timeZone = TimeZone.getTimeZone("Canada/Pacific")
        val today = c.time
        sdf.format(today)
        val timestamp: Date
        try {
            timestamp = sdf.parse(time)
            difference = Math.round(((timestamp.time - today.time)).toFloat()) //diff on milS
        } catch (e: Exception) {
            difference = 0;
        }
        return difference
    }

    fun getTimer(hr: Int, day: Int, min: Int) :String {

        var c = Calendar.getInstance();
        var today = c.getTime();
        c.setTime(today);
        c.add(Calendar.HOUR, (hr + (24 * day) + min / 60))
        today = c.getTime()
        var sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.CANADA);
        sdf.setTimeZone(TimeZone.getTimeZone("Canada/Pacific"));
        return sdf.format(today)

    }

}