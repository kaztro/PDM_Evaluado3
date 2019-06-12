package com.example.evllab3.database.models

import androidx.room.Entity

@Entity
data class OmbdMovieResponse (

    val Search: List<MoviePreview>,

    val totalResults: String,

    val Response: String
)