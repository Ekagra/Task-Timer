package com.ekagra.tasktimer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

var stasksL : MutableList<String> = mutableListOf()
var sweightsL: MutableList<String> = mutableListOf()
var taskno : Int = 0

class CreateSubtask : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_task)
        get();

        val ok_btn : Button = findViewById(R.id.btn)
        ok_btn.setOnClickListener(View.OnClickListener {
            create()
        })
    }

    private fun create() {

        Log.d("CreateSubTask", "Starting")

        val etask : EditText = findViewById(R.id.edit_task)
        val eweight : EditText = findViewById(R.id.edit_weight)

        var getweight = eweight.text.toString()
        val gettask = etask.text.toString()

        if(getweight.isEmpty() || getweight.toInt() >100 || getweight.equals("")) getweight = "100"

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val sharedPreferencesEditor = sharedPref.edit()

        val subno = "stasks" + taskno.toString()
        val wno = "sweights" + taskno.toString()
        val tasks : String? = sharedPref.getString(subno, "")
        val weights : String? =  sharedPref.getString(wno, "")


        stasksL.clear()
        sweightsL.clear()

        stasksL.add(gettask)
        sweightsL.add(getweight)

        val ctasks = StringBuilder();
        val cweights = StringBuilder();

        ctasks.append(tasks)
        cweights.append(weights)

        for (t in stasksL) {
            ctasks.append(t)
            ctasks.append(",")
        }

        for (w in sweightsL) {
            cweights.append(w)
            cweights.append(",")
        }


        sharedPreferencesEditor.putString(subno, ctasks.toString()).commit()
        sharedPreferencesEditor.putString(wno, cweights.toString()).commit()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    public fun get()
    {
        var bundle :Bundle ?=intent.extras
        taskno = bundle!!.getInt("current")

//        val mContext: Context = this
//        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
//        val cListT = sharedPref.getString("tasks", "")
//        val cListW = sharedPref.getString("weights", "")
//        val itemsT = cListT!!.split(",").toTypedArray()
//        val itemsW = cListW!!.split(",").toTypedArray()
//
//
//        if (itemsW.size > 1) {
//                task = TasksModel(itemsW[id].toInt(), itemsT[id])
//                  Toast.makeText(this, "subtask", Toast.LENGTH_LONG).show()
//        }
    }

}