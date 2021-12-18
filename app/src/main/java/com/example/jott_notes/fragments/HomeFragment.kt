package com.example.jott_notes.fragments

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.jott_notes.R
import com.example.jott_notes.adapters.NotesRvAdapter
import com.example.jott_notes.databinding.FragmentHomeBinding
import com.example.jott_notes.mvvmstuff.Viewmodel.NotesViewModel
import com.example.jott_notes.mvvmstuff.entity.Notes
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialElevationScale
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    val addednotes by navArgs<EditNotesFragmentArgs>()

    private lateinit var navController: NavController
    //var defaultColor = requireContext().getColor(R.color.menu_and_accents)

    lateinit var binding: FragmentHomeBinding
    val viewModel: NotesViewModel by viewModels()
    var notes = ArrayList<Notes>()
    private lateinit var adapter: NotesRvAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        exitTransition = MaterialElevationScale(false).apply {
            duration = 300
        }

        enterTransition = MaterialElevationScale(true).apply {
            duration = 300
        }
        arguments?.let {

            val toolbar: Toolbar = binding.toolbar
            //val newActionbar = binding.toolbar
            (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)

            val collapsingToolbar: CollapsingToolbarLayout = binding.collapsingToolbarLayout
            collapsingToolbar.title = "Jott Notes"

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        (activity as AppCompatActivity?)!!.supportActionBar?.title = ""
        (activity as AppCompatActivity?)!!.supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(requireContext(), R.color.menu_and_accents)
            )
        )
        setHasOptionsMenu(true)



        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)



        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.innerFab.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment_to_notesCreateFragment)
        }

        binding.chatFabText.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment_to_notesCreateFragment)
        }

        //recyclerviewcreator || layoutManager

        viewModel.getNotes().observe(viewLifecycleOwner, { notesList ->
            notes = notesList as ArrayList<Notes>
            notesList.reverse()

            binding.RvNotes.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = NotesRvAdapter(requireContext(), notesList)
            binding.RvNotes.adapter = adapter.apply {
                postponeEnterTransition(300L, TimeUnit.MILLISECONDS)
                adapter.stateRestorationPolicy =
                    RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                view.viewTreeObserver.addOnDrawListener {
                    startPostponedEnterTransition()
                    true
                }

                binding.noData.isVisible = adapter.itemCount == 0
                ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(Rv_notes)
            }


        })


        //SearchQueries

        binding.cardView.searchViewQuery.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                var tempArr = ArrayList<Notes>()

                for (i in notes) {
                    if (i.title.toLowerCase(Locale.getDefault())
                            .contains(p0.toString()) || i.notesdesc.toLowerCase(Locale.getDefault())
                            .contains(p0.toString())
                    ) {
                        tempArr.add(i)
                    }
                }

                adapter.findingNotes(tempArr)
                adapter.notifyDataSetChanged()
                return true
            }

        })

//        swipeToDelete(binding.RvNotes)


//    companion object {
//
//        fun newInstance(param1: String, param2: String) =
//            HomeFragment().apply {
//                arguments = Bundle().apply {
//
//                }
//            }
//    }

//        binding.nestedScrollView.addO

        binding.nestedScrollView.setOnScrollChangeListener { _, scrollX, scrollY, _, oldScrollY ->
            when {
                scrollY > oldScrollY -> {
                    binding.chatFabText.isVisible = false
                }
                scrollX == scrollY -> {
                    binding.chatFabText.isVisible = true
                }

                else -> {
                    binding.chatFabText.isVisible = true
                }
            }
        }




    }

    val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
        0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            val notes = adapter.notesList[position]
            viewModel.deleteNotes(notes.id!!)
            Snackbar.make(
                requireView(), "Note deleted Succesfully !", Snackbar.LENGTH_LONG
            ).apply {
                setAction(
                    "Undo"
                ) {
                    viewModel.addNotes(notes)
                }
                show()
            }

        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.moremain, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.aboutSection) {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_homeFragment_to_aboutFragment)

        }
        return super.onOptionsItemSelected(item)

    }


}


//Log.e("@@@@","notesFinding : $newText")


