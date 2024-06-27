package com.example.mathongoassignment.presentation.favourite

import com.example.mathongoassignment.domain.Recipe
import com.example.mathongoassignment.domain.Recipes

data class FavouriteUiState (
    val isLoading:Boolean=false,
    val error:String? = null,
    val favouriteRecipes:List<Recipe>?=null

)