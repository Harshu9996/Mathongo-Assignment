package com.example.mathongoassignment.data.room_db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mathongoassignment.domain.Equipment
import com.example.mathongoassignment.domain.ExtendedIngredient

@Entity(tableName = "recipes")
data class RoomRecipe(
    @PrimaryKey
    val id: Int,
    val image: String,
    val imageType: String,
    val title: String
)