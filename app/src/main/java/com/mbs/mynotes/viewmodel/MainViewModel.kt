package com.mbs.mynotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mbs.mynotes.db.NotesRepository
import com.mbs.mynotes.model.NotesModel

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = NotesRepository.getInstance(application)
    private val listNotes = MutableLiveData<List<NotesModel>>()
    val notes: LiveData<List<NotesModel>> = listNotes

    fun getAllNotes() {
        listNotes.value = repository.getAllNotes()
    }

}