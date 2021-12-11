package com.example.jott_notes.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.jott_notes.R
import com.example.jott_notes.databinding.FragmentNotesPageBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class NotesPageBottomSheet : BottomSheetDialogFragment() {
    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)

    lateinit var binding: FragmentNotesPageBottomSheetBinding
    var priorityColor: String = "0"


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


        binding = FragmentNotesPageBottomSheetBinding.inflate(layoutInflater, container, false)
        binding.fNote0.setImageResource(R.drawable.ic_check_24)
        setListeners()
        // return inflater.inflate(R.layout.fragment_notes_page_bottom_sheet, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun setListeners() {

        binding.fNote0.setOnClickListener{
            priorityColor = "0"
            binding.fNote0.setImageResource(R.drawable.ic_check_24)
            binding.fNote1.setImageResource(0)
            binding.fNote2.setImageResource(0)
            binding.fNote3.setImageResource(0)

            val intent = Intent("bottom_sheet_color_action")
            intent.putExtra("action","dark")
            intent.putExtra("priorityColor",priorityColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

        binding.fNote1.setOnClickListener {
            priorityColor = "1"
            binding.fNote1.setImageResource(R.drawable.ic_check_24)
            binding.fNote2.setImageResource(0)
            binding.fNote3.setImageResource(0)
            binding.fNote0.setImageResource(0)

            val intent = Intent("bottom_sheet_color_action")
            intent.putExtra("action","purple")
            intent.putExtra("priorityColor",priorityColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)


        }

        binding.fNote2.setOnClickListener {
            priorityColor = "2"

            binding.fNote2.setImageResource(R.drawable.ic_check_24)
            binding.fNote1.setImageResource(0)
            binding.fNote3.setImageResource(0)
            binding.fNote0.setImageResource(0)

            val intent = Intent("bottom_sheet_color_action")
            intent.putExtra("action","orange")
            intent.putExtra("priorityColor",priorityColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

        }

        binding.fNote3.setOnClickListener {
            priorityColor = "3"

            binding.fNote3.setImageResource(R.drawable.ic_check_24)
            binding.fNote2.setImageResource(0)
            binding.fNote1.setImageResource(0)
            binding.fNote0.setImageResource(0)

            val intent = Intent("bottom_sheet_color_action")
            intent.putExtra("action","green")
            intent.putExtra("priorityColor",priorityColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

        }

    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NotesPageBottomSheet().apply {
                arguments = Bundle().apply {

                }
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