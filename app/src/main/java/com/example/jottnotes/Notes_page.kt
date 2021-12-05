package com.example.jottnotes


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager

import com.example.jottnotes.database.NotesDatabase
import com.example.jottnotes.entities.Notes
import kotlinx.android.synthetic.main.fragment_notes_page.*
import kotlinx.android.synthetic.main.fragment_notes_page_bottom_sheet.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NotesPage : BaseFragment() {
    var selectedColor = "#232323"
    var currentDate: String? = null
    private var noteId = -1

    private var colorView = view?.findViewById<EditText>(R.id.notes_title)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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


//        if (noteId != 1){
//            launch {
//                context?.let {
//                    var notes = NotesDatabase.getDatabase(it).noteDao().getSpecificNote(noteId)
//                    notes_title.setText(notes.title)
//                    notes_desc.setText(notes.noteText)
//                    colorView?.setBackgroundColor(Color.parseColor(notes.color))
//                }
//            }
//        }

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            BroadcastReceiver,IntentFilter("bottom_sheet_action")
            )



        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())

        colorView?.setBackgroundColor(Color.parseColor(selectedColor))

        date.text = currentDate

        note_back_button.setOnClickListener {
            saveNote()
            requireActivity().supportFragmentManager.popBackStack()


        }

        imgDone.setOnClickListener {
            if (noteId != -1){
                //updateNote()
            }else{
                saveNote()
            }
        }

        imgMore.setOnClickListener{


            var noteBottomSheetFragment = NotesPageBottomSheet.newInstance(noteId)
            noteBottomSheetFragment.show(requireActivity().supportFragmentManager,"Note Bottom Sheet Fragment")
        }


    }

    private fun saveNote() {
        if (notes_title.text.isNullOrEmpty()) {
            Toast.makeText(context, "Title Required", Toast.LENGTH_SHORT).show()
        }
        else {

            launch {
                val notes = Notes()
                notes.title = notes_title.text.toString()
                notes.noteText = notes_desc.text.toString()
                notes.dateTime = currentDate
                notes.color = selectedColor
                //notes.imgPath = selectedImagePath
                //notes.webLink = webLink

                context?.let {
                    NotesDatabase.getDatabase(it).noteDao().insertNotes(notes)
                    notes_desc.setText("")
                    notes_title.setText("")
                    layoutImage.visibility = View.GONE
                    //imgNote.visibility = View.GONE
                    //tvWebLink.visibility = View.GONE
                    requireActivity().supportFragmentManager.popBackStack()

                }

            }
        }
    }

    private val BroadcastReceiver : BroadcastReceiver = object :BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {

            var actionColor = p1!!.getStringExtra("action")

            when(actionColor!!){

                "Blue" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    colorView?.setBackgroundColor(Color.parseColor(selectedColor))

                }

                "Yellow" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    colorView?.setBackgroundColor(Color.parseColor(selectedColor))

                }


                "Purple" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    colorView?.setBackgroundColor(Color.parseColor(selectedColor))

                }


                "Green" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    colorView?.setBackgroundColor(Color.parseColor(selectedColor))

                }


                "Orange" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    colorView?.setBackgroundColor(Color.parseColor(selectedColor))

                }


                "Black" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    colorView?.setBackgroundColor(Color.parseColor(selectedColor))

                }

//                "Image" ->{
//                    readStorageTask()
//                    layoutWebUrl.visibility = View.GONE
//                }

//                "WebUrl" ->{
//                    layoutWebUrl.visibility = View.VISIBLE
//                }
//                "DeleteNote" -> {
//                    //delete note
//                    deleteNote()
//                }


                else -> {
                    layoutImage.visibility = View.GONE
//                    imgNote.visibility = View.GONE
//                    layoutWebUrl.visibility = View.GONE
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    colorView?.setBackgroundColor(Color.parseColor(selectedColor))

                }
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

    override fun onDestroy() {

        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(BroadcastReceiver)
        super.onDestroy()
    }


}


