package com.me.todolistfinal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import java.text.SimpleDateFormat
import java.util.*

class TaskListAdapter(private val context: Context, private val taskList: List<Task>) : BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return taskList.size
    }

    override fun getItem(position: Int): Any {
        return taskList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            // Inflate the list item view if it hasn't been inflated already
            view = inflater.inflate(R.layout.listitemtask, parent, false)

            // Create a new ViewHolder object and store references to the child views
            holder = ViewHolder()
            holder.title = view.findViewById(R.id.task_title)
            holder.description = view.findViewById(R.id.task_description)
            holder.dueDate = view.findViewById(R.id.task_due_date)
            holder.priority = view.findViewById(R.id.task_priority)

            // Store the ViewHolder object in the view's tag for easy access later
            view.tag = holder
        } else {
            // Use the recycled view if it already exists
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        // Populate the view with data from the Task object at the specified position
        val task = taskList[position]

        holder.title?.text = task.title
        holder.description?.text = task.description

        val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
        val dueDate = dateFormat.format(task.dueDate)
        holder.dueDate?.text = dueDate

        holder.priority?.text = task.priority.toString()

        return view
    }


    private class ViewHolder {
        var title: TextView? = null
        var description: TextView? = null
        var dueDate: TextView? = null
        var priority: TextView? = null
    }

}
