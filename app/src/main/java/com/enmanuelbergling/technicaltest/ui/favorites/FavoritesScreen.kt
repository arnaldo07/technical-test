package com.enmanuelbergling.technicaltest.ui.favorites

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.enmanuelbergling.technicaltest.data.local.Favorite
import com.enmanuelbergling.technicaltest.ui.components.AddEditFavoriteDialog
import com.enmanuelbergling.technicaltest.viewmodel.MapViewModel

@Composable
fun FavoritesScreen(
    viewModel: MapViewModel = hiltViewModel(),
    onFavoriteClick: (Favorite) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    uiState.editingFavorite?.let { favoriteToEdit ->
        var title by remember(favoriteToEdit.id) { mutableStateOf(favoriteToEdit.title) }
        var isError by remember { mutableStateOf(false) }

        AddEditFavoriteDialog(
            title = title,
            isError = isError,
            dialogTitle = "Editar Favorito",
            onTitleChange = { newTitle ->
                title = newTitle
                if (isError) isError = false
            },
            onConfirm = {
                if (title.isNotBlank()) {
                    viewModel.updateFavorite(favoriteToEdit.copy(title = title))
                } else {
                    isError = true
                }
            },
            onDismiss = { viewModel.onFinishEditing() }
        )
    }

    if (uiState.favorites.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No tienes lugares favoritos guardados.")
        }
    } else {
        LazyColumn(contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)) {
            items(uiState.favorites, key = { it.id }) { favorite ->
                FavoriteCardItem(
                    modifier = Modifier.animateItem(
                        tween(durationMillis = 300)
                    ),
                    favorite = favorite,
                    onItemClick = { onFavoriteClick(favorite) },
                    onEditClick = { viewModel.onStartEditing(favorite) },
                    onDeleteClick = { viewModel.deleteFavorite(favorite) }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteCardItem(
    modifier: Modifier = Modifier,
    favorite: Favorite,
    onItemClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        onClick = onItemClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono principal
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = "Ubicación",
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(16.dp))


            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = favorite.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = favorite.address,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(8.dp))


            Row {
                IconButton(onClick = onEditClick) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}