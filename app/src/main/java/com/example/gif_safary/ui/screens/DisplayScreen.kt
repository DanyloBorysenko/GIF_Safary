package com.example.gif_safary.ui.screens

import android.os.Build
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.gif_safary.R
import com.example.gif_safary.model.Gif

@Composable
fun DisplayScreen(uiState: GifSafaryUiState) {
    when (uiState) {
        is GifSafaryUiState.Success -> uiState.gif?.let { ShowGifs(it) }
        is GifSafaryUiState.Loading -> LoadingScreen()
        is GifSafaryUiState.Error -> ErroorScreen()
    }

}
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
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(context).data(data = gif.images.original.url).apply(block = {
                    size(Size.ORIGINAL)
                }).crossfade(true).build(), imageLoader = imageLoader
            ),
            contentDescription = gif.title,
            modifier = Modifier.fillMaxWidth()
        )
    }
}