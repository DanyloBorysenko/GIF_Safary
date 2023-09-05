package com.example.gif_safary

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gif_safary.ui.screens.GifSafaryViewModel
import com.example.gif_safary.ui.screens.DisplayScreen
import com.example.gif_safary.ui.screens.GifSafaryUiState
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
    val viewModel : GifSafaryViewModel = viewModel(factory = GifSafaryViewModel.Factory)
    Scaffold(
        topBar = { SafaryAppBar(
            appName = R.string.app_name,
            textFieldValue = viewModel.keyword,
            onValChange = {viewModel.updateKeyword(it)},
            scrollBehavior = scrollBehavior
        )},
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->

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
    textFieldValue : String,
    onValChange: (String) -> Unit,
    scrollBehavior : TopAppBarScrollBehavior,
    modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar (
        title = {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = appName),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(start = 30.dp))
                Spacer(modifier = Modifier.width(60.dp))

                EditWordField(
                    value = textFieldValue,
                    onValChange = onValChange,
                    keyboardOptions = KeyboardOptions.Default
                )
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background),
        modifier = modifier
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditWordField(
    value: String,
    onValChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = value,
        singleLine = true,
        onValueChange = onValChange,
        keyboardOptions = keyboardOptions,
        leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null)},
        shape = ShapeDefaults.Medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GIF_SafaryTheme {
        SafaryAppBar(appName = R.string.app_name, "", {},scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior())
    }
}