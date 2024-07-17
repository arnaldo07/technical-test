package com.enmanuelbergling.technicaltest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.enmanuelbergling.technicaltest.ui.AppState
import com.enmanuelbergling.technicaltest.ui.contact.navigation.ContactGraph
import com.enmanuelbergling.technicaltest.ui.contact.navigation.contactGraph
import com.enmanuelbergling.technicaltest.ui.contact.navigation.navigateToDetails
import com.enmanuelbergling.technicaltest.ui.maps.navigation.mapScreen
import com.enmanuelbergling.technicaltest.ui.maps.navigation.navigateToMap

@Composable
fun AppNavHost(appState: AppState) {

    val navController = appState.navController

    NavHost(navController = navController, startDestination = ContactGraph) {

        contactGraph(
            onDetails = navController::navigateToDetails,
            onBack = navController::navigateUp,
            onLocate = navController::navigateToMap
        )

        mapScreen(onBack = navController::navigateUp)
    }
}