package com.example.jott_notes.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.jott_notes.mvvmstuff.entity.Note

class DiffUtilCallBack : DiffUtil.ItemCallback<Note>(){
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return  oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }
}