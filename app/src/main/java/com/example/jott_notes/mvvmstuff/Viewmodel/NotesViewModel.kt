package com.example.jott_notes.mvvmstuff.Viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.room.Query
import com.example.jott_notes.mvvmstuff.Database.NotesDatabase
import com.example.jott_notes.mvvmstuff.NotesRepository.NotesRepository
import com.example.jott_notes.mvvmstuff.entity.Notes

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository:NotesRepository

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

//    fun searchDatabase(searchQuery: String) : LiveData<List<Notes>>{
//        return repository.searchDatabase(searchQuery).asLivedata()
//    }
}