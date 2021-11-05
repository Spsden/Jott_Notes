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
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_main_page.*


class Mainpage : Fragment() {

    val fragarray = ArrayList<Fragment>()


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

<<<<<<< HEAD
        val manager: FragmentManager = (this.context as AppCompatActivity).supportFragmentManager

=======
        //for bottom sheet transaction
        val manager: FragmentManager = (this.context as AppCompatActivity).supportFragmentManager
>>>>>>> Sp_main
        floating_action_button.setOnClickListener {
            BottomSheet().show(manager, ContentValues.TAG)
        }

<<<<<<< HEAD
=======
        //for launching Viewpager fragment
        val tabLayout = activity?.findViewById<TabLayout>(R.id.tabs)
        val viewpager2 = activity?.findViewById<ViewPager2>(R.id.View_pager)

        fragarray.add(RvFragment())

        val adapter = ViewPagerAdapter(requireActivity().supportFragmentManager,lifecycle)
        viewpager2?.adapter = adapter

        if (viewpager2 != null) {
            if (tabLayout != null) {
                TabLayoutMediator(tabLayout, viewpager2){tab,position ->

                    when(position){
                        0 -> tab.text = "Notes"
                        1 -> tab.text = "Folders"
                    }
                }.attach()
            }
        }



>>>>>>> Sp_main
    }


}