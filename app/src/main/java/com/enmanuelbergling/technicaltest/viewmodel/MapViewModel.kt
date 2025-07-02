package com.enmanuelbergling.technicaltest.viewmodel

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enmanuelbergling.technicaltest.data.local.Favorite
import com.enmanuelbergling.technicaltest.data.repository.FavoritesRepository
import com.enmanuelbergling.technicaltest.data.repository.GeocodingRepository
import com.enmanuelbergling.technicaltest.data.repository.PlaceDetails
import com.enmanuelbergling.technicaltest.viewmodel.states.MapUiState
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MapType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: FavoritesRepository,
    private val geocodingRepository: GeocodingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState = _uiState.asStateFlow()



    init {
        viewModelScope.launch {
            repository.allFavorites.collect { favoritesList ->
                _uiState.update { it.copy(favorites = favoritesList) }
            }
        }
    }

    fun setLastKnownLocation(location: LatLng) {

        if (_uiState.value.lastKnownLocation == null) {
            _uiState.update { it.copy(lastKnownLocation = location) }
        }
    }

    fun onMapClick(latLng: LatLng) {
        _uiState.update { it.copy(selectedLocation = latLng, addressIsLoading = true) }
        calculateDistance(latLng)
        fetchAddress(latLng)
    }

    private fun fetchAddress(latLng: LatLng) {
        viewModelScope.launch {

            val result: GeocodeResult<PlaceDetails> = geocodingRepository.getPlaceDetails(latLng)

            var addressText = ""

            when (result) {
                is GeocodeResult.Success -> {
                    addressText = result.data.address
                }
                is GeocodeResult.NoAddressFound -> {
                    val coords = result.latLng
                    addressText = "Lat: ${"%.4f".format(coords.latitude)}, Lng: ${"%.4f".format(coords.longitude)}"
                }
                is GeocodeResult.NetworkError -> {
                    addressText = "Error de red al obtener detalles."
                }
            }

            _uiState.update {
                it.copy(
                    selectedAddress = addressText,
                    addressIsLoading = false
                )
            }
        }
    }


    fun addFavorite(title: String) {
        val location = _uiState.value.selectedLocation ?: return


        if (title.isBlank()) {

            return
        }

        val address = _uiState.value.selectedAddress.takeIf { it.isNotBlank() } ?: "Sin dirección"

        val newFavorite = Favorite(
            title = title,
            address = address,
            latitude = location.latitude,
            longitude = location.longitude
        )
        viewModelScope.launch {
            repository.addFavorite(newFavorite)

            _uiState.update { it.copy(selectedLocation = null, distanceToSelectedPoint = null, selectedAddress = "") }
        }
    }



    fun updateFavorite(favorite: Favorite) {
        viewModelScope.launch {
            repository.updateFavorite(favorite)
            onFinishEditing()
        }
    }

    fun deleteFavorite(favorite: Favorite) {
        viewModelScope.launch {
            repository.deleteFavorite(favorite)
        }
    }

    fun onStartEditing(favorite: Favorite) {
        _uiState.update { it.copy(editingFavorite = favorite) }
    }

    fun onFinishEditing() {
        _uiState.update { it.copy(editingFavorite = null) }
    }

    fun setMapType(mapType: MapType) {
        _uiState.update { it.copy(mapType = mapType) }
    }

    private fun calculateDistance(targetLatLng: LatLng) {
        _uiState.value.lastKnownLocation?.let { userLocation ->
            val results = FloatArray(1)
            Location.distanceBetween(userLocation.latitude, userLocation.longitude, targetLatLng.latitude, targetLatLng.longitude, results)
            _uiState.update { it.copy(distanceToSelectedPoint = results[0]) }
        }
    }
}



sealed class GeocodeResult<T> {
    data class Success<T>(val data: T) : GeocodeResult<T>()
    data class NoAddressFound<T>(val latLng: LatLng) : GeocodeResult<T>()
    class NetworkError<T> : GeocodeResult<T>()
}