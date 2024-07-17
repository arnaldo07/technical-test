package com.enmanuelbergling.technicaltest.ui.contact.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.enmanuelbergling.technicaltest.domain.entity.Contact
import com.enmanuelbergling.technicaltest.domain.entity.Coordinates
import com.enmanuelbergling.technicaltest.ui.contact.detail.ContactRoute
import com.enmanuelbergling.technicaltest.ui.contact.home.HomeRoute
import kotlinx.serialization.Serializable

fun NavHostController.navigateToDetails(contact: Contact, navOptions: NavOptions? = null) {
    navigate(contact.toDestination(), navOptions)
}

@Serializable
data object ContactGraph

@Serializable
data object HomeDestination

@Serializable
data class LocationArgument(
    val street: String,
    val city: String,
    val state: String,
    val country: String,
    val postcode: Int,
    val latitude: String,
    val longitude: String,
)

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
    val location: LocationArgument,
)


fun NavGraphBuilder.contactGraph(
    onDetails: (Contact) -> Unit,
    onBack: () -> Unit,
    onLocate: (Coordinates)->Unit,
) {
    navigation<ContactGraph>(HomeDestination) {

        composable<HomeDestination> {
            HomeRoute(onDetails)
        }

        composable<ContactDestination> {
            ContactRoute(onBack, onLocate)
        }
    }
}