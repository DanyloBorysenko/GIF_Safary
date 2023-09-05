package com.example.gif_safary.data

import com.example.gif_safary.network.GifApiService
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

interface AppContainer {
    val gifsRepository : GifsRepository
}
class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://api.giphy.com/v1/gifs/"


    private val retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    private val retrofitService : GifApiService by lazy {
        retrofit.create(GifApiService::class.java)
    }
    override val gifsRepository: GifsRepository by lazy {
        NetworkGifsRepository(retrofitService)
    }
}