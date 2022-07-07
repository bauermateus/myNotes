package com.mbs.mynotes.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import com.mbs.mynotes.model.NotesModel

class NotesRepository private constructor(private val context: Context) {

    private val notesDataBase = NotesDataBase(context)

    //SINGLETON ->
    companion object {
        private lateinit var repository: NotesRepository

        fun getInstance(context: Context): NotesRepository {
            if (!Companion::repository.isInitialized) {
                repository = NotesRepository(context)
            }
            return repository
        }
    }

    fun insert(note: NotesModel): Boolean {
        val list = mutableListOf<NotesModel>()
        return try {
            val db = notesDataBase.writableDatabase

            val values = ContentValues()
            values.put(DataBaseConstants.TITLE, note.title)
            values.put(DataBaseConstants.CONTENT, note.content)
            db.insert(DataBaseConstants.TABLE_NAME, null, values)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun delete(id: Int): Boolean {
        return try {
            val db = notesDataBase.writableDatabase
            val selection = DataBaseConstants.ID + " = args"
            val args = arrayOf(id.toString())
            db.delete(DataBaseConstants.TABLE_NAME, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    @SuppressLint("Range")
    fun getNotes(): List<NotesModel> {
        val list = mutableListOf<NotesModel>()
        try {
            val db = notesDataBase.readableDatabase

            val projection =
                arrayOf(DataBaseConstants.ID, DataBaseConstants.TITLE, DataBaseConstants.CONTENT)

            val cursor = db.query(DataBaseConstants.TABLE_NAME, projection,
                null, null, null, null, null)
            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.ID))
                    val title = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TITLE))
                    val content = cursor.getString(cursor.getColumnIndex(DataBaseConstants.CONTENT))
                    list.add(NotesModel(id, title, content))
                }
            }
            cursor.close()
        } catch (e: Exception) {
            return list
        }
        return list
    }
}
