package com.example.mathongoassignment.presentation.favourite

import com.example.mathongoassignment.domain.Recipe

sealed class FavouriteScreenEvent {
    data class onRecipeClick(val recipe:Recipe): FavouriteScreenEvent()
    object fetchFavouriteRecipes : FavouriteScreenEvent()
}