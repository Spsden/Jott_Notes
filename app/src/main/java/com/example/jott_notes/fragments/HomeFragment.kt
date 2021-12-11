package com.example.jott_notes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.jott_notes.R
import com.example.jott_notes.adapters.NotesRvAdapter
import com.example.jott_notes.databinding.FragmentHomeBinding
import com.example.jott_notes.mvvmstuff.Viewmodel.NotesViewModel

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    val viewModel : NotesViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
//        binding.collapsingToolbarLayout.apply {
//            setCollapsedTitleTextAppearance(R.style.collapsingActionBarCollapsed)
//            setExpandedTitleTextAppearance(R.style.collapsingActionBarExpanded)
//        }

//        val collapsingActionbar = binding.collapsingToolbarLayout
//        collapsingActionbar.setExpandedTitleTextAppearance(R.style.collapsingActionBarExpanded)
//        collapsingActionbar.setCollapsedTitleTextAppearance(R.style.collapsingActionBarCollapsed)
//
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)


        binding.fbAddButton.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment_to_notesCreateFragment)
        }
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getNotes().observe(viewLifecycleOwner,{notesList ->
            binding.RvNotes.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            binding.RvNotes.adapter = NotesRvAdapter(requireContext(),notesList)

        })
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}