package com.example.mathongoassignment.presentation.favourite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathongoassignment.domain.Repository
import com.example.mathongoassignment.presentation.recipe_details.RecipeDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavouriteViewModel(
    private val repository: Repository
): ViewModel() {

    val TAG = "FavouriteViewModel"

    private val _uiState = MutableStateFlow(FavouriteUiState())
    val uiState = _uiState.asStateFlow()

    init {
      fetchFavouriteRecipe()
    }


    fun onEvent(event: FavouriteScreenEvent){
        when(event){
            is FavouriteScreenEvent.fetchFavouriteRecipes -> {
                fetchFavouriteRecipe()
            }

            else -> {}
        }

    }

    fun fetchFavouriteRecipe(){
        viewModelScope.launch {
            _uiState.emit(
                FavouriteUiState(
                    isLoading = true,
                    error = null,
                    favouriteRecipes = null
                )
            )
            val response =  repository.getFavouriteRecipes()
            if(response.isSuccess){
                //Check whether using this function will keep the latest state in flow or not

                Log.d(TAG, "onEvent: RecipeInformation = "+response.getOrNull())
                _uiState.emit(
                    _uiState.value.copy(isLoading  = false, favouriteRecipes = response.getOrNull(), error = "")
                )
            }else{
                _uiState.emit(_uiState.value.copy(isLoading = false, error = response.exceptionOrNull()?.localizedMessage))

            }
        }
    }




}