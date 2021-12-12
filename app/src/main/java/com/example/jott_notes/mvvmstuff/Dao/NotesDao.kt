package com.example.jott_notes.mvvmstuff.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.jott_notes.mvvmstuff.entity.Notes


@Dao
interface NotesDao {

    @Query("SELECT * FROM NOTES")
    fun getNotes():LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(notes: Notes)

    @Query("DELETE FROM Notes Where id=:id")
    fun deleteNotes(id: Int)

    @Update
    fun updateNotes(notes: Notes)
//
//    @Query("SELECT * FROM Notes WHERE title LIKE :searchQuery OR notesdesc LIKE :searchQuery")
//    fun searchDatabase(searchQuery: String) : LiveData<List<Notes>>

}





//import androidx.room.*
//import com.codingwithme.notesapp.entities.Notes
//
//@Dao
//interface NoteDao {
//
//    @Query("SELECT * FROM notes ORDER BY id DESC")
//    suspend fun getAllNotes() : List<Notes>
//
//    @Query("SELECT * FROM notes WHERE id =:id")
//    suspend fun getSpecificNote(id:Int) : Notes
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertNotes(note:Notes)
//
//    @Delete
//    suspend fun deleteNote(note:Notes)
//
//    @Query("DELETE FROM notes WHERE id =:id")
//    suspend fun deleteSpecificNote(id:Int)
//
//    @Update
//    suspend fun updateNote(note:Notes)
//}