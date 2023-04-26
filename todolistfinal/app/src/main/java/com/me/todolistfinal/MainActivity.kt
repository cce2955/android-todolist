package com.me.todolistfinal



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import java.util.Date
import android.app.Activity



class MainActivity : AppCompatActivity() {
    private val taskList = mutableListOf<Task>()
    private val EDIT_TASK_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the task list
        taskList.add(Task("Task 1", "Description 1", Date(), 1))
        taskList.add(Task("Task 2", "Description 2", Date(), 2))
        taskList.add(Task("Task 3", "Description 3", Date(), 3))

        // Set up the list view adapter
        val listView = findViewById<ListView>(R.id.task_list_view)
        val adapter = TaskListAdapter(this, taskList)
        listView.adapter = adapter

        // Add a new task
        val newTask = Task("New task", "Description", Date(), 1)
        taskList.add(newTask)
        adapter.notifyDataSetChanged()

        // Edit a task
        val task = taskList[0]
        val intent = Intent(this, EditTaskActivity::class.java)
        intent.putExtra("task", task)
        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val updatedTask = result.data?.getParcelableExtra("updated_task") as? Task



                val index = taskList.indexOf(task)
                updatedTask?.let {
                    taskList[index] = it
                    adapter.notifyDataSetChanged()
                }
            }
        }
        launcher.launch(intent)

        // Update a task
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            // This method is no longer needed with the new registerForActivityResult() method
        }




        // Delete a task
        listView.setOnItemClickListener { _, _, position, _ ->
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Delete task?")
            alertDialogBuilder.setMessage("Are you sure you want to delete this task?")
            alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
                taskList.removeAt(position)
                adapter.notifyDataSetChanged()
            }
            alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            alertDialogBuilder.create().show()
        }
    }
}
