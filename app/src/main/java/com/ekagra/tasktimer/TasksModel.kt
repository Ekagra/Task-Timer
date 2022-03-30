package com.ekagra.tasktimer


/**
 * Created by User on 8/22/2017.
 */
class TasksModel(var clk: Int, var nn: String)  {

    private val subList: MutableList<TasksModel> = ArrayList()

    fun addSubContent(subContent: TasksModel) {
        subList.add(subContent)
    }

    fun subContentSize(): Int {
        return subList.size
    }

    fun getSubContent(position: Int): TasksModel {
        return subList[position]
    }

    fun getNote(): String {
        return nn
    }

    fun getClock(): Int {
        return clk
    }
}