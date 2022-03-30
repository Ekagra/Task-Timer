package com.ekagra.tasktimer

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.collections.ArrayList

var tasksL : MutableList<String> = mutableListOf()
var weightsL: MutableList<String> = mutableListOf()
val TAG : String = "Create_Task"

class CreateTask : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_task)

        val ok_btn : Button = findViewById(R.id.btn)
        ok_btn.setOnClickListener(View.OnClickListener {
            create()
        })
    }

    private fun create() {

        Log.d("CreateTask", "Starting")

        val etask : EditText = findViewById(R.id.edit_task)
        val eweight : EditText = findViewById(R.id.edit_weight)

        var getweight = eweight.text.toString()
        val gettask = etask.text.toString()

        if(getweight.isEmpty() || getweight.toInt() >100 || getweight.equals("")) getweight = "100"

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val sharedPreferencesEditor = sharedPref.edit()
        val tasks : String? = sharedPref.getString("tasks", "")
        val weights : String? =  sharedPref.getString("weights", "")

        tasksL.clear()
        weightsL.clear()

        tasksL.add(gettask)
        weightsL.add(getweight)

        val ctasks = StringBuilder();
        val cweights = StringBuilder();

        ctasks.append(tasks)
        cweights.append(weights)

        for (t in tasksL) {
            ctasks.append(t)
            ctasks.append(",")
        }

        for (w in weightsL) {
            cweights.append(w)
            cweights.append(",")
        }


        sharedPreferencesEditor.putString("tasks", ctasks.toString()).commit()
        sharedPreferencesEditor.putString("weights", cweights.toString()).commit()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }




//    var sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity())
//    var all_safe = sharedPref.getString("safe_links", "")
//
//
//    if (all_safe.isEmpty())
//    {
//        text.setVisibility(View.VISIBLE)
//    } else
//    {
//        //serialize
//        links = ArrayList<String>()
//        val cList = sharedPref.getString("safe_links", "")
//        val items = cList!!.split(",").toTypedArray()
//        for (i in items.indices) {
//            links.add(items[i])
//        }
//        adapter = LinksAdapter(mContext, R.layout.safe_link, links)
//        listView.setAdapter(adapter)
//    }

}