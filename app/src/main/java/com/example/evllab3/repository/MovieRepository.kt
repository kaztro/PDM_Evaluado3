package com.example.evllab3.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.evllab3.dabase.daos.MovieDao
import com.example.evllab3.dabase.models.Movie
import com.example.evllab3.dabase.models.MoviePreview
import com.example.evllab3.dabase.models.OmbdMovieResponse
import com.example.evllab3.networkutils.Omdb
import kotlinx.coroutines.Deferred
import retrofit2.Response


class MovieRepository(private val movieDao: MovieDao, private val api: Omdb){

    fun retrieveMoviesByNameAsync(name:String): Deferred<Response<OmbdMovieResponse>> = api.getMoviesByName(name)

    fun retrieveMoviesByTitleAsync(name:String): Deferred<Response<Movie>> = api.getMovieByTitle(name)

    @WorkerThread
    suspend fun insert(movie: Movie) = movieDao.insert(movie)

    fun getAllfromRoomDB():LiveData<List<Movie>> = movieDao.getAll()

    fun getMovieByName(name: String) = movieDao.getByTitle(name)
}