package com.example.mathongoassignment.data

import com.example.mathongoassignment.data.mappers.toDomain
import com.example.mathongoassignment.data.mappers.toRoom
import com.example.mathongoassignment.data.room_db.RoomDAO
import com.example.mathongoassignment.domain.PopularRecipes
import com.example.mathongoassignment.domain.Recipe
import com.example.mathongoassignment.domain.RecipeInformation
import com.example.mathongoassignment.domain.Recipes
import com.example.mathongoassignment.domain.Repository
import com.example.mathongoassignment.domain.SimilarRecipes
import com.example.mathongoassignment.domain.SimilarRecipesItem
import com.example.networking.remote.ApiDAO

class RepositoryImpl(
    private val apiDAO : ApiDAO,
    private val roomDAO:RoomDAO
) : Repository {
    override suspend fun getAllRecipes(): Result<Recipes> {

        return try {
            val response = apiDAO.getAllRecipes()
             if(response.isSuccessful){
                response.body()?.let {
                    Result.success(it.toDomain())
                }?: run {
                    Result.failure(Exception("An error occurred"))
                }
            }else{
                Result.failure(Exception("An error occurred"))
            }
        }catch (e:Exception){
            Result.failure(e)
        }

    }

    override suspend fun getPopularRecipes(): Result<PopularRecipes> {
        return try {
            val response = apiDAO.getRandomRecipes()
            return if(response.isSuccessful){
                response.body()?.let {
                    Result.success(it.toDomain())
                }?: run {
                    Result.failure(Exception("An error occurred"))
                }
            }else{
                Result.failure(Exception("An error occurred"))
            }

        }catch (e:Exception){
            Result.failure(e)
        }

    }

    override suspend fun addFavouriteRecipe(recipe : RecipeInformation) {


        roomDAO.insertRecipe(recipe.toRoom())

    }

    override suspend fun getRecipeInformation(id: String): Result<RecipeInformation> {

        return try {
            val response = apiDAO.getRecipeInformation(id)
            if(response.isSuccessful){
                response.body()?.let {
                    Result.success(it.toDomain())
                }?: run {
                    Result.failure(Exception("An error occurred"))
                }
            }else{
                Result.failure(Exception("An error occurred"))
            }
        }catch(e:Exception){
            Result.failure(e)
        }

    }

    override suspend fun searchRecipes(query : String): Result<Recipes> {
        return try {
            val response = apiDAO.searchRecipe(query = query)
            if(response.isSuccessful){
                response.body()?.let {
                    Result.success(it.toDomain())
                }?: run {
                    Result.failure(Exception("An error occurred"))
                }
            }else{
                Result.failure(Exception("An error occurred"))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun getSimilarRecipes(recipeId: String): Result<List<SimilarRecipesItem>> {
        return try {
            val response = apiDAO.getSimilarRecipe(id = recipeId)
            if(response.isSuccessful){
                response.body()?.let {
                    Result.success(it.toDomain())
                }?: run {
                    Result.failure(Exception("An error occurred"))
                }
            }else{
                Result.failure(Exception("An error occurred"))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun getFavouriteRecipes(): Result<List<Recipe>> {
        return Result.success(roomDAO.getRecipes().map {
            it.toDomain()
        })

    }

}