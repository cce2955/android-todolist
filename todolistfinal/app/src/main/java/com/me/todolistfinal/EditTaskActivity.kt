package com.me.todolistfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.me.todolistfinal.R
import com.me.todolistfinal.Task
import java.util.Date

public class EditTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the content view for the activity
        setContentView(R.layout.activity_edit_task)

        // Get the task object from the intent extras
        val task = intent.getParcelableExtra<Task>("task")

        // TODO: Populate the UI with the task data

        // Set up the save button
        val saveButton = findViewById<Button>(R.id.save_button)
        saveButton.setOnClickListener {
            // TODO: Save the task data and return to the main activity
            val updatedTask = Task("Updated task", "Updated description", Date(), 1)
            val resultIntent = Intent()
            resultIntent.putExtra("updated_task", updatedTask)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        // Set up the cancel button
        val cancelButton = findViewById<Button>(R.id.cancel_button)
        cancelButton.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}
