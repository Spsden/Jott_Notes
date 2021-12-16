package com.example.jott_notes.fragments

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.jott_notes.R
import com.example.jott_notes.databinding.FragmentNotesCreateBinding
import com.example.jott_notes.mvvmstuff.Viewmodel.NotesViewModel
import com.example.jott_notes.mvvmstuff.entity.Notes
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_edit_notes.*
import java.text.SimpleDateFormat
import java.util.*


class EditNotesFragment : Fragment() {

    private val addednotes by navArgs<EditNotesFragmentArgs>()
    var priorityColor: String = "0"
    private lateinit var tts : TextToSpeech
    private lateinit var binding: FragmentNotesCreateBinding

    private val viewModel: NotesViewModel by viewModels()
    private lateinit var iconSwitch:com.suke.widget.SwitchButton




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


//        val callback = object : OnBackPressedCallback(true){
//            override fun handleOnBackPressed() {
////                textToSpeech?.setOnClickListener {
////                    tts = TextToSpeech(context?.applicationContext) {
////                        if (tts.isSpeaking) {
////                            tts.stop()
////                            tts.shutdown()
////
////
////                        }else
////                        {
////                            Toast.makeText(context,"jhfdh",Toast.LENGTH_SHORT).show()
////                        }
////
////                    }
////                }
//                findNavController().popBackStack()
//
//                // if you want onBackPressed() to be called as normal afterwards
//                if (isEnabled) {
//                    isEnabled = false
//                    requireActivity().onBackPressed()
//                }
//            }
//
//        }
//
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)



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

            val iconSwitch = bottomSheetMoreOptions.findViewById<SwitchCompat>(R.id.switch_button)

            val layut = bottomSheetMoreOptions.findViewById<LinearLayout>(R.id.textToSpeech)

            iconSwitch?.setOnClickListener {
                if (iconSwitch.isChecked){
                    tts = TextToSpeech(context?.applicationContext) {
                        if (it == TextToSpeech.SUCCESS) {
                            tts.language = Locale.getDefault()
                            tts.speak(notes_desc.text.toString(), TextToSpeech.QUEUE_ADD, null)
                        }
                    }
                }
                else{
                    stopTextToSpeech()

                }
            }

        }

        if (item.itemId == R.id.shareButton) {
            val share = Intent()
            share.action = Intent.ACTION_SEND
            share.type = "text/plain"
            share.putExtra(Intent.EXTRA_TEXT,binding.notesDesc.getMD())
            context?.startActivity(Intent.createChooser(share,getString(R.string.app_name)))
        }


        return super.onOptionsItemSelected(item)
    }

    private fun stopTextToSpeech() {
        //Log.e("@@@@","toggle check")
        Toast.makeText(context,"Reader Stopped",Toast.LENGTH_SHORT).show()
        if (tts.isSpeaking){
            tts.stop()
            tts.shutdown()
        }
        else
        {
            Toast.makeText(context,"Reader Never Started",Toast.LENGTH_SHORT).show()
        }

    }

//    private fun startingTextToSpeech() {
//        Toast.makeText(context,"Text To Speech Is Not Running",Toast.LENGTH_SHORT)
//
//        tts = TextToSpeech(context?.applicationContext){
//                    if (it == TextToSpeech.SUCCESS){
//                        tts.language = Locale.getDefault()
//                        tts.setSpeechRate(1.0f)
//                        tts.speak(notes_desc.text.toString(),TextToSpeech.QUEUE_ADD,null)
//                    }
//                }
//
//    }








    companion object {

        @JvmStatic
        fun newInstance() =
            EditNotesFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}