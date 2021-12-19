package com.example.jott_notes.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.jott_notes.R
import com.example.jott_notes.databinding.FragmentNotesCreateBinding
import com.example.jott_notes.mvvmstuff.Viewmodel.NotesViewModel
import com.example.jott_notes.mvvmstuff.entity.Notes
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.thebluealliance.spectrum.SpectrumPalette
import kotlinx.android.synthetic.main.fragment_notes_create.view.*
import java.text.SimpleDateFormat
import java.util.*


class NotesCreateFragment : Fragment() {

    private lateinit var binding: FragmentNotesCreateBinding
    private var color: Int = 0
    val viewModel: NotesViewModel by viewModels()
    private var READ_STORAGE_PERM = 123
    private var WRITE_STORAGE_PERM = 123


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

        val onBackPressedCallback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
//                tts.stop()
//                tts.shutdown()
                view?.let { Navigation.findNavController(it).popBackStack() }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
       // (activity as AppCompatActivity?)!!.supportActionBar?.title = "Add Note"


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

        color = resources.getColor(R.color.bg_sheet)

        val bottomSheet = binding.MoreOptions
        binding.share


        bottomSheet.setOnClickListener {
            bottomSheetMenuOptions()
        }

//        shareButton.setOnClickListener {
//            shareFunc
//        }

//        markwon = Markwon.builder(requireContext()).usePlugin(AbstractMarkwonPlugin() {
//            @Override
//            con
//        })




    }

    private fun bottomSheetMenuOptions() {

        val bottomSheetMoreOptions =
            BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        bottomSheetMoreOptions.setContentView(R.layout.fragment_notes_page_bottom_sheet)
        bottomSheetMoreOptions.show()


        val colorPick = bottomSheetMoreOptions.findViewById<SpectrumPalette>(R.id.colorPicker)

        colorPick?.apply {
            setSelectedColor(color)
            setOnColorSelectedListener { value ->
                color = value
                binding.apply {
                    noteContentFragmentParent.setBackgroundColor(color)
                    bottomBar.setBackgroundColor(color)
                    style_bar.setBackgroundColor(color)
                    requireActivity().window.statusBarColor = color
//                    (activity as AppCompatActivity?)!!.supportActionBar?.setBackgroundDrawable(
//                        ColorDrawable(
//                            color
//                        )
//                    )

                }

            }
        }


    }

    private fun createNotes(it: View?) {

        if (binding.notesTitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Atleast Title Required", Toast.LENGTH_SHORT).show()
        }
        else{

            val notesTitle = binding.notesTitle.text.toString()
            val notesDesc = binding.notesDesc.getMD()
            val colorhere = color

            val sdf = SimpleDateFormat("dd/M/yyy hh:mm:ss")
            val currentDate = sdf.format(Date())

            val data =
                Notes(
                    null,
                    title = notesTitle,
                    notesdesc = notesDesc,
                    date = currentDate,
                    color = colorhere
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





    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NotesCreateFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }


}