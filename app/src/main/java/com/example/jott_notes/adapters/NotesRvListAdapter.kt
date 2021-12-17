package com.example.jott_notes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jott_notes.R
import com.example.jott_notes.databinding.RvcardBinding
import com.example.jott_notes.fragments.HomeFragmentDirections
import com.example.jott_notes.mvvmstuff.entity.Note
import com.example.jott_notes.utils.hideKeyboard
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.MarkwonVisitor
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin
import io.noties.markwon.ext.tasklist.TaskListPlugin
import org.commonmark.node.SoftLineBreak
import kotlinx.coroutines.NonDisposableHandle.parent


class NotesRvListAdapter : ListAdapter<Note, NotesRvListAdapter.NotesViewHolder>(DiffUtilCallBack()) {


    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val rvCardBinding = RvcardBinding.bind(itemView)

        val cardTitle : MaterialTextView = rvCardBinding.titleThumb
        val noteDescription : MaterialTextView = rvCardBinding.noteDescThumb
        val date : MaterialTextView = rvCardBinding.noteDate
        val rvCardParent : MaterialCardView = rvCardBinding.rvCardParent
        val markWon = Markwon.builder(itemView.context).usePlugin(StrikethroughPlugin.create())
            .usePlugin(TaskListPlugin.create(itemView.context))
            .usePlugin(object : AbstractMarkwonPlugin() {
                override fun configureVisitor(builder: MarkwonVisitor.Builder) {
                    super.configureVisitor(builder)
                    builder.on(
                        SoftLineBreak::class.java
                    ) { visitor, _-> visitor.forceNewLine() }
                }
            }).build()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rvcard,parent,false)
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        getItem(position).let{note ->
            holder.apply {
               rvCardParent.transitionName = "recyclerView_${note.id}"
                cardTitle.text = note.title
                markWon.setMarkdown(noteDescription,note.notesDesc)
                date.text = note.date
                rvCardParent.setCardBackgroundColor(note.color)

                itemView.setOnClickListener {

                    val action = HomeFragmentDirections.actionHomeFragmentToEditNotesFragment().setNote(note)
//                        note = Note(
//                            getItem(position).id,
//                            title = note.title,
//                            notesDesc = note.notesDesc,
//                            date = note.date,
//                            color = note.color
//                        )
//                    )

                    val extras = FragmentNavigatorExtras(rvCardParent to "recyclerView_${note.id}")
                    it.hideKeyboard()
                    Navigation.findNavController(it).navigate(action,extras)


                }
                noteDescription.setOnClickListener {

                 val action = HomeFragmentDirections.actionHomeFragmentToEditNotesFragment().setNote(note)

                    val extras = FragmentNavigatorExtras(rvCardParent to "recyclerView_${note.id}")
                    it.hideKeyboard()
                    Navigation.findNavController(it).navigate(action,extras)


                }
            }
        }

    }
}