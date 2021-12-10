package com.example.jott_notes.mvvmstuff.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Notes")
class Notes (

    @PrimaryKey(autoGenerate = true)
    var id:Int? = null,

    var title: String,
    var notesdesc: String,
    var date:String,
    var prioritycolor:String,
    var image_path:String,
    var web_link:String





)



//import androidx.room.ColumnInfo
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//import java.io.Serializable
//
//@Entity(tableName = "Notes")
//class Notes: Serializable {
//
//    @PrimaryKey(autoGenerate = true)
//    var id:Int? = null
//
//    @ColumnInfo(name = "title")
//    var title:String? = null
//
//    @ColumnInfo(name = "sub_title")
//    var subTitle:String? = null
//
//    @ColumnInfo(name = "date_time")
//    var dateTime:String? = null
//
//    @ColumnInfo(name = "note_text")
//    var noteText:String? = null
//
//    @ColumnInfo(name = "img_path")
//    var imgPath:String? = null
//
//    @ColumnInfo(name = "web_link")
//    var webLink:String? = null
//
//    @ColumnInfo(name = "color")
//    var color:String? = null
//
//
//    override fun toString(): String {
//
//        return "$title : $dateTime"
//
//    }
//}