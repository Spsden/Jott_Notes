package com.example.jott_notes.mvvmstuff.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.jott_notes.mvvmstuff.entity.Note


@Dao
interface DAO {

    @Query("SELECT * FROM Note ORDER BY id DESC")
    fun getAllNote():LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Note)

    @Delete
    suspend fun deleteNotes(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM Note WHERE title LIKE :query OR notesDesc LIKE :query")
    fun searchNote(query: String) : LiveData<List<Note>>

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