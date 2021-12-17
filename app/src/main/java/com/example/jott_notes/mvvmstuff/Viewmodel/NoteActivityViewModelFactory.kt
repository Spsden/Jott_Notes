package com.example.jott_notes.mvvmstuff.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jott_notes.mvvmstuff.NotesRepository.NotesRepository

class NoteActivityViewModelFactory(private val repository: NotesRepository):ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(repository) as T
    }
}