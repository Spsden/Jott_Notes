package com.example.jott_notes.fragments

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.jott_notes.R
import com.example.jott_notes.adapters.NotesRvAdapter
import com.example.jott_notes.databinding.FragmentHomeBinding
import com.example.jott_notes.mvvmstuff.Viewmodel.NotesViewModel

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
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
        (activity as AppCompatActivity?)!!.supportActionBar?.title = null
        (activity as AppCompatActivity?)!!.supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(requireContext(), R.color.menu_and_accents)
            )
        )



        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)


        binding.fbAddButton.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment_to_notesCreateFragment)
        }
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getNotes().observe(viewLifecycleOwner, { notesList ->
            binding.RvNotes.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            binding.RvNotes.adapter = NotesRvAdapter(requireContext(), notesList)

        })

        val searchButton = binding.searchViewQuery as SearchView
        searchButton.queryHint = "Search Notes ..."
        searchButton.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                notesFinding(query)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                notesFinding(newText)
                return true
            }
        })



    }

//    companion object {
//
//        fun newInstance(param1: String, param2: String) =
//            HomeFragment().apply {
//                arguments = Bundle().apply {
//
//                }
//            }
//    }

     fun notesFinding(newText: String?) {

       Log.e("@@@@","notesFinding : $newText")

    }
}


