package com.example.evllab3.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.evllab3.R
import com.example.evllab3.database.models.Movie
import kotlinx.android.synthetic.main.cardview_movie.view.*

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
                .into(cv_movie_image)
            cv_movie_title.text = movie.Title
            cv_movie_plot.text = movie.Plot
            cv_movie_rate.text = movie.omdbRating
            cv_movie_runtime.text = movie.Runtime
            this.setOnClickListener { clickListener(movie) }
        }
    }
}