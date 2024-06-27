package com.example.mathongoassignment.presentation.recipe_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathongoassignment.domain.Repository
import com.example.mathongoassignment.presentation.home.HomeUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(
    private val repository: Repository
):ViewModel() {

    private val _uiState = MutableStateFlow(RecipeDetailsUiState())
    val uiState = _uiState.asStateFlow()

    val TAG = "RecipeDetailsViewModel"



    fun onEvent(events: RecipeDetailsEvents){
        when(events){
            is RecipeDetailsEvents.fetchRecipeInfo -> {
                viewModelScope.launch {
                    _uiState.emit(RecipeDetailsUiState(
                        isLoading = true,
                        error = null,
                        recipeDetails = null
                    ))
                    val response =  repository.getRecipeInformation(events.recipeId)
                    if(response.isSuccess){
                        //Check whether using this function will keep the latest state in flow or not

                        Log.d(TAG, "onEvent: RecipeInformation = "+response.getOrNull())
                        _uiState.emit(
                            _uiState.value.copy(isLoading  = false, recipeDetails = response.getOrNull(), error = "")
                        )
                    }else{
                        _uiState.emit(_uiState.value.copy(isLoading = false, error = response.exceptionOrNull()?.localizedMessage))

                    }
                }
            }
        }

    }




}