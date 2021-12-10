package com.example.jott_notes.mvvmstuff.Database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jott_notes.mvvmstuff.Dao.NotesDao
import com.example.jott_notes.mvvmstuff.entity.Notes
import java.security.AccessControlContext

abstract class NotesDatabase : RoomDatabase() {

    abstract fun myNotesDao(): NotesDao

    companion object {
        @Volatile
        var INSTANCE: NotesDatabase? = null

        fun getDatabaseInstance(context: Context): NotesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val roomDatabaseInstance =
                    Room.databaseBuilder(context, NotesDatabase::class.java, "Notes").build()

                INSTANCE = roomDatabaseInstance
                return return roomDatabaseInstance
            }
        }


    }
}