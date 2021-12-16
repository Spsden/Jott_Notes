package com.example.jott_notes.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation

import androidx.navigation.fragment.navArgs
import com.example.jott_notes.R
import com.example.jott_notes.databinding.FragmentNotesCreateBinding
import com.example.jott_notes.mvvmstuff.Viewmodel.NotesViewModel
import com.example.jott_notes.mvvmstuff.entity.Notes
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.*


class EditNotesFragment : Fragment() {

    val addednotes by navArgs<EditNotesFragmentArgs>()
    var priorityColor: String = "0"
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

        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        (activity as AppCompatActivity?)!!.supportActionBar?.title = "Edit Note"

        binding = FragmentNotesCreateBinding.inflate(layoutInflater,container,false)
        setHasOptionsMenu(true)



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
            Log.d("TAG", "FROM EDIT NOTES")
        }



    }

    private fun updateNotes(it: View?) {
        val notesTitle = binding.notesTitle.text.toString()
        val notesDesc = binding.notesDesc.getMD()

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



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.more, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.More_Options) {
            val bottomSheetMoreOptions = BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme)
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
//            val bottomSheetDelete = BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme)
//            bottomSheetDelete.setContentView(R.layout.fragment_delete_bottom_sheet)
//
//
//            bottomSheetDelete.show()
//
//            val yesDelete = bottomSheetDelete.findViewById<TextView>(R.id.DeleteYes)
//            val noDelete = bottomSheetDelete.findViewById<TextView>(R.id.DeleteNo)
//
//            yesDelete?.setOnClickListener {
//                viewModel.deleteNotes(addednotes.dataTransfer?.id!!)
//                Toast.makeText(
//                    activity?.applicationContext,
//                    "Note Deleted Successfully",
//                    Toast.LENGTH_SHORT
//                ).show()
//                //requireActivity().supportFragmentManager.popBackStack()
//
//                //Navigation.findNavController(it).popBackStack(R.id.homeFragment,true)
//
//               // Navigation.findNavController(it!!).navigate(R.id.action_deleteBottomSheet2_to_homeFragment)
//
//
//               bottomSheetDelete.dismissWithAnimation
//
//
//
//                //Navigation.findNavController(it).popBackStack()
//
//
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
            EditNotesFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}