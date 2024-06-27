package com.example.mathongoassignment.domain

data class Recipes(
    val number: Int,
    val offset: Int,
    val results: List<Recipe>,
    val totalResults: Int
)