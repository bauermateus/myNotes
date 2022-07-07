package com.mbs.mynotes

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mbs.mynotes.databinding.ActivityInsertBinding
import com.mbs.mynotes.model.NotesModel
import com.mbs.mynotes.viewmodel.InsertViewModel

class InsertActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInsertBinding
    private lateinit var viewModel: InsertViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(InsertViewModel::class.java)
        binding.saveButton.setOnClickListener {
            if (binding.insertContent.text.toString() != ""
                && binding.insertTitle.text.toString() != ""
            ) {
                insert()
                hideKeyboard(it)
                clearAll()
                this.finish()
            } else {
                Toast.makeText(this, "Por favor, preencha Título e Conteúdo.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun insert() {
        val dataFromView = NotesModel(null,
            binding.insertTitle.text.toString(),
            binding.insertContent.text.toString())
            viewModel.insert(dataFromView)
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun clearAll() {
        binding.insertTitle.text.clear()
        binding.insertContent.text.clear()
    }
}