package com.mbs.mynotes.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbs.mynotes.R
import com.mbs.mynotes.databinding.ActivityInsertBinding
import com.mbs.mynotes.databinding.RecyclerContentBinding
import com.mbs.mynotes.listeners.OnNotesListener
import com.mbs.mynotes.model.NotesModel

class NotesViewHolder(private val bind: RecyclerContentBinding, private val listener: OnNotesListener)
    : RecyclerView.ViewHolder(bind.root) {
    fun bind(note: NotesModel) {
        bind.title.text = note.title
        bind.content.text = note.content
        bind.cardview.setOnClickListener {
            listener.onClick(note.id!!)
        }
        bind.cardview.setOnLongClickListener {
            listener.onDelete(note.id!!)
            true
        }
    }
}