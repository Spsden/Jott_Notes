package com.example.jott_notes.fragments

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.jott_notes.R
import com.example.jott_notes.adapters.NotesRvAdapter
import com.example.jott_notes.databinding.FragmentHomeBinding
import com.example.jott_notes.mvvmstuff.Viewmodel.NotesViewModel
import com.example.jott_notes.mvvmstuff.entity.Notes
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    val viewModel: NotesViewModel by viewModels()
    var notes = ArrayList<Notes>()
    lateinit var adapter: NotesRvAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            val toolbar: Toolbar = binding.toolbar
            //val newActionbar = binding.toolbar
            (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)

            val collapsingToolbar :CollapsingToolbarLayout = binding.collapsingToolbarLayout
            collapsingToolbar.title = "Jott Notes"

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {




        (activity as AppCompatActivity?)!!.supportActionBar?.title = "Jott Notes"
        (activity as AppCompatActivity?)!!.supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(requireContext(), R.color.menu_and_accents)
            )
        )
        setHasOptionsMenu(true)



        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)


        binding.fbAddButton.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment_to_notesCreateFragment)
        }
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //recyclerviewcreator || layoutManager

        viewModel.getNotes().observe(viewLifecycleOwner, { notesList ->
            notes = notesList as ArrayList<Notes>
            binding.RvNotes.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = NotesRvAdapter(requireContext(),notesList)
            binding.RvNotes.adapter = adapter

        })


        //SearchQueries

        binding.cardView.searchViewQuery.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                var tempArr = ArrayList<Notes>()

                for (i in notes){
                    if (i.title.toLowerCase(Locale.getDefault()).contains(p0.toString()) || i.notesdesc.toLowerCase(Locale.getDefault()).contains(p0.toString()))
                    {
                        tempArr.add(i)
                    }
                }

                adapter.findingNotes(tempArr)
                adapter.notifyDataSetChanged()
                return true
            }

        })



//    companion object {
//
//        fun newInstance(param1: String, param2: String) =
//            HomeFragment().apply {
//                arguments = Bundle().apply {
//
//                }
//            }
//    }




    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.moremain,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


}





//Log.e("@@@@","notesFinding : $newText")


