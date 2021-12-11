package com.example.jott_notes.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.jott_notes.R
import com.example.jott_notes.databinding.FragmentNotesPageBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class NotesPageBottomSheet : BottomSheetDialogFragment() {
    override fun getTheme():  Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(),theme)

    lateinit var binding: FragmentNotesPageBottomSheetBinding





//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentNotesPageBottomSheetBinding.inflate(layoutInflater,container,false)
        setListeners()
       // return inflater.inflate(R.layout.fragment_notes_page_bottom_sheet, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }

    private fun setListeners(){

        binding.fNote1.setOnClickListener{
            binding.fNote1.setImageResource(R.drawable.ic_check_24)


        }

        binding.fNote2.setOnClickListener{
            binding.fNote2.setImageResource(R.drawable.ic_check_24)

        }

        binding.fNote3.setOnClickListener{
            binding.fNote3.setImageResource(R.drawable.ic_check_24)

        }

    }


}


























//    companion object {
//
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            NotesPageBottomSheet().apply {
//                arguments = Bundle().apply {
//
//                }
//            }
//    }