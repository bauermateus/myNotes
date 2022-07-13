package com.mbs.mynotes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mbs.mynotes.databinding.RecyclerContentBinding
import com.mbs.mynotes.listeners.OnNotesListener
import com.mbs.mynotes.model.NotesModel

class ListAdapter : RecyclerView.Adapter<NotesViewHolder>() {
    private var notes: List<NotesModel> = listOf()
    private lateinit var listener: OnNotesListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val item = RecyclerContentBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(item, listener)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun updatedNotes(list: List<NotesModel>) {
        notes = list
        notifyDataSetChanged()
    }

    fun setListener(listener: OnNotesListener) {
        this.listener = listener
    }

    fun search(text: String?) {
            notes = notes.sortedByDescending { it.content.contains(text.toString(), true) ||
                    it.title.contains(text.toString(), true) }
        notifyDataSetChanged()
    }
}