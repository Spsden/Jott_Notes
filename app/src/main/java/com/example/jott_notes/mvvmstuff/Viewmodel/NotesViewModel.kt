package com.example.jott_notes.mvvmstuff.Viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.jott_notes.mvvmstuff.Database.NotesDatabase
import com.example.jott_notes.mvvmstuff.NotesRepository.NotesRepository
import com.example.jott_notes.mvvmstuff.entity.Notes

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    val repository:NotesRepository

    init {
        val dao = NotesDatabase.getDatabaseInstance(application).myNotesDao()
        repository= NotesRepository(dao)

    }

    fun addNotes(notes: Notes){
        repository.insertNOtes(notes)
    }

    fun getNotes():LiveData<List<Notes>> = repository.getAllNotes()

    fun deleteNotes(id:Int){
        repository.deleteNotes(id)
    }

    fun updateNotes(notes: Notes){
        repository.updateNotes(notes)
    }
}