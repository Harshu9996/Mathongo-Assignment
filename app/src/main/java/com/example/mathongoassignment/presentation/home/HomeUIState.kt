package com.example.mathongoassignment.presentation.home

import com.example.mathongoassignment.domain.PopularRecipes
import com.example.mathongoassignment.domain.Recipes

data class HomeUIState(
    val isPopularRecipesLoading: Boolean=false,
    val isAllRecipesLoading:Boolean=false,
    val popularRecipesError: String?=null,
    val allRecipesError: String?=null,
    val popularRecipes: PopularRecipes?=null,
    val allRecipes: Recipes?=null
)