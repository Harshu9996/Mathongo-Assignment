package com.example.mathongoassignment.presentation.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mathongoassignment.R
import com.example.mathongoassignment.domain.PopularRecipe
import com.example.mathongoassignment.domain.Recipe
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = getViewModel<HomeViewModel>(),
    onEvents: (HomeScreenEvents)->Unit
) {
    val TAG = "HomeScreen"



    val uiState = viewModel.uiState.collectAsState().value


    Log.d(TAG, "HomeScreen: All recipes = "+uiState.allRecipes)
    Log.d(TAG, "HomeScreen: Popular recipes = "+uiState.popularRecipes)

    Scaffold{
        Box(modifier = Modifier
            .padding(it)) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState(),true)
            ) {

                Text(
                    text = "\uD83D\uDC4B Hey <user first name>",
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp)
//                        .width(184.dp)
                        .height(24.dp),
                    style = MaterialTheme.typography.displayMedium
                )

                Text(
                    text = "Discover tasty and healthy receipt",
                    modifier = Modifier
                        .padding(start = 16.dp)
//                        .width(184.dp)
                        .height(24.dp),
                    style = MaterialTheme.typography.bodySmall
                )

                Box(
                    modifier = Modifier
                        .padding(top = 12.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                        .height(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(color = Color(0xFFF2F7FD))
                        .clickable { onEvents(HomeScreenEvents.onSearchClick) }
                ){
                    Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.height(40.dp)) {
                        Icon(painter = painterResource(id = R.drawable.baseline_search_24), contentDescription = "Search",
                            modifier = Modifier
                                .size(20.dp)
                                .padding(start = 8.dp))
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Search any recipe", style = MaterialTheme.typography.bodyLarge)
                    }
                }

//                SearchBar(
//                    query = "",
//                    onQueryChange = {},
//                    onSearch = {},
//                    active = false,
//                    onActiveChange = {},
//                    modifier = Modifier
//                        .padding(top = 12.dp, start = 16.dp, end = 16.dp)
//                        .fillMaxWidth()
////                        .height(40.dp)
//                        .clip(RoundedCornerShape(12.dp))
//                        .clickable { onEvents(HomeScreenEvents.onSearchClick) },
//                    leadingIcon = {
//                        Icon(
//                            painter = painterResource(id = R.drawable.baseline_search_24),
//                            contentDescription = "Search"
//                        )
//                    },
////                    trailingIcon = {
////                        Icon(
////                            painter = painterResource(id = R.drawable.baseline_close_24),
////                            contentDescription = "Close"
////                        )
////                    },
//                    shape = RoundedCornerShape(12.dp),
//                    placeholder = {
//                        Text(text = "Search any recipe", style = MaterialTheme.typography.bodyLarge)
//                    }
//                ) {
//
//                }

                Text(
                    text = "Popular Recipes",
                    modifier = Modifier
                        .padding(start = 16.dp, top = 24.dp)
                        .width(127.dp)
                        .height(24.dp),
                    style = MaterialTheme.typography.titleLarge
                )

                LazyRow(modifier = Modifier.padding(start = 16.dp, top = 12.dp)) {
                    if (uiState.isPopularRecipesLoading) {
                        item {
                            CircularProgressIndicator()
                        }
                    } else if (uiState.popularRecipes?.recipes?.isNotEmpty() == true) {
                        items(uiState.popularRecipes.recipes) { item ->
                            PopularRecipeUi(popularRecipe = item)
                            Spacer(modifier = Modifier.width(11.dp))
                        }
                    }
                }

                Text(
                    text = "All recipes",
                    modifier = Modifier
                        .padding(start = 16.dp, top = 24.dp)
                        .width(84.dp)
                        .height(24.dp),
                    style = MaterialTheme.typography.titleLarge
                )

                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 12.dp, end = 16.dp)
                        .fillMaxWidth()
                ) {
                    if (uiState.isAllRecipesLoading) {
//                        item {
                            CircularProgressIndicator()
//                        }
                    } else if (uiState.allRecipes?.results?.isNotEmpty() == true) {

//                        items(uiState.allRecipes.results) { item ->
//                            AllRecipeUi(recipe = item)
//                            Spacer(modifier = Modifier.height(11.dp))
//
//                        }
                        uiState.allRecipes.results.forEach{ item ->
                            AllRecipeUi(recipe = item){
                                onEvents(HomeScreenEvents.onRecipeClick(it))
                            }
                            Spacer(modifier = Modifier.height(11.dp))

                        }
                    }

                }

            }


        }

    }





}


@Preview
@Composable
fun HomeScreenPreview() {
//    HomeScreen()
}

@Composable
fun PopularRecipeUi(popularRecipe: PopularRecipe) {

    //Apply Border
    Box(
        modifier = Modifier
            .size(156.dp)
            .clip(RoundedCornerShape(14.dp))
    ) {

        AsyncImage(model = popularRecipe.image, contentDescription = popularRecipe.title,
            modifier = Modifier
                .size(156.dp)
                .clip(RoundedCornerShape(14.dp)),
            contentScale = ContentScale.Crop)


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(156.dp) // Height of the gradient overlay
                .graphicsLayer { alpha = 0.80f } // Ensure the gradient is drawn on top
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = 0f,
                        endY = 400f // Same as the height of the gradient overlay
                    )
                )
                .align(Alignment.BottomCenter)
        )

        Text(
            text = popularRecipe.title.trim(),
            modifier = Modifier
//                .width(132.dp)
//                .height(20.dp)
                .padding(start = 12.dp, bottom = 30.dp)
                .align(Alignment.BottomEnd),
            style = MaterialTheme.typography.labelMedium,
            color = Color.White
        )

        Text(
            text = "Ready in " + popularRecipe.readyInMinutes + " min",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 4.dp, end = 12.dp, bottom = 12.dp)
                .align(Alignment.BottomEnd),
            color = Color.White
        )
    }

}

@Composable
fun AllRecipeUi(recipe: Recipe, onRecipeClick:(recipe:Recipe)->Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .background(color = Color.Transparent)
        .clip(RoundedCornerShape(12.dp))
        .border(border = BorderStroke(1.dp, Color(0xFFE7F0F8)))

        .clickable { onRecipeClick(recipe) }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = recipe.image, contentDescription = recipe.title,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(text = recipe.title, modifier = Modifier
                    .padding(start = 4.dp)
                    .width(157.dp)
                    .height(20.dp),
                    style = MaterialTheme.typography.labelSmall)

                //Fetch time from server
                Text(text = "Ready in 25 min", modifier = Modifier
                    .padding(start = 4.dp, top = 4.dp)
                    .width(91.dp)
                    .height(14.dp),
                    style = MaterialTheme.typography.bodyMedium)
            }
        }
    }

}