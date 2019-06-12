package com.example.evllab3.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.evllab3.R
import com.example.evllab3.dabase.models.Movie

class MovieAdapter(var movies: List<Movie>, val clickListener: (Movie) -> Unit) : RecyclerView.Adapter<MovieAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(movies[position], clickListener)

    fun changeDataSet(newMovieList: List<Movie>) {
        movies = newMovieList
        notifyDataSetChanged()
    }

    class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        fun bind(movie: Movie, clickListener: (Movie) -> Unit) = with(itemView){
            Glide.with(itemView.context)
                .load(movie.Poster)
                .placeholder(R.drawable.ic_launcher_background)
                .into(movie_image_cv)
            movie_title_cv.text = movie.Title
            movie_plot_cv.text = movie.Plot
            movie_rate_cv.text = movie.omdbRating
            movie_runtime_cv.text = movie.Runtime
            this.setOnClickListener { clickListener(movie) }
        }
    }
}