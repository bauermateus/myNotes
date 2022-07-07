package com.mbs.mynotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mbs.mynotes.db.NotesRepository
import com.mbs.mynotes.model.NotesModel

class InsertViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = NotesRepository.getInstance(application)
    fun insert(note: NotesModel) {
        repository.insert(note)
    }
}