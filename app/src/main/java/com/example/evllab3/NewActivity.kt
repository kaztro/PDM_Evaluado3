package com.example.evllab3

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.evllab3.ViewModel.MovieViewModel
import com.example.evllab3.database.models.MoviePreview
import com.example.evllab3.networkutils.AppConstants
import com.example.evllab3.recyclerview.PreviewAdapter
import kotlinx.android.synthetic.main.preview_add_movie.*

class NewActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.preview_add_movie)

        val MovieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        val layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        val recyclerView = rv_preview
        val moviesPreviewAdapter = PreviewAdapter(movies = AppConstants.emptyMoviesPreview,
            clickListener = { movie: MoviePreview, checky: View ->
                movie.Selected = !movie.Selected
                Toast.makeText(this, if (movie.Selected) "Selected ${movie.Title}" else "Unselected ${movie.Title}", Toast.LENGTH_SHORT).show()
                //Snackbar.make(checky.rootView, if (movie.selected) "Selected ${movie.Title}" else "Unselected ${movie.Title}", Snackbar.LENGTH_SHORT)
                if (movie.Selected) checky.visibility = View.VISIBLE else checky.visibility = View.GONE
            })
        recyclerView.apply {
            adapter = moviesPreviewAdapter
            setHasFixedSize(true)
            this.layoutManager = layoutManager
        }
        MovieViewModel.getMovieListVM().observe(this, Observer { result ->
            moviesPreviewAdapter.changeDataSet(result)
        })
        btn_search.setOnClickListener {
            val movieNameQuery = et_search.text.toString()
            if (movieNameQuery.isNotEmpty() && movieNameQuery.isNotBlank()) {
                MovieViewModel.fetchMovie(movieNameQuery)
                MovieViewModel.getMovieListVM().observe(this, Observer { result ->
                    moviesPreviewAdapter.changeDataSet(result)
                })
            }
        }

        btn_cancel.setOnClickListener {clearView(et_search, moviesPreviewAdapter)}

        btn_add_preview.setOnClickListener {
            val thenownow = MovieViewModel.getMovieListVM().value
            val selectedMovies = thenownow?.filter { it.Selected } ?: AppConstants.emptyMoviesPreview

            for (movie in selectedMovies) {
                MovieViewModel.fetchMovieByTitle(movie.Title)
                MovieViewModel.getMovieResult().observe(this, Observer {resultMovie ->
                    MovieViewModel.insert(resultMovie)
                })
            }
            clearView(et_search, moviesPreviewAdapter)

            setResult(Activity.RESULT_OK)
            finish()
        }
    }


    fun clearView(et: EditText, adapter: PreviewAdapter){
        et.text.clear()
        adapter.changeDataSet(AppConstants.emptyMoviesPreview)
    }
}