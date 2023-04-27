package com.me.todolist

import android.content.ContentValues
import android.os.Bundle
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.me.todolist.databinding.ActivityMainBinding
import com.me.todolist.Task

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listViewTasks: ListView
    private lateinit var taskAdapter: TaskAdapter
    private val tasks = mutableListOf<Task>()
    private lateinit var taskDbHelper: TaskDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        listViewTasks = findViewById(R.id.listView_tasks)
        taskAdapter = TaskAdapter(this, tasks)
        listViewTasks.adapter = taskAdapter
        taskDbHelper = TaskDbHelper(this)
        binding.fab.setOnClickListener { _ ->
            showAddTaskDialog()
        }


        setupTaskClickListeners()
    }
    private fun showAddTaskDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialogue_edit_task, null)
        builder.setView(dialogView)

        val editTextTaskTitle = dialogView.findViewById<EditText>(R.id.editText_task_title)
        val editTextTaskDescription = dialogView.findViewById<EditText>(R.id.editText_task_description)

        builder.setPositiveButton(R.string.positive_button) { _, _ ->
            val newTaskTitle = editTextTaskTitle.text.toString()
            val newTaskDescription = editTextTaskDescription.text.toString()
            val newTaskId = tasks.size + 1
            val newTask = Task(newTaskId, newTaskTitle, newTaskDescription)

            tasks.add(newTask)
            taskAdapter.notifyDataSetChanged()
        }

        builder.setNegativeButton(R.string.negative_button) { _, _ -> }
        builder.create().show()
    }


    private fun updateTaskInDatabase(task: Task, newTitle: String, newDescription: String) {
        val db = taskDbHelper.writableDatabase
        val values = ContentValues().apply {
            put(TaskContract.TaskEntry.COLUMN_TASK, newTitle)
            put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, newDescription)
        }
        val selection = "\${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(taskId.toString())
        db.update(TaskContract.TaskEntry.TABLE_NAME, values, selection, selectionArgs)
    }

    private fun showEditTaskDialog(task: Task, position: Int) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialogue_edit_task, null)
        builder.setView(dialogView)

        val editTextTaskTitle = dialogView.findViewById<EditText>(R.id.editText_task_title)
        val editTextTaskDescription = dialogView.findViewById<EditText>(R.id.editText_task_description)

        editTextTaskTitle.setText(task.title)
        editTextTaskDescription.setText(task.description)

        builder.setTitle("Edit Task")
        builder.setPositiveButton("Save") { _, _ ->
            val newTitle = editTextTaskTitle.text.toString()
            val newDescription = editTextTaskDescription.text.toString()
            updateTaskInDatabase(task, newTitle, newDescription)
            taskAdapter.updateTask(position, newTitle, newDescription)
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.create().show()
    }
    private fun setupTaskClickListeners() {
        listViewTasks.setOnItemClickListener { _, _, position, _ ->
            val task = tasks[position]

            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val dialogView = inflater.inflate(R.layout.dialog_new_task, null)
            builder.setView(dialogView)

            val editTextTask = dialogView.findViewById<EditText>(R.id.editText_new_task)
            editTextTask.setText(task.title)

            builder.setPositiveButton(R.string.positive_button) { _, _ ->
                val editedTaskTitle = editTextTask.text.toString()
                val updatedTask = Task(task.id, editedTaskTitle, task.description) // Create a new Task object with the updated title
                tasks[position] = updatedTask // Replace the existing task object in the tasks list
                taskAdapter.notifyDataSetChanged()
            }

            builder.setNegativeButton(R.string.negative_button) { dialogInterface, _ ->
                dialogInterface.cancel()
            }

            showEditTaskDialog(task, position)
            val dialog = builder.create()
            dialog.show()
        }

        listViewTasks.setOnItemLongClickListener { _, _, position, _ ->
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Do you want to delete this task?")

            builder.setPositiveButton("Yes") { _, _ ->
                tasks.removeAt(position)
                taskAdapter.notifyDataSetChanged()
            }

            builder.setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.cancel()
            }

            val dialog = builder.create()
            dialog.show()

            true
        }
    }



}