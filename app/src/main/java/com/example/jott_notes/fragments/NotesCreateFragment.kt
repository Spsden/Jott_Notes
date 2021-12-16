package com.example.jott_notes.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.jott_notes.R
import com.example.jott_notes.databinding.FragmentNotesCreateBinding
import com.example.jott_notes.mvvmstuff.Viewmodel.NotesViewModel
import com.example.jott_notes.mvvmstuff.entity.Notes
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.*


class NotesCreateFragment : Fragment() {

    lateinit var binding: FragmentNotesCreateBinding

    var priorityColor: String = "0"


    val viewModel: NotesViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        (activity as AppCompatActivity?)!!.supportActionBar?.title = "Add Note"


        binding = FragmentNotesCreateBinding.inflate(
            layoutInflater, container, false
        )
        setHasOptionsMenu(true)


        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.SaveNoteButtonFAB.setOnClickListener {

                createNotes(it)

        }

        try {
            binding.notesDesc.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding.bottomBar.visibility = View.VISIBLE
                    binding.notesDesc.setStylesBar(binding.styleBar)
                } else {
                    binding.bottomBar.visibility = View.GONE
                }
            }
        } catch (e: Throwable) {
            Log.d("TAG", "FROM CREATE NOTES")
        }


    }

    private fun createNotes(it: View?) {

        if (binding.notesTitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Atleast Title Required", Toast.LENGTH_SHORT).show()
        }
        else{

            val notesTitle = binding.notesTitle.text.toString()
            val notesDesc = binding.notesDesc.getMD()

            val sdf = SimpleDateFormat("dd/M/yyy hh:mm:ss")
            val currentDate = sdf.format(Date())

            val data =
                Notes(
                    null,
                    title = notesTitle,
                    notesdesc = notesDesc,
                    date = currentDate,
                    //prioritycolor
                )

            viewModel.addNotes(data)

            Toast.makeText(
                activity?.applicationContext,
                "Note Created Successfully",
                Toast.LENGTH_SHORT
            ).show()

            Navigation.findNavController(it!!).navigate(R.id.action_notesCreateFragment_to_homeFragment)

        }




    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.more, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.More_Options) {
            val bottomSheetMoreOptions =
                BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
            bottomSheetMoreOptions.setContentView(R.layout.fragment_notes_page_bottom_sheet)
            bottomSheetMoreOptions.show()

//            val dark = bottomSheetMoreOptions.findViewById<ImageView>(R.id.fNote0)
//            val purple = bottomSheetMoreOptions.findViewById<ImageView>(R.id.fNote1)
//            val orange = bottomSheetMoreOptions.findViewById<ImageView>(R.id.fNote2)
//            val green = bottomSheetMoreOptions.findViewById<ImageView>(R.id.fNote3)
//
//            dark?.setOnClickListener {
//                priorityColor = "0"
//
//                dark.setImageResource(R.drawable.ic_check_24)
//                purple?.setImageResource(0)
//                orange?.setImageResource(0)
//                green?.setImageResource(0)
//
//            }
//
//            purple?.setOnClickListener {
//                priorityColor = "1"
//
//                purple.setImageResource(R.drawable.ic_check_24)
//                dark?.setImageResource(0)
//                orange?.setImageResource(0)
//                green?.setImageResource(0)
//
//            }
//
//            orange?.setOnClickListener {
//                priorityColor = "2"
//
//                orange.setImageResource(R.drawable.ic_check_24)
//                dark?.setImageResource(0)
//                purple?.setImageResource(0)
//                green?.setImageResource(0)
//
//            }
//
//            green?.setOnClickListener {
//                priorityColor = "3"
//
//                green.setImageResource(R.drawable.ic_check_24)
//                dark?.setImageResource(0)
//                orange?.setImageResource(0)
//                purple?.setImageResource(0)
//
//            }


        }
//        if (item.itemId == R.id.Delete) {
//            val bottomSheetDelete =
//                BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
//            bottomSheetDelete.setContentView(R.layout.fragment_delete_bottom_sheet)
//            bottomSheetDelete.show()
//
//            val yesDelete = bottomSheetDelete.findViewById<TextView>(R.id.DeleteYes)
//            val noDelete = bottomSheetDelete.findViewById<TextView>(R.id.DeleteNo)
//
//            yesDelete?.setOnClickListener {
//                //viewModel.deleteNotes()
//
//            }
//            noDelete?.setOnClickListener {
//                bottomSheetDelete.dismiss()
//
//            }
//
//        }

        return super.onOptionsItemSelected(item)
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