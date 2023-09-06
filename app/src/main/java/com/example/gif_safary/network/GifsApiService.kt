package com.example.gif_safary.network

import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 * KEYWORD is used for default loading gifs on the start screen. Can be changed on any word
 */

private const val KEY = "6YLqI0TfL00373q7yObRII1QkoPLMkNk"
private const val KEYWORD = "transparent"
private const val LIMIT = 25
private const val OFFSET = 0
private const val RATING = ""
private const val LANG = "en"

interface GifApiService {
    @GET("search")
    suspend fun getAllGifs(
        @Query("api_key") apiKey : String = KEY,
        @Query("q") keyword : String = KEYWORD,
        @Query("limit") limit : Int = LIMIT,
        @Query("offset") offset : Int = OFFSET,
        @Query("rating") rating : String = RATING,
        @Query("lang") lang : String = LANG,
    ) : String
}
