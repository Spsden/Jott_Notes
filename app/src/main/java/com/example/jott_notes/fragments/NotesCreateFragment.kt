package com.example.jott_notes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.jott_notes.R
import com.example.jott_notes.databinding.FragmentHomeBinding
import com.example.jott_notes.databinding.FragmentNotesCreateBinding


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

        binding = FragmentNotesCreateBinding.inflate(layoutInflater,container,false)

//        binding.MoreOptions.setOnClickListener {
//            Navigation.findNavController(it).navigate(R.id.action_notesCreateFragment_to_notesPageBottomSheet2)
//        }

        binding.MoreOptions.setOnClickListener {
            findNavController().navigate(R.id.action_notesCreateFragment_to_notesPageBottomSheet2)
        }



        return binding.root
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