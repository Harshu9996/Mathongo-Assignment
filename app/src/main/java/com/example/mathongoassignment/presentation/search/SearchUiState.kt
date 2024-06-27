package com.example.mathongoassignment.presentation.search

import android.provider.ContactsContract.Contacts.AggregationSuggestions
import com.example.mathongoassignment.domain.Recipe
import com.example.mathongoassignment.domain.RecipeInformation
import com.example.mathongoassignment.domain.Recipes
import com.example.mathongoassignment.domain.SimilarRecipes
import com.example.mathongoassignment.domain.SimilarRecipesItem

data class SearchUiState(
    val isLoading: Boolean=false,
    val error: String?=null,
    val suggestedRecipes: Recipes?=null,
    val searchedRecipe: RecipeInformation?=null,
    val similarRecipes: List<SimilarRecipesItem>?=null

)