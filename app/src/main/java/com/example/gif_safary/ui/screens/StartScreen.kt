package com.example.gif_safary.ui.screens


import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.gif_safary.R
import com.example.gif_safary.model.Gif

@Composable
fun StartScreen(
    paddingValues: PaddingValues,
    uiState: GifSafaryUiState,
    onClick: () -> Unit,
    chooseSelectedGif: (Gif) -> Unit,
    modifier: Modifier = Modifier){
    when (uiState) {
        is GifSafaryUiState.Success -> GifsGrid(
            paddingValues = paddingValues,
            gifs = uiState.gifsList,
            onClick = onClick,
            chooseSelectedGif = chooseSelectedGif
        )
        is GifSafaryUiState.Loading -> LoadingScreen()
        is GifSafaryUiState.Error -> ErroorScreen()
    }
}

@Composable
fun GifsGrid(
    paddingValues: PaddingValues,
    gifs: List<Gif>,
    onClick: () -> Unit,
    chooseSelectedGif: (Gif) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(3), contentPadding = paddingValues) {
        items(gifs) {
            GifCard(
                gif = it,
                onClick = onClick,
                modifier = Modifier.padding(5.dp),
                chooseSelectedGif = chooseSelectedGif
            )
        }
    }
}

@Composable
fun GifCard(
    gif: Gif,
    onClick: () -> Unit,
    chooseSelectedGif: (Gif) -> Unit,
    modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context).data(data = gif.images.fixedHeight.url).apply(block = {
                size(coil.size.Size.ORIGINAL)
            }).crossfade(true).build(), imageLoader = imageLoader
        ),
        contentScale = ContentScale.Crop,
        contentDescription = gif.title,
        modifier = modifier.fillMaxWidth().clickable {
            onClick()
            chooseSelectedGif(gif)
        }
    )
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.loading_img),
        contentDescription = stringResource(
            id = R.string.loading_screen_cont_descr),
        modifier = modifier.fillMaxSize())
}
@Composable
fun ErroorScreen(modifier: Modifier = Modifier) {
    Column (
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = stringResource(
                id = R.string.network_connection_error
        ))
        Text(text = stringResource(id = R.string.network_connection_error))
    }
}

