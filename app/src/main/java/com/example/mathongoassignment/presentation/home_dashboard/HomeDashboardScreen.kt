package com.example.mathongoassignment.presentation.home_dashboard

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.mathongoassignment.presentation.favourite.Favourite
import com.example.mathongoassignment.presentation.favourite.FavouriteScreenEvent
import com.example.mathongoassignment.presentation.home.HomeScreen
import com.example.mathongoassignment.presentation.home.HomeScreenEvents
import com.example.mathongoassignment.presentation.recipe_details.RecipeDetails
import com.example.mathongoassignment.presentation.recipe_details.RecipeDetailsEvents
import com.example.mathongoassignment.presentation.recipe_details.RecipeDetailsViewModel
import com.example.mathongoassignment.presentation.search.SearchScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeDashBoardScreen(
    navController: NavHostController
) {

    val screens = listOf(Screens.Home, Screens.Favourite)
    Scaffold(modifier = Modifier.animateContentSize(),bottomBar = {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        if(navBackStackEntry?.destination?.route.equals(Screens.Home.route) || navBackStackEntry?.destination?.route.equals(Screens.Favourite.route))
        {
            BottomNavigation(backgroundColor = MaterialTheme.colorScheme.surface) {

                //Show Bottom Navigation Bar
                screens.forEach { screen ->
                    BottomNavigationItem(selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {

                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(painter = painterResource(id = screen.iconId), contentDescription = "Home")
                        },
                        label = {
                            Text(
                                text = screen.label,
//                            modifier = Modifier
//                                .height(16.dp)
//                                .width(34.dp),
                                style = MaterialTheme.typography.titleSmall
                            )
                        })

                }









            }

        }

    }) { innerPadding ->
        NavHost(navController, startDestination = Screens.Home.route, Modifier.padding(innerPadding)) {
            composable(Screens.Home.route) {
                HomeScreen(){event->

                when(event){
                    is HomeScreenEvents.onRecipeClick -> {

                        navController.navigate("recipe_details/"+event.recipe.id)

                    }
                    is HomeScreenEvents.onSearchClick -> {
                        navController.navigate("search")

                    }
                }
            } }
            composable(Screens.Favourite.route) {
                Favourite{event->
                    when(event){
                        is FavouriteScreenEvent.onRecipeClick -> {
                            navController.navigate("recipe_details/"+event.recipe.id)
                        }

                        else -> {}
                    }
                }
            }

            composable(route="recipe_details/{recipeId}",
                arguments = listOf(navArgument("recipeId"){
                    type = NavType.StringType

            })){navBackStackEntry ->
                val recipeId = navBackStackEntry.arguments?.getString("recipeId")
                val viewModel = getViewModel<RecipeDetailsViewModel>()

                if(recipeId!=null){
                    viewModel.onEvent(RecipeDetailsEvents.fetchRecipeInfo(recipeId))
                }
                RecipeDetails(viewModel = viewModel)

            }


            composable("search"){
                SearchScreen()
            }
        }




    }


}