package com.example.networking.model

data class StepX(
    val equipment: List<EquipmentX>,
    val ingredients: List<IngredientX>,
    val length: LengthX,
    val number: Int,
    val step: String
)