package com.example.jott_notes.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.jott_notes.R
import com.example.jott_notes.databinding.RvcardBinding
import com.example.jott_notes.mvvmstuff.entity.Notes


class NotesRvAdapter(val requireContext: Context, val notesList: List<Notes>) :
    RecyclerView.Adapter<NotesRvAdapter.NotesViewHolder>() {


    class NotesViewHolder(val binding: RvcardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            RvcardBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.binding.titleThumb.text = notesList[position].title
        holder.binding.noteDescThumb.text = notesList[position].title

        if (notesList[position].prioritycolor != null){
            holder.binding.rvCard.setCardBackgroundColor(Color.parseColor(notesList[position].prioritycolor))
        }
        else{
            holder.binding.rvCard.setCardBackgroundColor(Color.parseColor(R.color.float_cards.toString()))
        }



    }

    override fun getItemCount() = notesList.size


}









//when (notesList[position].prioritycolor) {
//    "0" -> {
//        holder.binding.rvCard.setCardBackgroundColor(Color.parseColor(R.color.float_cards.toString()))
//
//    }
//
//    "1" -> {
//        holder.binding.rvCard.setCardBackgroundColor(Color.parseColor(R.color.holo_purple.toString()))
//
//    }
//
//    "2" -> holder.binding.rvCard.setCardBackgroundColor(Color.parseColor(R.color.orange.toString()))
//
//    "3" -> {
//        holder.binding.rvCard.setCardBackgroundColor(Color.parseColor(R.color.colorgreen.toString()))
//
//    }
//}