package com.example.jott_notes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.jott_notes.R
import com.example.jott_notes.databinding.FragmentNotesCreateBinding


class EditNotesFragment : Fragment() {

    val notes by navArgs<EditNotesFragmentArgs>()
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.notesTitle.setText(notes.dataTransfer?.title)
        binding.notesDesc.setText(notes.dataTransfer?.notesdesc)
        binding.date.setText(notes.dataTransfer?.date)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditNotesFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}