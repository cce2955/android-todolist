package com.me.todolist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.me.todolist.Task
class TaskAdapter(context: Context, private val tasks: MutableList<Task>) :
    ArrayAdapter<Task>(context, R.layout.list_item, tasks) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        val taskTextView = view.findViewById<TextView>(R.id.task_text_view)
        taskTextView.text = tasks[position].title
        return view
    }
    fun updateTask(position: Int, newTitle: String, newDescription: String) {
        val task = getItem(position)
        task?.let {
            val updatedTask = Task(it.id, newTitle, newDescription) // Create a new Task object with the updated values
            tasks[position] = updatedTask // Replace the original task object in the list
            notifyDataSetChanged()
        }
    }




}
