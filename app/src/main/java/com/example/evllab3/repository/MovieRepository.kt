package com.example.evllab3.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.evllab3.database.daos.MovieDao
import com.example.evllab3.database.models.Movie
import com.example.evllab3.database.models.MoviePreview
import com.example.evllab3.networkutils.Omdb


class MovieRepository(private val movieDao: MovieDao, private val api: Omdb) : BaseRepository() {

    suspend fun getMoviesByName(name: String) : MutableList<MoviePreview>? {
        val moviesResponse = safeApiCall(
                call = { api.getMoviesByName(name).await()},
                errorMessage = "Error Fetching Movies by Name"
        )
        return moviesResponse?.Search?.toMutableList()
    }

    suspend fun getMovieByTitle(name: String) : Movie? {
        val movieResponse = safeApiCall(
            call = { api.getMovieByTitle(name).await()},
            errorMessage = "Error Fetching Movie by Title"
        )
        return movieResponse
    }

    @WorkerThread
    suspend fun insert(movie: Movie) = movieDao.insert(movie)

    fun getAllfromRoomDB():LiveData<List<Movie>> = movieDao.getAll()

    fun getMovieByName(name: String) = movieDao.getByTitle(name)
}