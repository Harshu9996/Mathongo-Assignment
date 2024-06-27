package com.example.mathongoassignment.domain

data class RecipeInformation(
    val equipment: List<Equipment>,
    val extendedIngredients: List<ExtendedIngredient>,
    val id: Int,
    val image: String,
    val imageType: String,
    val instructions: String,
    val pricePerServing: Double,
    val readyInMinutes: Int,
    val servings: Int,
    val summary: String,
    val title: String,
)