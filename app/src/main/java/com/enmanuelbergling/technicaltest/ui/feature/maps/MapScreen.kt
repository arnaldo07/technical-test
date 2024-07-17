package com.enmanuelbergling.technicaltest.ui.feature.maps

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.enmanuelbergling.technicaltest.R
import com.enmanuelbergling.technicaltest.domain.entity.Coordinates
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapRoute(coordinates: Coordinates, onBack: () -> Unit) {

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.map)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "arrow back"
                        )
                    }
                }
            )
        }, snackbarHost = {
            SnackbarHost(snackBarHostState)
        }
    ) {
        SimpleGoogleMap(
            coordinates = coordinates,
            onMarker = {
                scope.launch {
                    snackBarHostState.showSnackbar("You have clicked me!")
                }
            }, modifier = Modifier.padding(it)
        )
    }
}

fun Coordinates.asLatLong() = LatLng(
    latitude.toDoubleOrNull() ?: .0,
    longitude.toDoubleOrNull() ?: .0,
)

@Composable
fun SimpleGoogleMap(coordinates: Coordinates, onMarker: () -> Unit, modifier: Modifier = Modifier) {

    GoogleMap(
        modifier = modifier, uiSettings = MapUiSettings(zoomControlsEnabled = false),
        cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(coordinates.asLatLong(), 20f)
        }
    ) {
        Marker(
            state = rememberMarkerState(
                position = coordinates.asLatLong()
            ), onClick = {
                onMarker()
                true
            }, title = "I live here"
        )
    }
}