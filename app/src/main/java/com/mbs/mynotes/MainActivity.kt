package com.mbs.mynotes

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mbs.mynotes.adapter.ListAdapter
import com.mbs.mynotes.databinding.ActivityMainBinding
import com.mbs.mynotes.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ListAdapter(applicationContext)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val recyclerView = binding.recyclerView
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getAllNotes()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        observe()

        setAddButtonOnClickListener()

        setEditNoteOnClickListener()

    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllNotes()
    }

    fun observe() {
        viewModel.notes.observe(this
        ) {
            adapter.updatedNotes(it)
        }
    }
    private fun setAddButtonOnClickListener() {
        binding.addButton.setOnClickListener{
            startActivity(Intent(applicationContext, InsertActivity::class.java))
        }
    }
    private fun setEditNoteOnClickListener() {
        findViewById<View>(R.id.note_body).setOnClickListener {
            startActivity(Intent(applicationContext, InsertActivity::class.java))
        }
    }

}