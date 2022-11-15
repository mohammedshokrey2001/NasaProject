package com.example.nasaproject.datalayer.localdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nasaproject.datalayer.localdatabase.astroid_db.room.AstroidDao
import com.example.nasaproject.datalayer.localdatabase.astroid_db.room.AstroidRoomModel
import com.example.nasaproject.datalayer.localdatabase.picture_db.PictureOfDayDao
import com.example.nasaproject.datalayer.localdatabase.picture_db.PictureOfDayModel

@Database(entities = [AstroidRoomModel::class,PictureOfDayModel::class], version = 1, exportSchema = false)

abstract class AstroidRoom :RoomDatabase(){
    abstract val astroidDao:AstroidDao
    abstract val pictureOfDayDao :PictureOfDayDao

}

private lateinit var INSTANCE :AstroidRoom

fun getDatabase(context: Context) :AstroidRoom {

    synchronized(AstroidRoom::class.java){
        if (!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                AstroidRoom::class.java,"astroidDB")
                .fallbackToDestructiveMigration().
                build()
        }
    }
    return INSTANCE
}