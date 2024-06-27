package com.example.mathongoassignment.presentation.search

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ButtonColors
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mathongoassignment.R
import com.example.mathongoassignment.domain.ExtendedIngredient
import com.example.mathongoassignment.domain.Recipe
import com.example.mathongoassignment.domain.RecipeInformation
import com.example.mathongoassignment.domain.Recipes
import com.example.mathongoassignment.domain.SimilarRecipes
import com.example.mathongoassignment.domain.SimilarRecipesItem
import com.example.mathongoassignment.presentation.recipe_details.EquipmentUi
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = getViewModel<SearchViewModel>()
) {
    val TAG = "SearchScreen"

    val uiState = viewModel.uiState.collectAsState().value


    var query by rememberSaveable{
        mutableStateOf("")
    }

    var expandIngredients by rememberSaveable{
        mutableStateOf(false)
    }

    var expandfullRecipe by rememberSaveable{
        mutableStateOf(false)
    }

    var expandSimilarRecipe by rememberSaveable{
        mutableStateOf(false)
    }

    var markedFavourite = rememberSaveable {
        mutableStateOf(false)
    }

    val scaffoldState = rememberBottomSheetScaffoldState()

    val scope = rememberCoroutineScope()


    Log.d(TAG, "SearchScreen: uiState = "+uiState)


        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetPeekHeight = 0.dp,
           sheetContent = {
               if(uiState.searchedRecipe!=null){

                   Box(modifier = Modifier
                       .heightIn(min = 0.dp, max = 568.dp)
                       .fillMaxWidth()) {
                       Column {

                            Row(modifier= Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                                verticalAlignment = Alignment.CenterVertically
//                                horizontalArrangement = Arrangement.SpaceAround
                            ) {


                                IconButton(onClick = {
                                    scope.launch {
                                        scaffoldState.bottomSheetState.hide()
                                    }

                                },
                                    modifier = Modifier
                                        .padding(16.dp)) {
                                    Icon(painter = painterResource(id = R.drawable.outline_arrow_circle_left_24), contentDescription = "Back")

                                }

                                Text(text = uiState.searchedRecipe.title, style = MaterialTheme.typography.headlineLarge)

                                IconButton(onClick = {
                                    markedFavourite.value = !markedFavourite.value
                                   viewModel.onEvent(SearchEvents.markFavourite(uiState.searchedRecipe))
                                },
                                    modifier = Modifier
                                        .padding(16.dp)) {

                                    if(markedFavourite.value){
                                        Icon(painter = painterResource(id = R.drawable.baseline_favorite_24), contentDescription = "Remove Favourite")
                                    }else{
                                        Icon(painter = painterResource(id = R.drawable.baseline_favorite_border_24), contentDescription = "Mark Favourite")
                                    }

                                }




                            }


                           //Image box
                           Box(modifier = Modifier
                               .height(512.dp)
                               .fillMaxWidth())
                           {

                               Column {
                                   Box(modifier = Modifier
                                       .fillMaxWidth()
                                       .height(360.dp)){
                                       AsyncImage(model = uiState.searchedRecipe.image, contentDescription = "Recipe Image",
                                           modifier = Modifier
                                               .fillMaxWidth()
                                               .height(360.dp), contentScale = ContentScale.Crop)
                                   }


                                   Row(modifier = Modifier
                                       .padding(start = 16.dp, end = 16.dp, top = 20.dp)
                                       .fillMaxWidth(),
                                       verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween)
                                   {

                                       Box(modifier = Modifier
                                           .width(104.dp)
                                           .height(56.dp)
                                           .border(BorderStroke(width = 1.dp, Color(0xFFE7F0F8)))
                                           .clip(RoundedCornerShape(12.dp))){
                                           Column(modifier = Modifier
                                               .align(Alignment.Center)
                                               .padding(top = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                               Text(text = "Ready in", style = MaterialTheme.typography.headlineSmall)
                                               Spacer(modifier = Modifier.height(4.dp))
                                               Text(text = uiState.searchedRecipe.readyInMinutes.toString(), style = MaterialTheme.typography.displayLarge)
                                           }
                                       }

                                       Box(modifier = Modifier
                                           .width(104.dp)
                                           .height(56.dp)
                                           .border(BorderStroke(width = 1.dp, Color(0xFFE7F0F8)))
                                           .clip(RoundedCornerShape(12.dp))
                                       ){

                                           Column(modifier = Modifier
                                               .align(Alignment.Center)
                                               .padding(top = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                               Text(text = "Servings", style = MaterialTheme.typography.headlineSmall)
                                               Spacer(modifier = Modifier.height(4.dp))
                                               Text(text = uiState.searchedRecipe.servings.toString(), style = MaterialTheme.typography.displayLarge)
                                           }

                                       }

                                       Box(modifier = Modifier
                                           .width(104.dp)
                                           .height(56.dp)
                                           .clip(RoundedCornerShape(12.dp))
                                           .border(BorderStroke(width = 1.dp, Color(0xFFE7F0F8)))){

                                           Column(modifier = Modifier
                                               .align(Alignment.Center)
                                               .padding(top = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                               Text(text = "Price/serving", style = MaterialTheme.typography.headlineSmall)
                                               Spacer(modifier = Modifier.height(4.dp))
                                               Text(text = uiState.searchedRecipe.pricePerServing.toString(), style = MaterialTheme.typography.displayLarge)
                                           }

                                       }



                                   }






                               }


                               Button(
                                   onClick = {
                                             expandIngredients=true
                                   },
                                   modifier = Modifier
                                       .fillMaxWidth()
                                       .padding(16.dp)
                                       .align(Alignment.BottomCenter),
                                   colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE54900))
                               ) {

                                   Text(text = "Get ingredients", style = MaterialTheme.typography.displayLarge, color = Color.White)

                                   Spacer(modifier = Modifier.width(8.dp))

                                   Icon(painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24), contentDescription = "Get ingredients")

                               }



                           }


                       }


                       //Ingredient Box
                       if(expandIngredients){
                           IngredientsUI(expand = expandIngredients, ingredients = uiState.searchedRecipe.extendedIngredients) {
                               expandfullRecipe=true
                           }
                       }



                       //Full Recipe Box
                       if(expandfullRecipe){

                           FullRecipeUi(
                               expand = expandfullRecipe,
                               recipeInformation = uiState.searchedRecipe
                           ){
                               expandSimilarRecipe = true
                           }

                       }



                       //Similar recipe Box
                       if(uiState.similarRecipes!=null){
                           if(expandSimilarRecipe) {
                               SimilarRecipeUi(expand = expandSimilarRecipe, similarRecipes = uiState.similarRecipes) {

                                   //Collapse all boxes as shown in figma design
                                   expandIngredients=false
                                   expandfullRecipe=false
                                   expandSimilarRecipe=false

                               }

                           }

                       }



                   }

                   
                   
               }
           }
        ) {
            Column(modifier=Modifier.padding(it), horizontalAlignment = Alignment.CenterHorizontally) {

                SearchBar(
                    query = query,
                    onQueryChange = {it->
                                    query =it
                    },
                    onSearch = {
                        viewModel.onEvent(SearchEvents.onSearch(it))
                    },
                    active = false,
                    onActiveChange = {},
                    modifier = Modifier
                        .padding(top = 12.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth()
//                        .height(40.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = "Search"
                        )
                    },
                    trailingIcon = {
                        if(query.isNotEmpty()){
                            IconButton(onClick = {
                                query=""
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_close_24),
                                    contentDescription = "Close"
                                )
                            }

                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    placeholder = {
                        Text(text = "Search any recipe", style = MaterialTheme.typography.bodyLarge)
                    }
                ) {






                }


                LaunchedEffect(uiState.searchedRecipe){
                    scaffoldState.bottomSheetState.expand()
                }

                if(uiState.isLoading){
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }else if(uiState.suggestedRecipes!=null){

                    LazyColumn{
                        items(uiState.suggestedRecipes.results){recipe ->
                            SearchSuggestionUi(recipe = recipe){
                                viewModel.onEvent(SearchEvents.onSuggestionClicked(it))
                            }
                        }
                    }
                }







            }

        }
}


@Composable
fun SearchSuggestionUi(recipe: Recipe,onRecipeClick:(recipe:Recipe)->Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp, end = 16.dp)
        .height(80.dp)
        .clickable { onRecipeClick(recipe) },
        verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.outline_fastfood_24), contentDescription = "Food"
            , modifier = Modifier
                    .size(40.dp)
                    .padding(start = 16.dp))
        
            Spacer(modifier = Modifier.width(16.dp))

        Text(text = recipe.title, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun IngredientsUI(expand:Boolean,ingredients:List<ExtendedIngredient>,onFullRecipeClick:()->Unit) {
    val TAG = "SearchScreen"
    val expandedHeight = 512.dp // Desired height of the bottom sheet when expanded
    val collapsedHeight = 0.dp  // Height of the bottom sheet when collapsed
    var isExpanded by remember { mutableStateOf(expand) }

    val transition = updateTransition(targetState = isExpanded, label = "Bottom Sheet Transition")
    val height by animateDpAsState(
        targetValue = expandedHeight,
        label = "Height Animation"
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {



        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .background(MaterialTheme.colorScheme.surface)
        ){

            if(ingredients.isEmpty()){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }


            Column {

                Row(modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Ingredients", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.padding(start=16.dp))
                    IconButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(end = 16.dp)) {
                        Icon(painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24), contentDescription = null)
                    }
                }


//                if(ingredients.isNotEmpty()){
//                    Log.d(TAG, "IngredientsUI: ingredients = "+ingredients)
//                    LazyVerticalGrid(columns = GridCells.Fixed(count = 3),modifier = Modifier.padding(start = 16.dp, end = 16.dp)){
//                        items(ingredients){ingredient->
//                            IngredientUi(ingredient = ingredient)
//                        }
//
//
//                    }
//                }



            }
            Button(
                onClick = {
                    onFullRecipeClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE54900))
            ) {

                Text(text = "Get full recipe", style = MaterialTheme.typography.displayLarge, color = Color.White)

                Spacer(modifier = Modifier.width(8.dp))

                Icon(painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24), contentDescription = "Get full recipe")

            }

        }
    }



}


@Composable
fun IngredientUi(ingredient: ExtendedIngredient) {
    val TAG = "SearchScreen"

    Log.d(TAG, " Single IngredientUi: ingredient = "+ingredient)

    Column(modifier = Modifier
        .height(108.dp)
        .width(86.dp)) {


        AsyncImage(model = ingredient.image, contentDescription = "Ingredient Image", modifier = Modifier
            .width(86.dp)
            .clip(
                CircleShape
            ), contentScale = ContentScale.Crop)

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = ingredient.name, style = MaterialTheme.typography.displaySmall)


    }

}

@Composable
fun FullRecipeUi(expand:Boolean,recipeInformation: RecipeInformation,onSimilarRecipeClick:(recipeInformation:RecipeInformation)->Unit) {



    val expandedHeight = 456.dp // Desired height of the bottom sheet when expanded
    val collapsedHeight = 0.dp  // Height of the bottom sheet when collapsed
    var isExpanded by remember { mutableStateOf(expand) }

    var nutriCardExpandState = rememberSaveable {
        mutableStateOf(false)
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {

        val transition = updateTransition(targetState = isExpanded, label = "Bottom Sheet Transition")
        val height by animateDpAsState(
            targetValue = expandedHeight,
            label = "Height Animation"
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .background(MaterialTheme.colorScheme.surface)
        ){

            Column {

                Row(modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Full Recipe", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.padding(start=16.dp))
                    IconButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(end = 16.dp)) {
                        Icon(painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24), contentDescription = null)
                    }
                }


//                LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 140.dp),modifier = Modifier.padding(start = 16.dp, end = 16.dp)){
//                    items(ingredients){ingredient->
//                        IngredientUi(ingredient = ingredient)
//                    }



                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {



                    Box(modifier = Modifier
//                    .height(128.dp)
                        .padding(start = 16.dp, end = 16.dp)
                        .fillMaxWidth()){
                        Column {
                            Text(text = "Instructions", style = MaterialTheme.typography.headlineMedium)

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(text = recipeInformation.instructions, style = MaterialTheme.typography.bodyLarge)




                        }
                    }



                    Spacer(modifier = Modifier.height(24.dp))




                    Box(modifier = Modifier
                        .height(154.dp)
                        .padding(start = 16.dp, end = 16.dp)
                        .fillMaxWidth()){
                        Column {

                            Text(text = "Equipments", style = MaterialTheme.typography.headlineMedium)

                            Spacer(modifier = Modifier.height(8.dp))

                            LazyRow{
                                items(recipeInformation.equipment){equipment->

                                    EquipmentUi(equipment = equipment)

                                    Spacer(modifier = Modifier.width(16.dp))

                                }
                            }


                        }
                    }


                    Spacer(modifier = Modifier.height(24.dp))



                    Box(modifier = Modifier
//                    .height(128.dp)
                        .padding(start = 16.dp, end = 16.dp)
                        .fillMaxWidth()){
                        Column {
                            Text(text = "Quick Summary", style = MaterialTheme.typography.headlineMedium)

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(text = recipeInformation.summary, style = MaterialTheme.typography.bodyLarge)




                        }
                    }


                    Spacer(modifier = Modifier.height(24.dp))



                    Card(modifier = Modifier
                        .fillMaxWidth()
//                    .height(160.dp)
                        .animateContentSize()) {


                        Box(modifier = Modifier
//                        .height(128.dp)
                            .padding(16.dp)
                            .fillMaxWidth()
                        ){
                            Column {

                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    , horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = "Nutrition", style = MaterialTheme.typography.headlineMedium)
                                    IconButton(onClick = { nutriCardExpandState.value = !nutriCardExpandState.value }) {
                                        if(nutriCardExpandState.value){
                                            Icon(painter = painterResource(id = R.drawable.baseline_arrow_drop_up_24), contentDescription = "Collapse Nutrition")

                                        }else{
                                            Icon(painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24), contentDescription = "Expand Nutrition")
                                        }
                                    }

                                }



                                if(nutriCardExpandState.value){
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(text = recipeInformation.summary, style = MaterialTheme.typography.bodyLarge)
                                }




                            }
                        }



                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)) {
                        Box(modifier = Modifier
                            .padding(16.dp)
                            .height(52.dp)
                            .fillMaxWidth() ){
                            Text(
                                text = "Bad for health nutrition",
                                modifier = Modifier
                                    .align(Alignment.CenterStart),
                                style = MaterialTheme.typography.headlineMedium
                            )

                            IconButton(onClick = { /*TODO*/ }, modifier = Modifier
                                .align(Alignment.TopEnd)
                            ) {
                                Icon(painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24), contentDescription = null)
                            }
                        }

                    }




                    Spacer(modifier = Modifier.height(4.dp))

                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)) {
                        Box(modifier = Modifier
                            .padding(16.dp)
                            .height(52.dp)
                            .fillMaxWidth() ){
                            Text(
                                text = "Good for health nutrition",
                                modifier = Modifier
                                    .align(Alignment.CenterStart),
                                style = MaterialTheme.typography.headlineMedium
                            )

                            IconButton(onClick = { /*TODO*/ }, modifier = Modifier
                                .align(Alignment.TopEnd)
                            ) {
                                Icon(painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24), contentDescription = null)
                            }
                        }

                    }









                }




                }

            }
            Button(
                onClick = {
                    onSimilarRecipeClick(recipeInformation)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomCenter)
                    ,colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE54900)),
            ) {


                Text(text = "Get similar recipe", style = MaterialTheme.typography.displayLarge, color = Color.White)

                Spacer(modifier = Modifier.width(8.dp))

                Icon(painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24), contentDescription = "Get similar recipe")

            }

        }
}


@Composable
fun SimilarRecipeUi(expand:Boolean,similarRecipes: List<SimilarRecipesItem>,onArrowClick:()->Unit) {

    val expandedHeight = 400.dp // Desired height of the bottom sheet when expanded
    val collapsedHeight = 0.dp  // Height of the bottom sheet when collapsed
    var isExpanded by remember { mutableStateOf(expand) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {

        val transition = updateTransition(targetState = isExpanded, label = "Bottom Sheet Transition")
        val height by animateDpAsState(
            targetValue = expandedHeight,
            label = "Height Animation"
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .background(MaterialTheme.colorScheme.surface)
        ){

            Column {

                Row(modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    , verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Similar recipes", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.padding(start=16.dp))
                    IconButton(onClick = {
                                         onArrowClick()
                    }, modifier = Modifier.padding(end = 16.dp)) {
                        Icon(painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24), contentDescription = null)
                    }
                }


                LazyColumn(modifier = Modifier.padding(start = 16.dp, end = 16.dp)){
                    items(similarRecipes){similarRecipe->
                        SimilarRecipeItemUi(recipe = similarRecipe)
                        Spacer(modifier = Modifier.height(11.dp))
                    }
                }













            }

        }
    }





}

@Composable
fun SimilarRecipeItemUi(recipe: SimilarRecipesItem) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .background(color = Color.Transparent)
        .border(border = BorderStroke(1.dp, Color(0xFFE7F0F8)))
        .clip(RoundedCornerShape(12.dp))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            //Api does not send any image here
            //Setting a default image
            AsyncImage(
                model = "https://img.spoonacular.com/recipes/716426-312x231.jpg", contentDescription = recipe.title,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(text = recipe.title, modifier = Modifier
                    .padding(start = 4.dp)
                    .width(157.dp)
                    .height(20.dp),
                    style = MaterialTheme.typography.labelSmall)

                Text(text = "Ready in "+recipe.readyInMinutes+" min", modifier = Modifier
                    .padding(start = 4.dp, top = 4.dp)
                    .width(91.dp)
                    .height(14.dp),
                    style = MaterialTheme.typography.bodyMedium)
            }
        }
    }

}