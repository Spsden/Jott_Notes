package com.example.jott_notes.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import androidx.core.graphics.toColorLong
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.example.jott_notes.databinding.RvcardBinding
import com.example.jott_notes.fragments.HomeFragmentDirections
import com.example.jott_notes.mvvmstuff.entity.Notes
import com.google.android.material.card.MaterialCardView
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.MarkwonVisitor
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin
import io.noties.markwon.ext.tasklist.TaskListPlugin
import kotlinx.android.synthetic.main.rvcard.view.*
import org.commonmark.node.SoftLineBreak


class NotesRvAdapter(val requireContext: Context, var notesList: List<Notes>) :
    RecyclerView.Adapter<NotesRvAdapter.NotesViewHolder>() {

    fun findingNotes(newSearchedList: ArrayList<Notes>) {
        notesList = newSearchedList


    }






    inner class NotesViewHolder(val binding: RvcardBinding) : RecyclerView.ViewHolder(binding.root)

    {



       // private val rvcardBinding = RvcardBinding.bind(itemView)

//        val cardTitle = rvcardBinding.titleThumb
//        val notesDescription = rvcardBinding.noteDescThumb
        //val rvCardParent: MaterialCardView = rvcardBinding.rvCard

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
            RvcardBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.binding.titleThumb.text = notesList[position].title
        holder.binding.noteDescThumb.text = notesList[position].notesdesc

        holder.binding.rvCard.strokeColor = notesList[position].color
        holder.binding.rvCard.transitionName = "recyclerView_${notesList[position].id}"
        val rvCardNew = holder.binding.rvCard.rootView




//        getItemId(position).let {notesList
//            holder.apply {
//                rvCardParent.transitionName = "recyclerView_${notesList[position].id}"
//
//            }
//        }



        holder.binding.root.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToEditNotesFragment(
                dataTransfer = Notes(
                    id = notesList[position].id,
                    title = notesList[position].title,
                    notesdesc = notesList[position].notesdesc,
                    date = notesList[position].date,
                    color = notesList[position].color
                )
            )
            val extras = FragmentNavigatorExtras(rvCardNew to "recyclerView_${notesList[position].id}")


            Navigation.findNavController(it).navigate(action,extras)

        }






    }


    override fun getItemCount() = notesList.size






}



