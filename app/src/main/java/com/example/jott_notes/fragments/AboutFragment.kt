package com.example.jott_notes.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.jott_notes.R


class AboutFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

        (activity as AppCompatActivity?)!!.supportActionBar?.title = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val github = view.findViewById<ImageView>(R.id.github)
        github.setOnClickListener {
          val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            intent.data = Uri.parse("https://github.com/Spsden/Jott_Notes")
            startActivity(intent)

        }
    }

//    companion object {
//
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            AboutFragment().apply {
//                arguments = Bundle().apply {
//
//                }
//            }
//    }
}