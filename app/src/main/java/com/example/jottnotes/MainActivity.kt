package com.example.jottnotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



<<<<<<< HEAD
=======



>>>>>>> Sp_main
        replaceFragment(Mainpage(), true)
    }

    //this is to change from mainactivity to main_page fragment.
    //the replacefragment class takes fragment(the one which will replace the mainactivity) and istransition as arguments.
    //if the boolean istransition is true then start the animation.
    //also this same function has the replace(one you are in, the one you want to go)
    //the addtobackstack is a stack maker function.

    private fun replaceFragment(fragment: Fragment, istransition: Boolean) {
        val fragmentTransition = supportFragmentManager.beginTransaction()

        if (istransition) {
            fragmentTransition.setCustomAnimations(
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left
            )

        }
        fragmentTransition.replace(R.id.frame_layout, fragment)
            .addToBackStack(fragment.javaClass.simpleName).commit()
    }
}