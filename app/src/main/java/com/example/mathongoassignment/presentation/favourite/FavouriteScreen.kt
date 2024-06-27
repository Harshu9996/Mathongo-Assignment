package com.example.mathongoassignment.presentation.favourite

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mathongoassignment.domain.Recipe
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Favourite(
    viewModel:FavouriteViewModel = getViewModel<FavouriteViewModel>(),
    onEvent:(FavouriteScreenEvent) -> Unit
) {

    val uiState = viewModel.uiState.collectAsState().value

    val isRefreshing by remember{
        mutableStateOf(false)
    }


    
    Scaffold {

        val pullRefreshState = rememberPullRefreshState(isRefreshing,{
                viewModel.onEvent(FavouriteScreenEvent.fetchFavouriteRecipes)
        })
        Box(modifier = Modifier.pullRefresh(pullRefreshState)){
            LazyColumn(modifier = Modifier.padding(it)){
                item {
                    Text(text = "Favourite", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))


                }

                if(uiState.favouriteRecipes!=null){
                    items(uiState.favouriteRecipes){item->

                        favRecipeListItem(item){
                            onEvent(FavouriteScreenEvent.onRecipeClick(it))
                        }

                        Spacer(modifier = Modifier.height(11.dp))

                    }
                }

            }


            PullRefreshIndicator(isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }


    }

}

@Composable
fun favRecipeListItem(recipe: Recipe, onRecipeClick:(recipe: Recipe)->Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp, end = 16.dp)
        .height(100.dp)
        .background(color = Color.Transparent)
        .border(border = BorderStroke(1.dp, Color(0xFFE7F0F8)))
        .clip(RoundedCornerShape(12.dp))
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