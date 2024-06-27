package com.example.mathongoassignment.presentation.search

import com.example.mathongoassignment.domain.Recipe
import com.example.mathongoassignment.domain.RecipeInformation

sealed class SearchEvents {
    data class onSearch(val query:String) : SearchEvents()
    data class onSuggestionClicked(val recipe: Recipe) : SearchEvents()
    data class markFavourite(val recipe:RecipeInformation): SearchEvents()
}