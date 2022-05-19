package com.romainbechard.instantsystemtestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.romainbechard.instantsystemtestapp.ui.screens.home.HomeScreen
import com.romainbechard.instantsystemtestapp.ui.screens.home.HomeViewModel
import com.romainbechard.instantsystemtestapp.ui.theme.InstantSystemTestAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application = applicationContext as InstantSystemTestApplication
        val homeViewModel = HomeViewModel(application.repository)
        setContent {
            InstantSystemTestAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeScreen(homeViewModel = homeViewModel)
                }
            }
        }
    }
}