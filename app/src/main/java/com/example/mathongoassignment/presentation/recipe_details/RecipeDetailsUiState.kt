package com.example.mathongoassignment.presentation.recipe_details

import com.example.mathongoassignment.domain.PopularRecipes
import com.example.mathongoassignment.domain.RecipeInformation
import com.example.mathongoassignment.domain.Recipes

data class RecipeDetailsUiState (
    val isLoading: Boolean=false,
    val error: String?=null,
    val recipeDetails: RecipeInformation?=null
)