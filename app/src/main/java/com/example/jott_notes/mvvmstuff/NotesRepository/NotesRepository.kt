package com.example.jott_notes.mvvmstuff.NotesRepository

import androidx.lifecycle.LiveData
import com.example.jott_notes.mvvmstuff.Dao.NotesDao
import com.example.jott_notes.mvvmstuff.entity.Notes

class NotesRepository(val dao: NotesDao) {

    fun getAllNotes():LiveData<List<Notes>> {
        return dao.getNotes()
    }

    fun insertNOtes(notes: Notes){
        dao.insertNotes(notes)
    }

    fun deleteNotes(id:Int){
        dao.deleteNotes(id)
    }

    fun updateNotes(notes: Notes){
        dao.updateNotes(notes)
    }
}