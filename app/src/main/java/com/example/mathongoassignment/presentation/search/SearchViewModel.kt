package com.example.mathongoassignment.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathongoassignment.domain.Repository
import com.example.mathongoassignment.presentation.recipe_details.RecipeDetailsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    val repository: Repository
):ViewModel() {

    val TAG = "SearchViewModel"

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()



    fun onEvent(events: SearchEvents){
        when(events){
            is SearchEvents.onSearch -> {

                viewModelScope.launch {
                    _uiState.emit(SearchUiState(
                        isLoading = true,
                        error = null,
                        suggestedRecipes = null,
                        searchedRecipe = null,
                        similarRecipes = null
                    ))
                    val response =  repository.searchRecipes(events.query)
                    if(response.isSuccess){
                        //Check whether using this function will keep the latest state in flow or not

                        Log.d(TAG, "onEvent: RecipeInformation = "+response.getOrNull())
                        _uiState.emit(
                            _uiState.value.copy(isLoading  = false,
                                suggestedRecipes = response.getOrNull(), error = "")
                        )
                    }else{
                        _uiState.emit(_uiState.value.copy(isLoading = false, error = response.exceptionOrNull()?.localizedMessage))

                    }
                }

            }

            is SearchEvents.onSuggestionClicked -> {

                viewModelScope.launch(Dispatchers.IO) {
                    _uiState.emit(SearchUiState(
                        isLoading = true,
                        error = null,
                        suggestedRecipes = null,
                        searchedRecipe = null,
                        similarRecipes = null
                    ))


                    searchRecipe(events.recipe.id.toString())
                    getSimilarRecipes(events.recipe.id.toString())



                }



            }

            is SearchEvents.markFavourite -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.addFavouriteRecipe(recipe = events.recipe)

                }
            }
        }

    }



    suspend fun searchRecipe(id:String){
        val response =  repository.getRecipeInformation(id)
        if(response.isSuccess){
            //Check whether using this function will keep the latest state in flow or not

            Log.d(TAG, "searchRecipe: searchRecipe = "+response.getOrNull())
            _uiState.emit(
                _uiState.value.copy(isLoading  = false, searchedRecipe = response.getOrNull(), error = "")
            )
        }else{
            _uiState.emit(_uiState.value.copy(isLoading = false, error = response.exceptionOrNull()?.localizedMessage))

        }
    }

    suspend fun getSimilarRecipes(recipeId:String){
        val response =  repository.getSimilarRecipes(recipeId = recipeId)
        if(response.isSuccess){
            //Check whether using this function will keep the latest state in flow or not

            Log.d(TAG, "getSimilarRecipes: getSimilarRecipes = "+response.getOrNull())
            _uiState.emit(
                _uiState.value.copy(isLoading  = false,
                    similarRecipes = response.getOrNull(), error = "")
            )
        }else{
            _uiState.emit(_uiState.value.copy(isLoading = false, error = response.exceptionOrNull()?.localizedMessage))

        }
    }



}