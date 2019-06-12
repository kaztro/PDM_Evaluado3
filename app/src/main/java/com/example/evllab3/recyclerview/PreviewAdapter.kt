package com.example.evllab3.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.evllab3.R
import com.example.evllab3.database.models.MoviePreview
import kotlinx.android.synthetic.main.cardview_preview.view.*

class PreviewAdapter(private var movies: List<MoviePreview>, private val clickListener: (MoviePreview, View) -> Unit) : RecyclerView.Adapter<PreviewAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_preview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(movies[position] , clickListener)

    fun changeDataSet(newMovieList: List<MoviePreview>) {
        movies = newMovieList
        notifyDataSetChanged()
    }

    class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        fun bind(movie: MoviePreview, clickListener: (MoviePreview, View) -> Unit) = with(itemView){
            Glide.with(itemView.context)
                .load(movie.Poster)
                .placeholder(R.drawable.ic_launcher_background)
                .into(cv_preview_image)
            cv_preview_title.text = movie.Title
            cv_preview_year.text = movie.Year
            if (movie.Selected) cv_preview_selected.visibility = View.VISIBLE else cv_preview_selected.visibility = View.GONE
            this.setOnClickListener { clickListener(movie, cv_preview_selected) }
        }
    }
}