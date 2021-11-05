package com.example.jottnotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codingwithme.notesapp.entities.Notes
import kotlinx.android.synthetic.main.rvcard.view.*

 class NotesToRvAdapter(val arraylist : List<Notes>) : RecyclerView.Adapter<NotesToRvAdapter.NotesViewHolder>() {

    class NotesViewHolder(view: View) :RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.rvcard,parent,false)
        return NotesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arraylist.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.itemView.title_thumb.text = arraylist[position].title
        holder.itemView.note_desc_thumb.text = arraylist[position].noteText
    }

}