package com.example.jott_notes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jott_notes.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class DeleteBottomSheet : BottomSheetDialogFragment() {


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

        return inflater.inflate(R.layout.fragment_delete_bottom_sheet, container, false)
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DeleteBottomSheet().apply {
                arguments = Bundle().apply {

                }
            }
    }
}