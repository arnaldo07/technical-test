package com.enmanuelbergling.technicaltest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.enmanuelbergling.technicaltest.ui.navigation.AppNavHost
import com.enmanuelbergling.technicaltest.ui.rememberAppState
import com.enmanuelbergling.technicaltest.ui.theme.TechnicalTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TechnicalTestTheme {
                AppNavHost(
                    appState = rememberAppState(windowSizeClass = calculateWindowSizeClass(activity = this))
                )
            }
        }
    }
}