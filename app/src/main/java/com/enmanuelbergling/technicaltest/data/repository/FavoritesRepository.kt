package com.enmanuelbergling.technicaltest.data.repository

import com.enmanuelbergling.technicaltest.data.local.Favorite
import com.enmanuelbergling.technicaltest.data.local.FavoriteDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FavoritesRepository @Inject constructor(private val favoriteDao: FavoriteDao) {
    val allFavorites: Flow<List<Favorite>> = favoriteDao.getAllFavorites()
    suspend fun addFavorite(favorite: Favorite) = favoriteDao.insert(favorite)
    suspend fun updateFavorite(favorite: Favorite) = favoriteDao.update(favorite)
    suspend fun deleteFavorite(favorite: Favorite) = favoriteDao.delete(favorite)
}