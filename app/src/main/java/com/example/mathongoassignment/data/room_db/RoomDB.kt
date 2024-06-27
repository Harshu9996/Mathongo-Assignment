package com.example.mathongoassignment.data.room_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mathongoassignment.data.room_db.entity.RoomRecipe

@Database(entities = [RoomRecipe::class], version = 1, exportSchema = false)
abstract class RoomDB : RoomDatabase() {
    abstract fun getDao() : RoomDAO
}