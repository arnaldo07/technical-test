package com.enmanuelbergling.technicaltest.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Map : Screen("map", "Mapa", Icons.AutoMirrored.Default.List)
    object Favorites : Screen("favorites", "Favoritos", Icons.Default.Favorite)
}

val navItems = listOf(Screen.Map, Screen.Favorites)