package com.enmanuelbergling.technicaltest.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun rememberAppState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController,
) = remember(windowSizeClass) {
    AppState(windowSizeClass, navController)
}

@Stable
data class AppState(
    val windowSizeClass: WindowSizeClass,
    val navController: NavHostController,
)