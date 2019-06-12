package com.example.evllab3.networkutils

import android.util.Log
import com.example.evllab3.database.models.Movie
import com.example.evllab3.database.models.MoviePreview

object AppConstants {

    val ADD_TASK_REQUEST = 1
    val emptyMoviesPreview = ArrayList<MoviePreview>()
    val emptyMovies = ArrayList<Movie>()

    fun debugPreviewMovies(movieList: List<Movie>){
        Log.d("TAG", "__________________________________________________")
        Log.d("TAG", "NEW MOVIES________________________________________")
        for (res in movieList) Log.d("TAG", "${res.Title} -> id = ${res.omdbID}")
        Log.d("TAG", "__________________________________________________")
    }
    fun debugPreviewMoviesPreview(movieList: List<MoviePreview>){
        Log.d("TAG", "__________________________________________________")
        Log.d("TAG", "PREVIEW___________________________________________")
        for (res in movieList) Log.d("TAG", "${res.Title} -> selected = ${res.Selected}")
        Log.d("TAG", "__________________________________________________")
    }
}