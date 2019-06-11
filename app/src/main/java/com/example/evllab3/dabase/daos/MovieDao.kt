package com.example.evllab3.dabase.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.evllab3.dabase.models.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Query("SELECT * FROM Movie")
    fun getAll(): LiveData<List<Movie>>

    @Query("SELECT * FROM Movie WHERE Title LIKE :title")
    fun getByTitle(title: String): LiveData<List<Movie>>
}