package com.example.networking.remote

import com.example.networking.model.RandomRecipesDTO
import com.example.networking.model.RecipeInformationDTO
import com.example.networking.model.SearchRecipesDTO
import com.example.networking.model.SimilarRecipesDTOItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiDAO {


    @GET("complexSearch")
    suspend fun getAllRecipes(@Query("apiKey") apiKey:String = "4a82657cf04d4d81b960a479b532fcdf") : Response<SearchRecipesDTO>

    @GET("random")
    suspend fun getRandomRecipes(@Query("apiKey") apiKey:String = "4a82657cf04d4d81b960a479b532fcdf") : Response<RandomRecipesDTO>

    @GET("{id}/information")
    suspend fun getRecipeInformation(@Path("id") id: String, @Query("apiKey") apiKey:String = "4a82657cf04d4d81b960a479b532fcdf") : Response<RecipeInformationDTO>

    @GET("complexSearch")
    suspend fun searchRecipe(@Query("query") query:String, @Query("apiKey") apiKey:String = "4a82657cf04d4d81b960a479b532fcdf") :Response<SearchRecipesDTO>

    @GET("{id}/similar")
    suspend fun getSimilarRecipe(@Path("id") id:String, @Query("apiKey") apiKey:String = "4a82657cf04d4d81b960a479b532fcdf") : Response<List<SimilarRecipesDTOItem>>
}