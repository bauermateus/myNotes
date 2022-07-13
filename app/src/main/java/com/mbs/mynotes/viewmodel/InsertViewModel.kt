package com.mbs.mynotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mbs.mynotes.db.NotesRepository
import com.mbs.mynotes.model.NotesModel

class InsertViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = NotesRepository.getInstance(application)
    fun insert(note: NotesModel) {
        repository.insert(note)
    }
    private val singleNoteModel = MutableLiveData<NotesModel>()
    val singleNote: LiveData<NotesModel> = singleNoteModel

    fun getNote(id: Int) {
        singleNoteModel.value = repository.getNote(id)
    }

    fun updateNote(note: NotesModel) {
        repository.update(note)
    }
    fun delete(id: Int) {
        repository.delete(id)
    }


}