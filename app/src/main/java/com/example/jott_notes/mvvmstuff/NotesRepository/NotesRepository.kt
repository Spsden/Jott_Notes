package com.example.jott_notes.mvvmstuff.NotesRepository

import com.example.jott_notes.mvvmstuff.Database.NotesDatabase
import com.example.jott_notes.mvvmstuff.entity.Note

class NotesRepository(private val db: NotesDatabase) {

    fun getNote() = db.getNoteDao().getAllNote();

    fun searchNote(query: String) = db.getNoteDao().searchNote(query)

    suspend fun addNote(note : Note) = db.getNoteDao().addNote(note)

    suspend fun updateNote(note : Note) = db.getNoteDao().updateNote(note)

    suspend fun deleteNote(note : Note) = db.getNoteDao().deleteNotes(note)



}

//    fun searchDatabase(searchQuery: String) : LiveData<List<Notes>>{
//        return dao.searchDatabase(searchQuery)
//    }
//}