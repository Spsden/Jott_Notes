package com.example.jott_notes.fragments

import android.nfc.Tag
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.jott_notes.R
import com.example.jott_notes.databinding.FragmentHomeBinding
import com.example.jott_notes.databinding.FragmentNotesCreateBinding
import java.text.SimpleDateFormat
import java.util.*


class NotesCreateFragment : Fragment() {

    lateinit var binding: FragmentNotesCreateBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNotesCreateBinding.inflate(layoutInflater, container, false)



        binding.MoreOptions.setOnClickListener {
            findNavController().navigate(R.id.action_notesCreateFragment_to_notesPageBottomSheet2)
        }

//        binding.SaveNoteButtonFAB.setOnClickListener {
//            createNotes(it)
//        }



        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.SaveNoteButtonFAB.setOnClickListener {
            createNotes(it)
        }


    }

    private fun createNotes(it: View?) {
        var notesTitle = binding.notesTitle.text
        var notesDesc = binding.notesDesc.text

        var sdf = SimpleDateFormat("dd/M/yyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        //Log.e(tag, "createMotes:  $currentDate")


    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NotesCreateFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}