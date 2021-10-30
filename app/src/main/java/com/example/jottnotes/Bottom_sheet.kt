package com.example.jottnotes

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*


class BottomSheet : BottomSheetDialogFragment(){

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet , container , false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        Notes_add.setOnClickListener {
            replaceFragment(Notes_page(),true)
            Toast.makeText(context,"this is toast message",Toast.LENGTH_SHORT).show()
            dismiss()
        }


    }

    fun replaceFragment(fragment: Fragment, istransition : Boolean)
    {
        val editNotes = fragment
        val manager = requireActivity().supportFragmentManager
        val transaction = manager.beginTransaction()

        if (istransition){
            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)

        }


        transaction.replace(R.id.frame_layout, editNotes).addToBackStack(null).commit()





    }


}


//val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()
//
//if (istransition){
//    fragmentTransition.setCustomAnimations(android.R.anim.slide_out_right , android.R.anim.slide_in_left)
//}
//fragmentTransition.replace(R.id.frame_layout,fragment).addToBackStack(fragment.javaClass.simpleName)