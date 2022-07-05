package com.mbs.mynotes.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NotesDataBase (context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION) {
        companion object{
            private const val NAME = "Notes"
            private const val VERSION = 1
        }

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL("CREATE TABLE "+ DataBaseConstants.TABLE_NAME +" (" +
                    DataBaseConstants.ID + " integer primary key autoincrement, " +
                    DataBaseConstants.TITLE + " text, " +
                    DataBaseConstants.CONTENT + " text);")
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        }
    }