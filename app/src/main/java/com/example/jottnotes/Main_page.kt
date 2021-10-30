package com.example.jottnotes

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.fragment_main_page.*


class Mainpage : Fragment() {


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

        return inflater.inflate(R.layout.fragment_main_page, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       val manager: FragmentManager = (this.context as AppCompatActivity).supportFragmentManager

        floating_action_button.setOnClickListener {
            BottomSheet().show(manager, ContentValues.TAG)
        }

        }





}