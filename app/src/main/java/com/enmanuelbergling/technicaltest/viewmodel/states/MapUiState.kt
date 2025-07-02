package com.enmanuelbergling.technicaltest.viewmodel.states

import com.enmanuelbergling.technicaltest.data.local.Favorite
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MapType

data class MapUiState(
    val lastKnownLocation: LatLng? = null,
    val selectedLocation: LatLng? = null,
    val selectedAddress: String = "",
    val addressIsLoading: Boolean = false,
    val favorites: List<Favorite> = emptyList(),
    val mapType: MapType = MapType.NORMAL,
    val distanceToSelectedPoint: Float? = null,
    val editingFavorite: Favorite? = null
)
