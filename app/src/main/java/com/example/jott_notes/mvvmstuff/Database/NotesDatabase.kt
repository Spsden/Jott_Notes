package com.example.jott_notes.mvvmstuff.Database

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jott_notes.mvvmstuff.Dao.DAO
import com.example.jott_notes.mvvmstuff.entity.Note
import java.util.concurrent.locks.Lock

@Database(entities = [Note::class], version = 1, exportSchema = false)

abstract class NotesDatabase : RoomDatabase() {

    abstract fun getNoteDao(): DAO

    companion object {
        @Volatile
        var instance: NotesDatabase? = null
        val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance ?: createDatabase(context).also {
                instance=it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NotesDatabase::class.java,
            "note_database"
        ).build()


    }
}