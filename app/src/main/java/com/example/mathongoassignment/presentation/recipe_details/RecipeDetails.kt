package com.example.mathongoassignment.presentation.recipe_details

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.mathongoassignment.domain.Equipment
import com.example.mathongoassignment.domain.ExtendedIngredient

@Composable
fun RecipeDetails(viewModel: RecipeDetailsViewModel) {

    val TAG = "RecipeDetails"

    val uiState = viewModel.uiState.collectAsState().value

    val nutriCardExpandState = rememberSaveable {
        mutableStateOf(true)
    }


    Log.d(TAG, "RecipeDetails: RecipeInformation = "+uiState.recipeDetails)

    Scaffold {

        if(uiState.isLoading){
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            }
        }else if(uiState.error?.isEmpty() == true && uiState.recipeDetails!=null){

            Column(modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState())) {


                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp)){
                    AsyncImage(model = uiState.recipeDetails.image, contentDescription = "Recipe Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(360.dp), contentScale = ContentScale.Crop)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(360.dp) // Height of the gradient overlay
                            .graphicsLayer { alpha = 0.80f } // Ensure the gradient is drawn on top
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.Black),
                                    startY = 0f,
                                    endY = 950f // Same as the height of the gradient overlay
                                )
                            )
                            .align(Alignment.BottomCenter))


                    Text(text = uiState.recipeDetails.title, style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier
                            .padding(start = 24.dp, bottom = 24.dp)
                            .align(Alignment.BottomStart),
                        color = Color.White)

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
                            Text(text = uiState.recipeDetails.readyInMinutes.toString(), style = MaterialTheme.typography.displayLarge)
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
                            Text(text = uiState.recipeDetails.servings.toString(), style = MaterialTheme.typography.displayLarge)
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
                            Text(text = uiState.recipeDetails.pricePerServing.toString(), style = MaterialTheme.typography.displayLarge)
                        }

                    }



                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Box(modifier = Modifier
                    .height(140.dp)
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()){
                    Column {
                        Text(text = "Ingredients", style = MaterialTheme.typography.headlineMedium)

                        Spacer(modifier = Modifier.height(12.dp))
                        
                        LazyRow{
                            items(uiState.recipeDetails.extendedIngredients){ ingredient->
                                IngredientUi(ingredient = ingredient)

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
                       Text(text = "Instructions", style = MaterialTheme.typography.headlineMedium)
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(text = uiState.recipeDetails.instructions, style = MaterialTheme.typography.bodyLarge)




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
                            items(uiState.recipeDetails.equipment){equipment->

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

                        Text(text = uiState.recipeDetails.summary, style = MaterialTheme.typography.bodyLarge)




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
                            
                            Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()
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
                                Text(text = uiState.recipeDetails.summary, style = MaterialTheme.typography.bodyLarge)
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



}

@Composable
fun IngredientUi(ingredient:ExtendedIngredient) {

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
fun EquipmentUi(equipment:Equipment) {

    Column(modifier = Modifier
        .height(108.dp)
        .width(86.dp)) {
        AsyncImage(model = equipment.image, contentDescription = "Equipment Image", modifier = Modifier
            .width(86.dp)
            .clip(
                CircleShape
            ), contentScale = ContentScale.Crop)

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = equipment.name, style = MaterialTheme.typography.displaySmall)


    }


}