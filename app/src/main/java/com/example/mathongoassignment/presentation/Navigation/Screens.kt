package com.example.mathongoassignment.presentation.Navigation

import com.example.mathongoassignment.R

sealed class Screens(val route: String, val label: String,val iconId: Int) {
    object Home : Screens("home", "Home", R.drawable.baseline_home_24)
    object Favourite : Screens("favourite", "Favourite",R.drawable.baseline_favorite_border_24)

}