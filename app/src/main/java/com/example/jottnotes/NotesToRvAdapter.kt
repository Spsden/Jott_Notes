package com.example.jottnotes

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jottnotes.entities.Notes
import kotlinx.android.synthetic.main.rvcard.view.*

 class NotesToRvAdapter():
     RecyclerView.Adapter<NotesToRvAdapter.NotesViewHolder>() {
     var listener : OnItemClickListener? = null

     interface OnItemClickListener {
         fun onClicked(noteId : Int)

     }

     var arrList = ArrayList<Notes>()
     class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

     }

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
         return NotesViewHolder(
             LayoutInflater.from(parent.context).inflate(R.layout.rvcard,parent,false)
         )
     }

     override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
         holder.itemView.title_thumb.text = arrList[position].title
         holder.itemView.note_desc_thumb.text = arrList[position].noteText

         if (arrList[position].color != null){
             holder.itemView.rv_card.setCardBackgroundColor(Color.parseColor(arrList[position].color))
         }else{
             holder.itemView.rv_card.setCardBackgroundColor(Color.parseColor(R.color.scroll_viewGrey.toString()))
         }

         holder.itemView.rv_card.setOnClickListener{
             listener!!.onClicked(arrList[position].id!!)
         }

     }

     override fun getItemCount(): Int {
         return arrList.size
     }
     
     fun setOnClickListener(listener1: OnItemClickListener){
         listener = listener1
     }

     fun setData(arrNotesList:List<Notes>){
         arrList = arrNotesList as ArrayList<Notes>
     }


 }