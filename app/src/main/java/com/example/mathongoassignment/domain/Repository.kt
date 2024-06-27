package com.example.mathongoassignment.domain

interface Repository {

    suspend fun getAllRecipes() : Result<Recipes>

    suspend fun getPopularRecipes() : Result<PopularRecipes>

    suspend fun addFavouriteRecipe(recipe: RecipeInformation)

    suspend fun getRecipeInformation(id:String) : Result<RecipeInformation>

    suspend fun searchRecipes(query:String) : Result<Recipes>

    suspend fun getSimilarRecipes(recipeId: String): Result<List<SimilarRecipesItem>>

    suspend fun getFavouriteRecipes() : Result<List<Recipe>>


}