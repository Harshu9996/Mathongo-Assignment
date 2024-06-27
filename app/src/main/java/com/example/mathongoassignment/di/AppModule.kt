package com.example.mathongoassignment.di

import androidx.room.Room
import com.example.mathongoassignment.data.RepositoryImpl
import com.example.mathongoassignment.data.room_db.RoomDAO
import com.example.mathongoassignment.data.room_db.RoomDB
import com.example.mathongoassignment.domain.Repository
import com.example.mathongoassignment.presentation.favourite.FavouriteViewModel
import com.example.mathongoassignment.presentation.home.HomeViewModel
import com.example.mathongoassignment.presentation.recipe_details.RecipeDetailsViewModel
import com.example.mathongoassignment.presentation.search.SearchViewModel
import com.example.networking.remote.ApiDAO
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {


    single<RoomDB>{
        Room.databaseBuilder(
            get(),
            RoomDB::class.java, "mathongo-database"
        ).fallbackToDestructiveMigration().build()
    }
    single<RoomDAO>{
        val db = get<RoomDB>()
        db.getDao()
    }

    single<Repository>{
        RepositoryImpl(get<ApiDAO>(),get<RoomDAO>())
    }


    viewModel {
        HomeViewModel(repository = get<Repository>())
    }

    viewModel {
        RecipeDetailsViewModel(repository = get<Repository>())
    }

    viewModel {
        FavouriteViewModel(repository = get<Repository>())
    }

    viewModel {
        SearchViewModel(repository = get<Repository>())
    }
}