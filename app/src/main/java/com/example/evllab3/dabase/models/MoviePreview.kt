package com.example.evllab3.dabase.models

import androidx.room.Entity

@Entity
data class MoviePreview (

    val Title: String = "N/A",

    val Year: String = "N/A",

    val imdbID: String = "N/A",

    val Type: String = "N/A",

    val Poster: String = "N/A",

    var Selected: Boolean = false

)