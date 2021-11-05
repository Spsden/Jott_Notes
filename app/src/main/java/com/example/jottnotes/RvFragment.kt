package com.example.jottnotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.codingwithme.notesapp.database.NotesDatabase
import kotlinx.android.synthetic.main.fragment_rv.*
import kotlinx.coroutines.coroutineScope


class RvFragment : BaseFragment() {

//    var arrNotes = ArrayList<Notes>()
//    var notesAdapter: NotesAdapter = NotesToRvAdapter()


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        main_page_rv.setHasFixedSize(true)
        main_page_rv.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

        launch {
            context?.let {
                var notes = NotesDatabase.getDatabase(it).noteDao().getAllNotes()
                main_page_rv.adapter = NotesToRvAdapter(notes)


            }
        }
    }


}