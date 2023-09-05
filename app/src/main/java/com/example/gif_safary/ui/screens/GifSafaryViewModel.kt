package com.example.gif_safary.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.gif_safary.GifSafaryApplication
import com.example.gif_safary.data.GifsRepository
import com.example.gif_safary.model.Gif
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface GifSafaryUiState {
    data class Success(val gifsList: List<Gif>, val gif: Gif?) : GifSafaryUiState
    object Loading : GifSafaryUiState
    object Error : GifSafaryUiState
}

class GifSafaryViewModel(private val gifsRepository: GifsRepository) : ViewModel() {
    var uiState : GifSafaryUiState by mutableStateOf(GifSafaryUiState.Loading)

    init {
        findGifs()
    }

    fun findGifs() {
        viewModelScope.launch {
            uiState = try {
                GifSafaryUiState.Success(
                    gifsList = gifsRepository.getGifs(),
                    gif = null
                )
            } catch (e: IOException) {
                GifSafaryUiState.Error
            }
        }
    }
    companion object {
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as GifSafaryApplication)
                val gifsRepository = application.container.gifsRepository
                GifSafaryViewModel(gifsRepository = gifsRepository)
            }
        }
    }

    fun setSelectedGif(gif: Gif) {
        uiState = (uiState as GifSafaryUiState.Success).copy(gif = gif)
    }
}