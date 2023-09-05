package com.example.gif_safary

import android.app.Application
import com.example.gif_safary.data.AppContainer
import com.example.gif_safary.data.DefaultAppContainer

class GifSafaryApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}