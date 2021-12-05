package com.example.jottnotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.codingwithme.notesapp.database.NotesDatabase
import com.codingwithme.notesapp.entities.Notes
import kotlinx.android.synthetic.main.fragment_rv.*
import kotlinx.coroutines.coroutineScope


class RvFragment : BaseFragment() {

    var arrNotes = ArrayList<Notes>()
     var notesAdapter: NotesToRvAdapter = NotesToRvAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rv, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RvFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        main_page_rv.setHasFixedSize(true)
        main_page_rv.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

        launch {
            context?.let {
                var notes = NotesDatabase.getDatabase(it).noteDao().getAllNotes()
                notesAdapter.setData(notes)
                arrNotes = notes as ArrayList<Notes>
                main_page_rv.adapter = notesAdapter


            }
        }

        notesAdapter.setOnClickListener(onCLicked)


    }

    private val onCLicked = object :NotesToRvAdapter.OnItemClickListener{
        override fun onClicked(noteId: Int) {

            var fragment : Fragment
            var bundle = Bundle()
            bundle.putInt("noteId",noteId)
            fragment = NotesPage.newInstance()
            fragment.arguments = bundle

            replaceFragment(fragment,false)


        }
    }

    fun replaceFragment(fragment: Fragment , istransition:Boolean) {
        val fragmentTransaction = requireActivity().supportFragmentManager
        val transaction = fragmentTransaction.beginTransaction()

        if (istransition) {
           transaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right)

        }
        transaction.replace(R.id.frame_layout,fragment).addToBackStack(fragment.javaClass.simpleName).commit()
    }

}

//fun replaceFragment(fragment: Fragment, istransition : Boolean)
//{
//    val editNotes = fragment
//    val manager = requireActivity().supportFragmentManager
//    val transaction = manager.beginTransaction()
//
//    if (istransition){
//        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
//
//    }
//
//
//    transaction.replace(R.id.frame_layout, editNotes).addToBackStack(null).commit()
//
//
//
//
//
//}