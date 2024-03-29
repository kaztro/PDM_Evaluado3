package com.example.evllab3.ViewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.evllab3.database.MovieDataBase
import com.example.evllab3.database.models.Movie
import com.example.evllab3.database.models.MoviePreview
import com.example.evllab3.networkutils.ApiFactory
import com.example.evllab3.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(val app: Application): AndroidViewModel(app){
    private val repository: MovieRepository

    init {
        val movieDao = MovieDataBase.getDatabase(app).dao()
        repository = MovieRepository(movieDao, ApiFactory.ombdApi)
    }

    private val scope = CoroutineScope(Dispatchers.IO)

    private val movieslist = MutableLiveData<MutableList<MoviePreview>>()

    private val movieResult = MutableLiveData<Movie>()

    fun fetchMovie(name: String){
        scope.launch {
            val response=repository.retrieveMoviesByNameAsync(name).await()
            if(response.isSuccessful){
                when(response.code()){
                    200->movieslist.postValue(response.body()?.Search?.toMutableList()?:arrayListOf(MoviePreview(Title = "Dummy 1"), MoviePreview(Title = "Dummy 2")))
                }
            }else{
                Toast.makeText(app, "Ocurrio un error", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun getMovieListVM(): LiveData<MutableList<MoviePreview>> = movieslist

    fun fetchMovieByTitle(name: String){
        scope.launch {
            val response=repository.retrieveMoviesByTitleAsync(name).await()
            if(response.isSuccessful) with(response){
                when(this.code()){
                    200->movieResult.postValue(this.body())
                }
            }else{
                Toast.makeText(app, "Ocurrio un error", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun getMovieResult(): LiveData<Movie> = movieResult


    fun insert(movie: Movie) = scope.launch {
        repository.insert(movie)
    }

    fun getAll():LiveData<List<Movie>> = repository.getAllfromRoomDB()

    fun getMovieByName(name: String): LiveData<List<Movie>> = repository.getMovieByName(name)

}