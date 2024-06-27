package com.example.networking.di

import com.example.networking.remote.ApiDAO
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    val BASE_URL = "https://api.spoonacular.com/recipes/"

    single{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiDAO::class.java)
    }
}