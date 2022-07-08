package com.mbs.mynotes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mbs.mynotes.R
import com.mbs.mynotes.model.NotesModel
import kotlinx.android.synthetic.main.recycler_content.view.*

class ListAdapter(private val context: Context)
    : RecyclerView.Adapter<NotesViewHolder>() {
    private var notes: List<NotesModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val itemList = LayoutInflater.from(context)
            .inflate(R.layout.recycler_content, parent, false)
        return NotesViewHolder(itemList)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.title.text = notes[position].title
        holder.content.text = notes[position].content
    }

    override fun getItemCount(): Int {
            return notes.size
    }
    fun updatedNotes(list: List<NotesModel>) {
        notes = list
        notifyDataSetChanged()
    }
}