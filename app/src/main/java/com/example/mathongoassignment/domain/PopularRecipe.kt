package com.example.mathongoassignment.domain

data class PopularRecipe(
    val id: Int,
    val image: String,
    val imageType: String,
    val readyInMinutes: Int,
    val title: String,
)