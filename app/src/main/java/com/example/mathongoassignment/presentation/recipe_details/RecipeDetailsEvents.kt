package com.example.mathongoassignment.presentation.recipe_details

sealed class RecipeDetailsEvents {
    data class fetchRecipeInfo(val recipeId:String) : RecipeDetailsEvents()
}