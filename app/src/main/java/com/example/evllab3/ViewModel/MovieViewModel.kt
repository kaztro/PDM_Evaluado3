package com.example.evllab3.ViewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.evllab3.dabase.MovieDataBase
import com.example.evllab3.dabase.models.Movie
import com.example.evllab3.dabase.models.MoviePreview
import com.example.evllab3.networkutils.ApiFactory
import com.example.evllab3.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(val app: Application): AndroidViewModel(app){

    private val repository: MovieRepository

    init {
        val moviewDao = MovieDataBase.getDatabase(app).dao()
        repository = MovieRepository(movieDao, ApiFactory.ombdApi)
    }

    private val scope = CoroutineScope(Dispatchers.IO)

    private val movieList = MutableLiveData<MutableList<MoviePreview>>()

    private val movieResult = MutableLiveData<Movie>()

    fun fetchMovie(name:String){
        scope.launch {
            val response = repository.retrieveMoviesByTitleAsync(name).await()
            if(response.isSuccessful) with(response){
                when(this.code()){
                    200->movieResult.postValue(this.body())
                }
            }else{
                Toast.makeText(app,"Ocurrio un error ",Toast.LENGTH_LONG).show()
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