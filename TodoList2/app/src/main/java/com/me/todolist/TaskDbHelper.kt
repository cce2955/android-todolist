package com.me.todolist

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TaskDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "tasks.db"
        const val DATABASE_VERSION = 2
    }

    override fun onCreate(db: SQLiteDatabase) {
        val SQL_CREATE_TASKS_TABLE = "CREATE TABLE ${TaskContract.TaskEntry.TABLE_NAME} (" +
                "${TaskContract.TaskEntry._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${TaskContract.TaskEntry.COLUMN_TASK} TEXT NOT NULL," +
                "${TaskContract.TaskEntry.COLUMN_DESCRIPTION} TEXT)"

        db.execSQL(SQL_CREATE_TASKS_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val SQL_DELETE_TASKS_TABLE = "DROP TABLE IF EXISTS ${TaskContract.TaskEntry.TABLE_NAME};"
        db.execSQL(SQL_DELETE_TASKS_TABLE)
        onCreate(db)
    }


}
