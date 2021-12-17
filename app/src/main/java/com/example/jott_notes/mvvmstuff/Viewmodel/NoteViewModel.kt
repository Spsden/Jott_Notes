package com.example.jott_notes.mvvmstuff.Viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jott_notes.mvvmstuff.NotesRepository.NotesRepository
import com.example.jott_notes.mvvmstuff.entity.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NotesRepository) : ViewModel() {

    fun saveNote(newNote : Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.addNote(newNote)
    }

    fun updateNote(existingNote : Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateNote(existingNote)
    }

    fun deleteNote(existingNote: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNote(existingNote)
    }

    fun searchNote(query: String) :LiveData<List<Note>>{
       return repository.searchNote(query)
    }

    fun getAllNote() : LiveData<List<Note>> = repository.getNote()




}


//
//
//class NoteViewModel(private val repository: NotesRepository) : ViewModel() {
//
//    fun saveNote(newNote : Note) = viewModelScope.launch(Dispatchers.IO) {
//        repository.addNote(newNote)
//    }
//
//    fun updateNote(existingNote : Note) = viewModelScope.launch(Dispatchers.IO) {
//        repository.updateNote(existingNote)
//    }
//
//    fun deleteNote(existingNote: Note) = viewModelScope.launch(Dispatchers.IO) {
//        repository.deleteNote(existingNote)
//    }
//
//    fun searchNote(query: String) :LiveData<List<Note>>{
//        return repository.searchNote(query)
//    }
//
//    fun getAllNote() : LiveData<List<Note>> = repository.getNote()
//
