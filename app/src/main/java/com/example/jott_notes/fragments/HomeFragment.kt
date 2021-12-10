package com.example.jott_notes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.jott_notes.R
import com.example.jott_notes.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding


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


        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        binding.fbAddButton.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment_to_notesCreateFragment)
        }
        return binding.root


    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}