package com.example.jott_notes.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation

import androidx.navigation.fragment.navArgs
import com.example.jott_notes.R
import com.example.jott_notes.databinding.FragmentNotesCreateBinding
import com.example.jott_notes.mvvmstuff.Viewmodel.NotesViewModel
import com.example.jott_notes.mvvmstuff.entity.Notes
import java.text.SimpleDateFormat
import java.util.*


class EditNotesFragment : Fragment() {

    val addednotes by navArgs<EditNotesFragmentArgs>()
    lateinit var binding: FragmentNotesCreateBinding

    val viewModel: NotesViewModel by viewModels()


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

        binding.notesTitle.setText(addednotes.dataTransfer?.title)
        binding.notesDesc.setText(addednotes.dataTransfer?.notesdesc)
        binding.date.setText(addednotes.dataTransfer?.date)

        binding.SaveNoteButtonFAB.setOnClickListener {
            updateNotes(it)
        }


    }

    private fun updateNotes(it: View?) {
        val notesTitle = binding.notesTitle.text.toString()
        val notesDesc = binding.notesDesc.text.toString()

        val sdf = SimpleDateFormat("dd/M/yyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        val data =
            Notes(
                addednotes.dataTransfer?.id,
                title = notesTitle,
                notesdesc = notesDesc,
                date = currentDate.toString(),
                //prioritycolor
            )

       Log.e("@@@@@","updateNotes: Title : $notesTitle SubTile : $notesDesc")

        viewModel.updateNotes(data)

        Toast.makeText(
            activity?.applicationContext,
            "Note Updated Successfully",
            Toast.LENGTH_SHORT
        ).show()

        Navigation.findNavController(it!!).navigate(R.id.action_editNotesFragment_to_homeFragment)
    }

//    companion object {
//
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            EditNotesFragment().apply {
//                arguments = Bundle().apply {
//
//                }
//            }
//    }
}