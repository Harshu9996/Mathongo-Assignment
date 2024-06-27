package com.example.mathongoassignment.data.room_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mathongoassignment.data.room_db.entity.RoomRecipe


@Dao
interface RoomDAO {


    @Insert
    suspend fun insertRecipe(recipe:RoomRecipe)

    @Query("SELECT * FROM recipes")
    suspend fun getRecipes() : List<RoomRecipe>
}