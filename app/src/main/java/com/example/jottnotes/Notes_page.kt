package com.example.jottnotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_notes_page.*
import java.text.SimpleDateFormat
import java.util.*

class Notes_page : Fragment() {

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
        return inflater.inflate(R.layout.fragment_notes_page, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Notes_page().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val CurrentDate = sdf.format(Date())

        date.text = CurrentDate

        note_back_button.setOnClickListener{
            saveNote()
            replaceFragment(Mainpage(),true)


        }
    }

    private fun saveNote(){

    }

    fun replaceFragment(fragment: Fragment , istransition : Boolean)
    {
        val manager = requireActivity().supportFragmentManager
        val transaction = manager.beginTransaction()

        if (istransition){
            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
        transaction.replace(R.id.notes_page,fragment).commit()
    }


}