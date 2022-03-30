package com.ekagra.tasktimer

import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.core.graphics.createBitmap

class ExpandableAdapter(private val context: Context, private val list: ArrayList<TasksModel>, timer: Int) : BaseExpandableListAdapter()  {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getGroupCount(): Int {
        return list.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return list[groupPosition].subContentSize()
    }

    override fun getGroup(groupPosition: Int): Any {
        return list[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): TasksModel {
        return list[groupPosition].getSubContent(childPosition);
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    fun getN(position: Int) : String {
        return list[position].getNote()
    }

    fun getC(position: Int) : Float {
        return list[position].getClock().toFloat()
    }

    fun getchildN(childPosition: Int, groupPosition: Int) : String {
        return list[groupPosition].getSubContent(childPosition).getNote()
    }

    fun getchildC(childPosition: Int, groupPosition: Int) : Float {
        return list[groupPosition].getSubContent(childPosition).getClock().toFloat()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong();
    }

    override fun hasStableIds(): Boolean {
        return false;
    }

    override fun getGroupView( groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        view = inflater.inflate(R.layout.task_layout, parent, false)

        holder = ViewHolder()
        holder.title = view.findViewById(R.id.task) as TextView
        holder.clock = view.findViewById(R.id.clock) as TextView
        holder.note = view.findViewById(R.id.note) as TextView
        holder.create_subtask = view.findViewById(R.id.create_subtask) as ImageView
        holder.delete = view.findViewById(R.id.delete_task) as ImageView

        holder.create_subtask.setOnClickListener(View.OnClickListener {
            val intent = Intent(context,CreateSubtask::class.java)
            intent.putExtra("current", groupPosition)
            context.startActivity(intent)

        })

        holder.delete.setOnClickListener(View.OnClickListener {
            Util().deletetask(groupPosition,context)
        })

        view.tag = holder

        holder.title.setText("TASK" + "#" + (groupPosition + 1))
        holder.note.setText(getN(groupPosition))

        var temp: Float = (getC(groupPosition) / 100f)
        if (count().isOn(context)) temp = (1f / getGroupCount() * 1f)
        val time: Float = (timer * temp)

        object : CountDownTimer(time.toInt().toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                holder.clock.setText(
                    "" + (millisUntilFinished / 1000 / 60 / 60 / 24) + " d " + ((millisUntilFinished / 1000 / 60 / 60) % 24) + " hr " +
                            ((millisUntilFinished / 1000 / 60) % 60) + " min " + ((millisUntilFinished / 1000) % 60) + " sec"
                )
            }

            override fun onFinish() {
                holder.clock.setText("timer not set")
            }
        }.start()

        return view
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val view: View
        val holder: ViewHolder

        view = inflater.inflate(R.layout.subtask_layout, parent, false)

        holder = ViewHolder()
        holder.title = view.findViewById(R.id.sub_task) as TextView
        holder.clock = view.findViewById(R.id.clock) as TextView
        holder.note = view.findViewById(R.id.sub_note) as TextView
        Log.d("subb", "" + childPosition)

        view.tag = holder

        holder.title.setText("SUBTASK" + "#" + (childPosition + 1))
        holder.note.setText(getchildN(childPosition, groupPosition))

        var temp: Float = (getchildC(childPosition, groupPosition) / 100f)
        if (count().isOn(context))  temp = (1f / getChildrenCount(groupPosition) * 1f)
        val time: Float = (timer * temp)

        object : CountDownTimer(time.toInt().toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                holder.clock.setText(
                    "" + (millisUntilFinished / 1000 / 60 / 60 / 24) + " d " + ((millisUntilFinished / 1000 / 60 / 60) % 24) + " hr " +
                            ((millisUntilFinished / 1000 / 60) % 60) + " min " + ((millisUntilFinished / 1000) % 60) + " sec"
                )
            }

            override fun onFinish() {
                holder.clock.setText("timer not set")
            }
        }.start()

        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false;
    }

    private class ViewHolder {
        lateinit var title: TextView
        lateinit var clock: TextView
        lateinit var note: TextView
        lateinit var create_subtask : ImageView;
        lateinit var delete : ImageView;
    }
}
