package com.example.mathongoassignment.presentation.home

import com.example.mathongoassignment.domain.Recipe

sealed class HomeScreenEvents {
    data class onRecipeClick(val recipe:Recipe) : HomeScreenEvents()
    object onSearchClick: HomeScreenEvents()
}