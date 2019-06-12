package com.example.evllab3.database.models

import androidx.room.Entity

@Entity
data class MoviePreview (

    val Title: String = "N/A",

    val Year: String = "N/A",

    val omdbID: String = "N/A",

    val Type: String = "N/A",

    val Poster: String = "N/A",

    var Selected: Boolean = false

)