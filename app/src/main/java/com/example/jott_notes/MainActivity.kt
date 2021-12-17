package com.example.jott_notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.jott_notes.mvvmstuff.Viewmodel.NoteViewModel
import com.example.jott_notes.databinding.ActivityMainBinding
import com.example.jott_notes.mvvmstuff.Database.NotesDatabase
import com.example.jott_notes.mvvmstuff.NotesRepository.NotesRepository
import com.example.jott_notes.mvvmstuff.Viewmodel.NoteActivityViewModelFactory
import java.lang.Exception

class MainActivity : AppCompatActivity() {


    private lateinit var noteActivityViewModel: NoteViewModel
    private lateinit var mainBinding:ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        //setContentView(R.layout.activity_main)

       mainBinding = ActivityMainBinding.inflate(layoutInflater)

        try {
            setContentView(mainBinding.root)
            val notesRepository = NotesRepository(NotesDatabase(this))
            val noteActivityViewModelFactory = NoteActivityViewModelFactory(notesRepository)
            noteActivityViewModel = ViewModelProvider(this,noteActivityViewModelFactory
            )[NoteViewModel::class.java]
        }catch (e : Exception){
            Log.d("TAG","IN MAIN ACTIVITY")
        }



    }

}