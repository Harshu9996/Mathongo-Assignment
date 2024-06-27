package com.example.mathongoassignment.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathongoassignment.domain.Repository
import com.example.mathongoassignment.presentation.home.HomeUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    val repository: Repository
): ViewModel() {

    val TAG = "HomeViewModel"
    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(isPopularRecipesLoading = true,isAllRecipesLoading = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            fetchAllRecipes()
            fetchPopularRecipes()

        }

    }


    suspend fun fetchAllRecipes(){
            val response =  repository.getAllRecipes()
            if(response.isSuccess){
                //Check whether using this function will keep the latest state in flow or not
                Log.d(TAG, "fetchAllRecipes: "+response.getOrNull())
                _uiState.emit(_uiState.value.copy(isAllRecipesLoading = false, allRecipes = response.getOrNull()))
            }else{
                _uiState.emit(_uiState.value.copy(isAllRecipesLoading = false, allRecipesError = response.exceptionOrNull()?.localizedMessage))

            }


    }

    suspend fun fetchPopularRecipes(){


            val response =  repository.getPopularRecipes()
            if(response.isSuccess){
                //Check whether using this function will keep the latest state in flow or not
                Log.d(TAG, "fetchPopularRecipes: "+response.getOrNull())
                _uiState.emit(_uiState.value.copy(isPopularRecipesLoading = false, popularRecipes = response.getOrNull()))
            }else{
                _uiState.emit(_uiState.value.copy(isPopularRecipesLoading = false, popularRecipesError = response.exceptionOrNull()?.localizedMessage))

            }



    }





}