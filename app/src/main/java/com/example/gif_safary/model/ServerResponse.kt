package com.example.gif_safary.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ServerResponse(
    val data: List<Gif>)

@Serializable
data class Gif(
    val id: String,
    val title: String,
    val images: GifImages
)

@Serializable
class GifImages (
    val original: Original,
    @SerialName(value = "preview_gif") val previewGif : PreviewGif
)

@Serializable
class PreviewGif(val url: String)

@Serializable
class Original (
    val url: String
)
