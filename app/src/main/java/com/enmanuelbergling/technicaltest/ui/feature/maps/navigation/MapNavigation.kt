package com.enmanuelbergling.technicaltest.ui.feature.maps.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.enmanuelbergling.technicaltest.domain.entity.Coordinates
import com.enmanuelbergling.technicaltest.ui.feature.maps.MapRoute
import kotlinx.serialization.Serializable

fun NavHostController.navigateToMap(
    coordinates: Coordinates,
    navOptions: NavOptions? = null,
) {
    navigate(
        route = MapDestination(
            latitude = coordinates.latitude,
            longitude = coordinates.longitude
        ),
        navOptions = navOptions
    )
}

@Serializable
data class MapDestination(
    val latitude: String,
    val longitude: String,
)

fun NavGraphBuilder.mapScreen(
    onBack: () -> Unit,
) {
    composable<MapDestination> {
        MapRoute(onBack)
    }
}