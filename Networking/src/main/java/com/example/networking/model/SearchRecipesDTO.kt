package com.example.networking.model

data class SearchRecipesDTO(
    val number: Int,
    val offset: Int,
    val results: List<SearchRecipe>,
    val totalResults: Int
)