package com.example.gif_safary.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * class Original is not currently used but may be used in the future for better quality of gif
 */
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
    @SerialName(value = "fixed_height") val fixedHeight : FixedHeight
)
@Serializable
class Original (
    val url: String
)
@Serializable
class FixedHeight (
    val url: String
)