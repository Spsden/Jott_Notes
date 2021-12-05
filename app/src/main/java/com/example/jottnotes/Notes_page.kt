package com.example.jottnotes


import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager

import com.codingwithme.notesapp.database.NotesDatabase
import com.codingwithme.notesapp.entities.Notes
import kotlinx.android.synthetic.main.fragment_notes_page.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NotesPage : BaseFragment() {
    var selectedColor = "#232323"
    var currentDate: String? = null
    private var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
        noteId = requireArguments().getInt("noteId",-1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes_page, container, false)
    }

    companion object{
        @JvmStatic
        fun newInstance()=
            NotesPage().apply {
                arguments= Bundle().apply {

                }
            }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (noteId != 1){
            launch {
                context?.let {
                    var notes = NotesDatabase.getDatabase(it).noteDao().getSpecificNote(noteId)
                    notes_title.setText(notes.title)
                    notes_desc.setText(notes.noteText)
                }
            }
        }


        var sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())

        date.text = currentDate

        note_back_button.setOnClickListener {
            saveNote()
            replaceFragment(Mainpage(), true)


        }
    }

    private fun saveNote() {
        if (notes_title.text.isNullOrEmpty()) {
            Toast.makeText(context, "Title Required", Toast.LENGTH_SHORT).show()
        }

        launch {
            val notes = Notes()
            notes.title = notes_title.text.toString()
            notes.noteText = notes_desc.text.toString()
            notes.dateTime = currentDate

            context?.let {
                NotesDatabase.getDatabase(it).noteDao().insertNotes(notes)
                notes_desc.setText("")
                notes_title.setText("")
                requireActivity().supportFragmentManager.popBackStack()

            }

        }
    }


    fun replaceFragment(fragment: Fragment, istransition: Boolean) {
        val manager = requireActivity().supportFragmentManager
        val transaction = manager.beginTransaction()

        if (istransition) {
            transaction.setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
            )
        }
        transaction.replace(R.id.notes_page, fragment).commit()
    }


}