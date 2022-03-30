package com.ekagra.tasktimer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ExpandableListView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.phishing.example.all.Create_Timer

val util : Util = Util()
val funmode: mode = mode()
lateinit var listView: ExpandableListView
lateinit var btn: Button
lateinit var reset: Button
lateinit var cntT: TextView
lateinit var smode: SwitchCompat
lateinit var scount : SwitchCompat
lateinit var adapter: ExpandableAdapter
var timer : Int = 0

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.list)
        btn = findViewById(R.id.btn)
        reset = findViewById(R.id.reset)
        smode = findViewById(R.id.mode)
        scount = findViewById(R.id.count)
        cntT = findViewById(R.id.countT)

        //ad
        var adView = AdView(this)

        MobileAds.initialize(this) {}

        adView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        getTimer()
        funmode.switch(smode, this)
        if (count().isOn(this))
        {
            scount.isChecked = true
            cntT.setText("1 / X")
        }
        else
        {
            scount.isChecked = false
            cntT.setText("%")
        }

        btn.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, CreateTask::class.java)
            startActivity(intent)
        })

        smode.setOnCheckedChangeListener({ _, isChecked ->

            val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
            val sharedPreferencesEditor = sharedPref.edit()

            if (isChecked) {
                sharedPreferencesEditor.putBoolean("isDarkModeOn", true).apply()
            } else {
                sharedPreferencesEditor.putBoolean("isDarkModeOn", false).apply()
            }
            funmode.switch(smode, this)
        })

        scount.setOnCheckedChangeListener({ _, isChecked ->

            val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
            val sharedPreferencesEditor = sharedPref.edit()

            if (isChecked) {
                sharedPreferencesEditor.putBoolean("count", true).apply()
                val intent = Intent(this,MainActivity::class.java)
                this.startActivity(intent)
                this.finishAffinity()
            } else {
                sharedPreferencesEditor.putBoolean("count", false).apply()
                val intent = Intent(this,MainActivity::class.java)
                this.startActivity(intent)
                this.finishAffinity()
            }
        })

        reset.setOnClickListener(View.OnClickListener {
            val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
            val sharedPreferencesEditor = sharedPref.edit()
            val sListW = sharedPref.getString("weights", "")
            val itemsW = sListW!!.split(",").toTypedArray()
            var i = itemsW.size-1
            sharedPreferencesEditor.putString("tasks", "").commit()
            sharedPreferencesEditor.putString("weights", "").commit()
            sharedPreferencesEditor.putString("timer", "").commit()

            while(i >= 0)
            {
                sharedPreferencesEditor.putString("stasks" + i.toString(), "").commit()
                sharedPreferencesEditor.putString("sweights" + i.toString(), "").commit()
                i--
            }

            val cr = Create_Timer()
            cr.showDialog(this)
        })
  }

    private fun getTimer() {

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val get: String? = sharedPref.getString("timer", "")

        if (get.equals("")) timer = 0
        else {
            timer = util.getTimestampDifference(get)
            count(timer)
        }
    }

    private fun count(time: Int) {

        val timer = findViewById<TextView>(R.id.clock)
        getTasks()

        object : CountDownTimer(time.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timer.setText("" + millisUntilFinished / 1000 / 60 / 60 / 24 + "d" + " " + ((millisUntilFinished / 1000 / 60 / 60) % 24) + "hr" + " " +
                        ((millisUntilFinished / 1000 / 60) % 60) + "min")
            }

            override fun onFinish() {
                timer.setText("timer not set")
            }
        }.start()

    }

    private fun getTasks() {

        val mContext: Context = this
        val tasks: ArrayList<TasksModel> = ArrayList()

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val cListT = sharedPref.getString("tasks", "")
        val cListW = sharedPref.getString("weights", "")
        val itemsT = cListT!!.split(",").toTypedArray()
        val itemsW = cListW!!.split(",").toTypedArray()
        tasks.clear()


        if (itemsW.size > 1) {
            for (i in 0..(itemsW.size - 2)) {

                val node = TasksModel(itemsW[i].toInt(), itemsT[i])

                val tno = "stasks" + i.toString()
                val wno = "sweights" + i.toString()

                val sListT = sharedPref.getString(tno, "")
                val sListW = sharedPref.getString(wno, "")
                val sitemsT = sListT!!.split(",").toTypedArray()
                val sitemsW = sListW!!.split(",").toTypedArray()


                if (sitemsW.size > 1) {
                    for (j in 0..(sitemsW.size - 2)) {
                        node.addSubContent(TasksModel(sitemsW[j].toInt(), sitemsT[j]))
                    }
                }
                tasks.add(node)

                adapter = ExpandableAdapter(mContext, tasks, timer)
                adapter.notifyDataSetChanged()
                listView.setAdapter(adapter)

            }
            }
        }
    }


