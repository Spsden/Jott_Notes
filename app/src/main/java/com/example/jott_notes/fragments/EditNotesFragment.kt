package com.example.jott_notes.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.jott_notes.mvvmstuff.Viewmodel.NoteViewModel
import com.example.jott_notes.MainActivity
import com.example.jott_notes.R
import com.example.jott_notes.R.string.modified_on
import com.example.jott_notes.adapters.NotesRvListAdapter
import com.example.jott_notes.databinding.BottomSheetLayoutNewBinding
import com.example.jott_notes.databinding.FragmentEditNotesBinding
import com.example.jott_notes.mvvmstuff.entity.Note
import com.example.jott_notes.utils.hideKeyboard
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class EditNotesFragment : Fragment(R.layout.fragment_edit_notes) {


    private lateinit var navController: NavController
    private lateinit var result: String

    private lateinit var contentBinding: FragmentEditNotesBinding

    private var note: Note? = null

    private var color = -1
    var modified: String = "Modified on"
    private lateinit var notesRvListAdapter: NotesRvListAdapter

    private val notesViewModel: NoteViewModel by activityViewModels()
    private val currentDate = SimpleDateFormat.getInstance().format(Date())
    private val job = CoroutineScope(Dispatchers.Main)
    private val args: EditNotesFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val animation = MaterialContainerTransform().apply {
            drawingViewId = R.id.homeFragment
            scrimColor = Color.TRANSPARENT
            duration = 300L
        }

        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }



    @SuppressLint("StringFormatInvalid")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contentBinding = FragmentEditNotesBinding.bind(view)


        navController = Navigation.findNavController(view)

        val activity = activity as MainActivity

        ViewCompat.setTransitionName(
            contentBinding.noteContentFragmentParent,
            "recyclerView_${args.note?.id}"
        )

        contentBinding.backButtonCreateFragment.setOnClickListener {
            requireView().hideKeyboard()
            navController.popBackStack()
        }

        contentBinding.date.text = getString(R.string.modified_on,SimpleDateFormat.getDateInstance().format(
            Date()
        ))


        contentBinding.SaveNoteButtonFAB.setOnClickListener {
            saveNote()
           // notesRvListAdapter.notifyDataSetChanged()

            //navController.popBackStack()
        }


        try {
            contentBinding.notesDesc.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    contentBinding.bottomBar.visibility = View.VISIBLE
                    contentBinding.notesDesc.setStylesBar(contentBinding.styleBar)
                } else {
                    contentBinding.bottomBar.visibility = View.GONE
                }
            }
        } catch (e: Throwable) {
            Log.d("TAG", "FROM CREATE NOTES")
        }



        contentBinding.MoreOptions.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(
                requireContext(), R.style.BottomSheetDialogTheme
            )

            Log.e("@@@", "tHIS BUTTON PRESSED")

            val bottomSheetView: View =
                layoutInflater.inflate(R.layout.bottom_sheet_layout_new, null,)

            with(bottomSheetDialog) {
                setContentView(bottomSheetView)
                show()
            }

            val bottomSheetContentBinding = BottomSheetLayoutNewBinding.bind(bottomSheetView)
            bottomSheetContentBinding.apply {
                colorPicker.apply {
                    setSelectedColor(color)
                    setOnColorSelectedListener { value ->
                        color = value
                        contentBinding.apply {
                            noteContentFragmentParent.setBackgroundColor(color)
                            toolbarFragmentInCreateNotes.setBackgroundColor(color)
                            bottomBar.setBackgroundColor(color)
                            activity.window.statusBarColor = color
                        }
                        bottomSheetContentBinding.bottomSheetParent.setBackgroundColor(color)
                    }
                }
                bottomSheetParent.setBackgroundColor(color)
            }
            bottomSheetView.post {
                bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }

        }

        //opens existing note
        setUpNote()

    }



    @SuppressLint("StringFormatInvalid")
    private fun setUpNote() {
        val note= args.note
        val title = contentBinding.notesTitle
        val content = contentBinding.notesDesc
        val date = contentBinding.date

        if (note==null)
        {

            contentBinding.date.text = getString(R.string.modified_on,SimpleDateFormat.getDateInstance().format(
                Date()
            ))



        }
        if (note != null)
        {
            title.setText(note.title)
            content.renderMD(note.notesDesc)
            date.text=getString(R.string.modified_on,note.date)
            color=note.color

            contentBinding.apply {
                job.launch {
                    delay(10)
                    noteContentFragmentParent.setBackgroundColor(color)

                }
                toolbarFragmentInCreateNotes.setBackgroundColor(color)
                bottomBar.setBackgroundColor(color)
            }
            activity?.window?.statusBarColor=note.color
        }
    }


    private fun saveNote() {
        if (contentBinding.notesTitle.toString().isEmpty()) {
            Toast.makeText(activity, "At Least title is required", Toast.LENGTH_SHORT).show()
        } else {


            note = args.note
            when (note) {
                null -> {
                    notesViewModel.saveNote(
                        Note(
                            0,
                            contentBinding.notesTitle.text.toString(),
                            contentBinding.notesDesc.getMD(),
                            currentDate,
                            color
                        )
                    )

                    //navController.navigate(EditNotesFragmentDirections.actionEditNotesFragmentToHomeFragment())



                    result = "Note Saved"
                    setFragmentResult(
                        "key",
                        bundleOf("bundleKey" to result)
                    )


                   navController.navigate(EditNotesFragmentDirections.actionEditNotesFragmentToHomeFragment())
                }
                else ->
                {
                    updateNote()
                    navController.popBackStack()
                }


            }

        }


    }

    private fun updateNote() {
       if (note!=null)
       {
           notesViewModel.updateNote(
               Note(
                   note!!.id,
                   contentBinding.notesTitle.text.toString(),
                   contentBinding.notesDesc.getMD(),
                   currentDate,
                   color,
               )
           )
       }
    }
}