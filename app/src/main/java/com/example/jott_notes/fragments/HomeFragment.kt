package com.example.jott_notes.fragments

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.jott_notes.mvvmstuff.Viewmodel.NoteViewModel
import com.example.jott_notes.MainActivity
import com.example.jott_notes.R
import com.example.jott_notes.adapters.NotesRvListAdapter
import com.example.jott_notes.databinding.FragmentHomeBinding
import com.example.jott_notes.utils.SwipeDelete
import com.example.jott_notes.utils.hideKeyboard
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialElevationScale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class HomeFragment : Fragment(R.layout.fragment_home) {


    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var rvListAdapter: NotesRvListAdapter

    //private lateinit var rvListAdapter : NotesRvAdapter
    //var notes = ArrayList<Note>()
    private val noteViewModel: NoteViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exitTransition = MaterialElevationScale(false).apply {
            duration = 300
        }

        enterTransition = MaterialElevationScale(true).apply {
            duration = 300
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeBinding = FragmentHomeBinding.bind(view)
        val activity = activity as MainActivity

        val navController = Navigation.findNavController(view)
        requireView().hideKeyboard()

        CoroutineScope(Dispatchers.Main).launch {
            delay(10)


            //activity.window.statusBarColor = Color.parseColor(R.color.menu_and_accents.toString())
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.statusBarColor = Color.parseColor("#3B51E3")
        }


        homeBinding.fbAddButton.setOnClickListener {
            homeBinding.appBarLayout.visibility = View.INVISIBLE
            navController.navigate(HomeFragmentDirections.actionHomeFragmentToEditNotesFragment())
        }

        homeBinding.innerFab.setOnClickListener {
            homeBinding.appBarLayout.visibility = View.INVISIBLE
            navController.navigate(HomeFragmentDirections.actionHomeFragmentToEditNotesFragment())
        }

//        noteActivityViewModel.getAllNote().observe(viewLifecycleOwner, {notesList ->
//            notes = notesList as ArrayList<Note>
//            homeBinding.RvNotes.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
//            rvListAdapter = NotesRvAdapter(requireContext(),notesList)
//            homeBinding.RvNotes.adapter = rvListAdapter
//
//        })


        //Rvview

        recyclerViewDisplay()
        //setUpRecyclerView(2)
        swipeDelete(homeBinding.RvNotes)


        homeBinding.searchViewQuery.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                homeBinding.noData.isVisible = false
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
               if (s.toString().isNotEmpty())
               {
                   val text = s.toString()
                   val query = "%$text%"
                   if (query.isNotEmpty())
                   {
                       noteViewModel.searchNote(query).observe(viewLifecycleOwner)
                       {
                           rvListAdapter.submitList(it)

                       }
                   }
                   else{
                       observerDataChanges()
                   }


               }
                else{
                    observerDataChanges()
               }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        homeBinding.searchViewQuery.setOnEditorActionListener{v,actionId,_ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH)
            {
                v.clearFocus()
                requireView().hideKeyboard()
            }
            return@setOnEditorActionListener true
        }



//        homeBinding.searchViewQuery.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
//            androidx.appcompat.widget.SearchView.OnQueryTextListener {
//
//            override fun onQueryTextSubmit(p0: String?): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(p0: String?): Boolean {
//                if (p0.toString().isNotEmpty()) {
//                    val text = p0.toString()
//                    val query = "%$text"
//
//                    if (query.isNotEmpty()) {
//                        noteViewModel.searchNote(query).observe(viewLifecycleOwner)
//                        {
//                            rvListAdapter.submitList(it)
//
//                        }
//                    } else {
//                        observerDataChanges()
//                    }
//                } else {
//                    observerDataChanges()
//                }
//                return true
//
//
//            }
//
//
//        })

        homeBinding.nestedScrollView.setOnScrollChangeListener { _, scrollX, scrollY, _, oldScrollY ->
            when {
                scrollY > oldScrollY -> {
                    homeBinding.chatFabText.isVisible = false
                }
                scrollX == scrollY -> {
                    homeBinding.chatFabText.isVisible = true
                }

                else -> {
                    homeBinding.chatFabText.isVisible = true
                }
            }
        }


    }

    private fun swipeDelete(rvNotes: RecyclerView) {

        val swipeToDeleteCallback = object : SwipeDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                val note = rvListAdapter.currentList[position]
                var actionButtonTapped = false
                noteViewModel.deleteNote(note)
                homeBinding.searchViewQuery.apply {
                    hideKeyboard()
                    clearFocus()
                }
                val snackBar = Snackbar.make(
                    requireView(),"Note Deleted",Snackbar.LENGTH_LONG
                ).addCallback((object :BaseTransientBottomBar.BaseCallback<Snackbar>(){
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)
                    }

                    override fun onShown(transientBottomBar: Snackbar?) {

                        transientBottomBar?.setAction("UNDO"){
                            noteViewModel.saveNote(note)
                            actionButtonTapped = true
                            homeBinding.noData.isVisible=false
                        }
                        super.onShown(transientBottomBar)
                    }
                })
                ).apply {
                    animationMode = Snackbar.ANIMATION_MODE_FADE
                    setAnchorView(R.id.fb_add_button)
                }
                snackBar.setActionTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.menu_and_accents
                    )
                )
                snackBar.show()


            }

        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(rvNotes)

    }

    private fun observerDataChanges() {
        noteViewModel.getAllNote().observe(viewLifecycleOwner) { list ->
            homeBinding.noData.isVisible = list.isEmpty()
            rvListAdapter.submitList(list)
        }
    }

    private fun recyclerViewDisplay() {
        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> setUpRecyclerView(2)
            Configuration.ORIENTATION_LANDSCAPE -> setUpRecyclerView(3)
        }
    }



    private fun setUpRecyclerView(gridCount: Int) {

        homeBinding.RvNotes.apply {
            layoutManager =
                StaggeredGridLayoutManager(gridCount, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            rvListAdapter = NotesRvListAdapter()
            rvListAdapter.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

            adapter = rvListAdapter
            postponeEnterTransition(300L, TimeUnit.MILLISECONDS)
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }
        observerDataChanges()


    }
}





//Log.e("@@@@","notesFinding : $newText")


