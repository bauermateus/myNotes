package com.mbs.mynotes

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mbs.mynotes.databinding.ActivityInsertBinding
import com.mbs.mynotes.db.DataBaseConstants
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
        loadData()
        handleSaveButton()
        handleBackButton()
    }

    private fun insert() {
        val dataFromView = NotesModel(null,
            binding.insertTitle.text.toString(),
            binding.insertContent.text.toString())
        viewModel.insert(dataFromView)
    }

    private fun update() {
        if (binding.insertContent.text.toString() == "" &&
            binding.insertTitle.text.toString() == ""
        ) {
            viewModel.delete(intent.extras!!.getInt(DataBaseConstants.ID))
            this.finish()
        } else if (binding.insertContent.text.toString() == "" ||
            binding.insertTitle.text.toString() == ""
        ) {
            Toast.makeText(this, getString(R.string.fill_all_fields_string),
                Toast.LENGTH_SHORT).show()
        } else {
            val dataToUpdate = NotesModel(intent.extras!!.getInt(DataBaseConstants.ID),
                binding.insertTitle.text.toString(),
                binding.insertContent.text.toString())
            viewModel.updateNote(dataToUpdate)
            this.finish()
        }
    }

    private fun loadData() {
        if (intent.extras != null) {
            val id = intent.extras!!.getInt(DataBaseConstants.ID)
            viewModel.getNote(id)
            viewModel.singleNote.observe(this) {
                binding.insertTitle.setText(it.title)
                binding.insertContent.setText(it.content)
            }
        }
    }

    private fun handleSaveButton() {
        binding.saveButton.setOnClickListener {
            if (intent.extras == null) {
                if (binding.insertContent.text.toString() != ""
                    && binding.insertTitle.text.toString() != ""
                ) {
                    insert()
                    this.finish()
                } else {
                    Toast.makeText(this,
                        getString(R.string.must_fill_all_fields_string),
                        Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                update()
            }
        }
    }

    private fun handleBackButton() {
        binding.backButton.setOnClickListener {
            this.finish()
        }
    }
}