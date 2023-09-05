package com.example.gif_safary.ui.screens

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.gif_safary.model.Gif

@Composable
fun DisplayScreen(uiState: GifSafaryUiState) {
    when (uiState) {
        is GifSafaryUiState.Success -> uiState.gif?.let { ShowGifs(it) }
        is GifSafaryUiState.Loading -> LoadingScreen()
        is GifSafaryUiState.Error -> ErroorScreen()
    }

}

/**
 * You can change data = gif.images.fixedHeight.url on gif.images.origin.url for better quality,
 * but it will increase the upload time of the gif
 */
@Composable
fun ShowGifs(gif: Gif) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context).components {
        if (Build.VERSION.SDK_INT >= 28) {
            add(ImageDecoderDecoder.Factory())
        } else {
            add(GifDecoder.Factory())
        }
    }.crossfade(true).build()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(context).data(data = gif.images.fixedHeight.url).apply(block = {
                    size(Size.ORIGINAL)
                }).crossfade(true).build(), imageLoader = imageLoader
            ),
            contentScale = ContentScale.Crop,
            contentDescription = gif.title,
            modifier = Modifier.fillMaxSize()
        )
    }
}