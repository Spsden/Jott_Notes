package com.example.jott_notes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.jott_notes.databinding.RvcardBinding
import com.example.jott_notes.fragments.HomeFragmentDirections
import com.example.jott_notes.mvvmstuff.entity.Notes


class NotesRvAdapter(val requireContext: Context, var notesList: List<Notes>) :
    RecyclerView.Adapter<NotesRvAdapter.NotesViewHolder>() {

    fun findingNotes(newSearchedList: ArrayList<Notes>) {
        notesList = newSearchedList
        notifyDataSetChanged()

    }


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
        holder.binding.noteDescThumb.text = notesList[position].notesdesc

//        if (notesList[position].prioritycolor != null){
//            holder.binding.rvCard.setCardBackgroundColor(Color.parseColor(notesList[position].prioritycolor))
//        }
//        else{
//            holder.binding.rvCard.setCardBackgroundColor(Color.parseColor(R.color.float_cards.toString()))
//        }

        holder.binding.root.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToEditNotesFragment(
                dataTransfer = Notes(
                    id = notesList[position].id,
                    title = notesList[position].title,
                    notesdesc = notesList[position].notesdesc,
                    date = notesList[position].date
                )
            )
            Navigation.findNavController(it).navigate(action)

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