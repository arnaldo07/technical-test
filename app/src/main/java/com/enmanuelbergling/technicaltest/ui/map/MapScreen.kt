package com.enmanuelbergling.technicaltest.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.enmanuelbergling.technicaltest.ui.components.AddEditFavoriteDialog
import com.enmanuelbergling.technicaltest.ui.components.LocationInfoPanel
import com.enmanuelbergling.technicaltest.viewmodel.MapViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("MissingPermission")
@Composable
fun MapScreen(viewModel: MapViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState()

    var showAddDialog by remember { mutableStateOf(false) }
    var newFavoriteTitle by remember { mutableStateOf("") }
    var isTitleError by remember { mutableStateOf(false) }
    var mapControlsExpanded by remember { mutableStateOf(false) }

    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    LaunchedEffect(Unit) {
        locationPermissionState.launchPermissionRequest()
    }

    LaunchedEffect(locationPermissionState.status) {
        if (locationPermissionState.status.isGranted) {

            val currentLocation = requestCurrentLocation(context)
            currentLocation?.let { latLng ->
                viewModel.setLastKnownLocation(latLng)
                cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                mapType = uiState.mapType,
                isMyLocationEnabled = locationPermissionState.status.isGranted
            ),
            uiSettings = MapUiSettings(zoomControlsEnabled = false, myLocationButtonEnabled = locationPermissionState.status.isGranted),
            onMapClick = { viewModel.onMapClick(it) }
        ) {
            uiState.selectedLocation?.let {
                Marker(
                    state = MarkerState(position = it),
                    title = "Ubicación seleccionada",
                    snippet = uiState.selectedAddress
                )
            }
            uiState.favorites.forEach { favorite ->
                Marker(
                    state = MarkerState(position = LatLng(favorite.latitude, favorite.longitude)),
                    title = favorite.title,
                    snippet = favorite.address,
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE),
                    onClick = {
                        viewModel.onMapClick(LatLng(favorite.latitude, favorite.longitude))
                        true
                    }
                )
            }
        }

        FloatingActionButton(
            onClick = { mapControlsExpanded = true },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Cambiar tipo de mapa")
            DropdownMenu(
                expanded = mapControlsExpanded,
                onDismissRequest = { mapControlsExpanded = false }) {
                DropdownMenuItem(
                    text = { Text("Normal") },
                    onClick = { viewModel.setMapType(MapType.NORMAL); mapControlsExpanded = false })
                DropdownMenuItem(
                    text = { Text("Satélite") },
                    onClick = {
                        viewModel.setMapType(MapType.SATELLITE); mapControlsExpanded = false
                    })
                DropdownMenuItem(
                    text = { Text("Híbrido") },
                    onClick = { viewModel.setMapType(MapType.HYBRID); mapControlsExpanded = false })
                DropdownMenuItem(
                    text = { Text("Terreno") },
                    onClick = {
                        viewModel.setMapType(MapType.TERRAIN); mapControlsExpanded = false
                    })
            }
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            LocationInfoPanel(uiState = uiState, onAddFavoriteClicked = { showAddDialog = true })
        }

        if (showAddDialog) {
            AddEditFavoriteDialog(
                title = newFavoriteTitle,
                isError = isTitleError,
                dialogTitle = "Añadir Favorito",
                onTitleChange = { title ->
                    newFavoriteTitle = title
                    if (isTitleError) isTitleError = false
                },
                onConfirm = {
                    if (newFavoriteTitle.isNotBlank()) {
                        viewModel.addFavorite(title =  newFavoriteTitle)
                        showAddDialog = false
                        newFavoriteTitle = ""
                    } else {
                        isTitleError = true
                    }
                },
                onDismiss = {
                    showAddDialog = false
                    newFavoriteTitle = ""
                }
            )
        }
    }
}

@SuppressLint("MissingPermission")
private suspend fun requestCurrentLocation(context: Context): LatLng? {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)


    return suspendCancellableCoroutine { continuation ->
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000L)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(5000L)
            .setMaxUpdateDelayMillis(100L)
            .build()


        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let { location ->
                    val latLng = LatLng(location.latitude, location.longitude)
                    // Si la corrutina sigue activa, le pasamos el resultado
                    if (continuation.isActive) {
                        continuation.resume(latLng)
                    }

                    fusedLocationClient.removeLocationUpdates(this)
                }
            }
        }

        continuation.invokeOnCancellation {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }
}


