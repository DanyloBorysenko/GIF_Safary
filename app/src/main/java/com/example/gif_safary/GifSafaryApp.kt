package com.example.gif_safary

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gif_safary.model.Gif
import com.example.gif_safary.ui.screens.GifSafaryViewModel
import com.example.gif_safary.ui.screens.DisplayScreen
import com.example.gif_safary.ui.screens.StartScreen
import com.example.gif_safary.ui.theme.GIF_SafaryTheme

enum class GifSafary {
    START,
    DISPLAY
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GifSafaryApp(
    navController: NavHostController = rememberNavController()) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = { SafaryAppBar(
            appName = R.string.app_name,
            scrollBehavior = scrollBehavior
        )},
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        val viewModel : GifSafaryViewModel = viewModel(factory = GifSafaryViewModel.Factory)
        NavHost(
            navController = navController,
            startDestination = GifSafary.START.name,
            modifier = Modifier.padding(paddingValues)) {
            composable(route = GifSafary.START.name) {
                StartScreen(
                    paddingValues = paddingValues, viewModel.uiState,
                    onClick = {
                        navController.navigate(route = GifSafary.DISPLAY.name)
                    },
                    chooseSelectedGif = {viewModel.setSelectedGif(it) }
                )
            }
            composable(route = GifSafary.DISPLAY.name) {
                DisplayScreen(viewModel.uiState)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SafaryAppBar(
    @StringRes appName: Int,
    scrollBehavior : TopAppBarScrollBehavior,
    modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar (
        title = {
            Row {
                Text(
                    text = stringResource(id = appName),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.secondary)
            }},
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GIF_SafaryTheme {
        GifSafaryApp()
    }
}