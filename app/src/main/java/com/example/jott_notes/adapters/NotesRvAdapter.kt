//package com.example.jott_notes.adapters
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.jott_notes.databinding.RvcardBinding
//import com.example.jott_notes.mvvmstuff.entity.Note
//
//class NotesRvAdapter(val requireContext: Context, var notesList: List<Note>) :
//RecyclerView.Adapter<NotesRvAdapter.NotesViewHolder>() {
//
//    fun findingNotes(newSearchedList: ArrayList<Note>) {
//        notesList = newSearchedList
//
//
//    }
//
//
//    class NotesViewHolder(val binding: RvcardBinding) : RecyclerView.ViewHolder(binding.root)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
//        return NotesViewHolder(
//            RvcardBinding.inflate(
//                LayoutInflater.from(parent.context), parent, false
//            )
//        )
//    }
//
//    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
//        holder.binding.titleThumb.text = notesList[position].title
//        holder.binding.noteDescThumb.text = notesList[position].notesDesc
//
////        if (notesList[position].prioritycolor != null){
////            holder.binding.rvCard.setCardBackgroundColor(Color.parseColor(notesList[position].prioritycolor))
////        }
////        else{
////            holder.binding.rvCard.setCardBackgroundColor(Color.parseColor(R.color.float_cards.toString()))
////        }
//
////        holder.binding.root.setOnClickListener {
////            val action = HomeFragmentDirections.actionHomeFragmentToEditNotesFragment(
////                dataTransfer = Notes(
////                    id = notesList[position].id,
////                    title = notesList[position].title,
////                    notesdesc = notesList[position].notesdesc,
////                    date = notesList[position].date
////                )
////            )
////            Navigation.findNavController(it).navigate(action)
////
////        }
//
//
//    }
//
//    override fun getItemCount() = notesList.size
//}
//
