package com.mbs.mynotes

import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.toColor
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mbs.mynotes.adapter.ListAdapter
import com.mbs.mynotes.databinding.ActivityMainBinding
import com.mbs.mynotes.db.DataBaseConstants
import com.mbs.mynotes.listeners.OnNotesListener
import com.mbs.mynotes.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.absoluteValue


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ListAdapter()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val recyclerView = binding.recyclerView
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getAllNotes()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        observers()

        setAddButtonOnClickListener()

        adapter.setListener(listener)

        searchBarManagement()
    }

    override fun onResume() {
        super.onResume()

        viewModel.getAllNotes()

        blankListHandler()

        binding.searchBar.clearFocus()
    }

    private val listener = object : OnNotesListener {
        override fun onClick(id: Int) {
            val intent = Intent(applicationContext, InsertActivity::class.java)
            intent.putExtra(DataBaseConstants.ID, id)
            startActivity(intent)
        }

        override fun onDelete(id: Int) {
            AlertDialog.Builder(this@MainActivity)
                .setTitle(getString(R.string.delete_confirmation))
                .setNegativeButton(getString(R.string.negative_confirmation_string), null)
                .setPositiveButton(getString(R.string.positive_confirmation_string),
                    DialogInterface.OnClickListener(fun(_, _) {
                        viewModel.delete(id)
                        viewModel.getAllNotes()
                        blankListHandler()
                    }
                    ))
                .show()
        }
    }

    private fun observers() {
        viewModel.notes.observe(this
        ) {
            adapter.updatedNotes(it)
        }
    }

    private fun setAddButtonOnClickListener() {
        binding.addButton.setOnClickListener {
            startActivity(Intent(applicationContext, InsertActivity::class.java))
        }
    }

    private fun searchBarManagement() {
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) viewModel.getAllNotes()
                else adapter.search(newText)
                return true
            }
        })
    }

    private fun blankListHandler() {
        if (viewModel.notes.value.isNullOrEmpty()) {
            binding.container.setText(R.string.add_and_remove_help_message)
        } else {
            binding.container.text = ""
        }
    }
}