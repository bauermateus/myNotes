package com.mbs.mynotes

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mbs.mynotes.adapter.ListAdapter
import com.mbs.mynotes.databinding.ActivityMainBinding
import com.mbs.mynotes.listeners.OnNotesListener
import com.mbs.mynotes.viewmodel.MainViewModel

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
        binding.searchBar.onWindowFocusChanged(false)
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
            intent.putExtra("id", id)
            startActivity(intent)
        }

        override fun onDelete(id: Int) {
            AlertDialog.Builder(this@MainActivity)
                .setTitle("Apagar este item?")
                .setMessage("Apagar este item?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", DialogInterface.OnClickListener(
                    fun(_, _) {
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
            binding.container.text = "Ainda não há notas. \n\n• Para criar uma nota, toque no botão '+'." +
                    " \n• Para deletar uma nota, pressione e segure sobre ela."
        } else {
            binding.container.text = ""
        }
    }
}