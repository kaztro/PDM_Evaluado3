package com.example.evllab3.dabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.evllab3.dabase.daos.MovieDao
import com.example.evllab3.dabase.models.Movie

@Database(entities = [Movie::class], version = 2, exportSchema = false)
abstract class MovieDataBase : RoomDatabase() {

    abstract fun dao(): MovieDao

    companion object{
        @Volatile
        private var INSTANCE: MovieDataBase? = null

        fun getDatabase(appContext: Context): MovieDataBase {
            if (INSTANCE == null) {
                synchronized(MovieDataBase::class) {
                    INSTANCE = Room
                        .databaseBuilder(appContext.applicationContext
                            , MovieDataBase::class.java
                            ,"db_omdb")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }
    }

}