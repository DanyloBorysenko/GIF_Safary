package com.example.gif_safary.data

import com.example.gif_safary.model.Gif
import com.example.gif_safary.model.ServerResponse
import com.example.gif_safary.network.GifApiService
import kotlinx.serialization.json.Json

interface GifsRepository {
    suspend fun getGifs() : List<Gif>
}
class NetworkGifsRepository(private val gifApiService: GifApiService) : GifsRepository {
    override suspend fun getGifs(): List<Gif> {
        val jsonConfig = Json {
            ignoreUnknownKeys = true
        }
        val serverResponse =
            jsonConfig.decodeFromString<ServerResponse>(gifApiService.getAllGifs())
        return serverResponse.data
    }
}