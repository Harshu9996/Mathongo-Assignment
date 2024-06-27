package com.example.mathongoassignment.data.mappers

import com.example.mathongoassignment.data.room_db.entity.RoomRecipe
import com.example.mathongoassignment.domain.Equipment
import com.example.mathongoassignment.domain.ExtendedIngredient
import com.example.mathongoassignment.domain.PopularRecipe
import com.example.mathongoassignment.domain.PopularRecipes
import com.example.mathongoassignment.domain.Recipe
import com.example.mathongoassignment.domain.RecipeInformation
import com.example.mathongoassignment.domain.Recipes
import com.example.mathongoassignment.domain.SimilarRecipes
import com.example.mathongoassignment.domain.SimilarRecipesItem
import com.example.networking.model.RandomRecipesDTO
import com.example.networking.model.RecipeInformationDTO
import com.example.networking.model.SearchRecipesDTO
import com.example.networking.model.SimilarRecipesDTOItem

fun SearchRecipesDTO.toDomain(): Recipes =
    Recipes(number = this.number,offset=this.offset,results=  this.results.map {
        Recipe(id = it.id, image = it.image, imageType = it.imageType, title = it.title)
    },totalResults=this.totalResults)

fun RandomRecipesDTO.toDomain() : PopularRecipes = PopularRecipes(recipes=this.recipes.map {
    PopularRecipe(id=it.id, image = it.image, imageType = it.imageType, readyInMinutes = it.readyInMinutes, title = it.title)
})

fun RecipeInformationDTO.toDomain() : RecipeInformation {
    val equipments = mutableListOf<Equipment>()
    this.analyzedInstructions.forEach { analyzedInstruction ->
        analyzedInstruction.steps.forEach { step ->
            step.equipment.forEach {
                equipments.add(Equipment(id = it.id, image = it.image, name = it.name, localizedName = it.localizedName))
            }
        }
    }
    return RecipeInformation(equipment = equipments,extendedIngredients=this.extendedIngredients.map {
        ExtendedIngredient(id=it.id,image=it.image, name = it.name)
    },id=this.id,
        image=this.image,imageType = this.imageType,instructions = this.instructions,pricePerServing=this.pricePerServing,readyInMinutes=this.readyInMinutes,servings=this.servings, summary = this.summary,title=this.title)
}


fun List<SimilarRecipesDTOItem>.toDomain() : List<SimilarRecipesItem> = map {
    SimilarRecipesItem(id=it.id, title = it.title, imageType = it.imageType, readyInMinutes = it.readyInMinutes, servings = it.servings, sourceUrl = it.sourceUrl)

}

fun RecipeInformation.toRoom() : RoomRecipe = RoomRecipe(id=id,  image, imageType, title)

fun RoomRecipe.toDomain(): Recipe = Recipe(id = id, image = image, imageType = imageType, title = title)
