package com.enmanuelbergling.technicaltest.ui.feature.contact.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.enmanuelbergling.technicaltest.domain.entity.Contact
import com.enmanuelbergling.technicaltest.domain.entity.Coordinates
import com.enmanuelbergling.technicaltest.ui.feature.contact.detail.ContactRoute
import com.enmanuelbergling.technicaltest.ui.feature.contact.home.HomeRoute
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

fun NavHostController.navigateToDetails(contact: Contact, navOptions: NavOptions? = null) {
    navigate(contact.toDestination(), navOptions)
}

@Serializable
data object ContactGraph

@Serializable
data object HomeDestination

@Serializable
data class ContactDestination(
    val gender: String,
    val name: String,
    val lastname: String,
    val email: String,
    val dayOfBirth: String,
    val age: Int,
    val phone: String,
    val cell: String,
    val thumbPicture: String,
    val nat: String,
    val location: LocationNavArgument,
)

fun NavGraphBuilder.contactGraph(
    onDetails: (Contact) -> Unit,
    onBack: () -> Unit,
    onLocate: (Coordinates) -> Unit,
) {
    navigation<ContactGraph>(HomeDestination) {

        composable<HomeDestination> {
            HomeRoute(onDetails)
        }

        composable<ContactDestination>(
            typeMap = mapOf(typeOf<LocationNavArgument>() to LocationArgumentType)
        ) {
            ContactRoute(onBack, onLocate)
        }
    }
}