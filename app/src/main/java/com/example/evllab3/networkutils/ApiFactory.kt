package com.example.evllab3.networkutils

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiFactory {

    val API_TOKEN = "9ccb1314"
    private val authInterceptor = Interceptor { chain ->
        val newUrl = chain.request().url()
            .newBuilder()
            .addQueryParameter("apikey", API_TOKEN)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    private val ombdClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(authInterceptor)
        .build()

    fun retrofit(): Retrofit = Retrofit.Builder()
        .client(ombdClient)
        .baseUrl("http://www.omdbapi.com")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val ombdApi: Omdb = retrofit().create(Omdb::class.java)

}