package com.example.jott_notes.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.example.jott_notes.R
import com.example.jott_notes.databinding.FragmentNotesCreateBinding
import com.example.jott_notes.mvvmstuff.Viewmodel.NotesViewModel
import com.example.jott_notes.mvvmstuff.entity.Notes
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.*


class NotesCreateFragment : Fragment() {

    lateinit var binding: FragmentNotesCreateBinding

    var priorityColor : String = "#232323"

    var noteHeadColor = view?.findViewById<TextView>(R.id.note_heading)
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

        binding = FragmentNotesCreateBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)


//        binding.MoreOptions.setOnClickListener {
//            findNavController().navigate(R.id.action_notesCreateFragment_to_notesPageBottomSheet2)
//        }

//        binding.SaveNoteButtonFAB.setOnClickListener {
//            createNotes(it)
//        }
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
//            BroadcastReceiver, IntentFilter("bottom_sheet_color_action")
//        )

        binding.SaveNoteButtonFAB.setOnClickListener {
            createNotes(it)
        }


    }

    private fun createNotes(it: View?) {
        var notesTitle = binding.notesTitle.text.toString()
        var notesDesc = binding.notesDesc.text.toString()

        var sdf = SimpleDateFormat("dd/M/yyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        val data =
            Notes(
                null,
                title = notesTitle,
                notesdesc = notesDesc,
                date = currentDate.toString(),
                prioritycolor = priorityColor
            )

        viewModel.addNotes(data)

        Toast.makeText(activity?.applicationContext,"Note Created",Toast.LENGTH_SHORT).show()


    }

//    private val BroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
//        override fun onReceive(p0: Context?, p1: Intent?) {
//            var actionColor = p1!!.getStringExtra("action")
//
//            when (actionColor!!) {
//
//
//                "dark" -> {
//                    priorityColor = p1.getStringExtra("priorityColor")!!
//                    noteHeadColor?.setBackgroundColor(Color.parseColor(priorityColor))
//                }
//
//                "purple" -> {
//                    priorityColor = p1.getStringExtra("priorityColor")!!
//                    noteHeadColor?.setBackgroundColor(Color.parseColor(priorityColor))
//                }
//
//                "orange" -> {
//                    priorityColor = p1.getStringExtra("priorityColor")!!
//                    noteHeadColor?.setBackgroundColor(Color.parseColor(priorityColor))
//                }
//
//                "green" -> {
//                    priorityColor = p1.getStringExtra("priorityColor")!!
//                    noteHeadColor?.setBackgroundColor(Color.parseColor(priorityColor))
//                }
//
//
//                else -> {
//                    priorityColor = p1.getStringExtra("priorityColor")!!
//                    noteHeadColor?.setBackgroundColor(Color.parseColor(priorityColor))
//                }
//
//            }
//        }
//    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.more,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.More_Options)
        {
            val bottomSheetMoreOptions=BottomSheetDialog(requireContext())
            bottomSheetMoreOptions.setContentView(R.layout.fragment_notes_page_bottom_sheet)
            bottomSheetMoreOptions.show()



        }
        if (item.itemId == R.id.Delete)
        {
            val bottomSheetDelete = BottomSheetDialog(requireContext())
            bottomSheetDelete.setContentView(R.layout.fragment_delete_bottom_sheet)
            bottomSheetDelete.show()

            val yesDelete = bottomSheetDelete.findViewById<TextView>(R.id.DeleteYes)
            val noDelete = bottomSheetDelete.findViewById<TextView>(R.id.DeleteNo)

            yesDelete?.setOnClickListener {
                //viewModel.deleteNotes()

            }
            noDelete?.setOnClickListener {
                bottomSheetDelete.dismiss()

            }

        }

        return super.onOptionsItemSelected(item)
    }

//    override fun onDestroy() {
////        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(BroadcastReceiver)
//        super.onDestroy()
//    }



    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NotesCreateFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}