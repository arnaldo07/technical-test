package com.enmanuelbergling.technicaltest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.enmanuelbergling.technicaltest.ui.theme.DimensionTokens
import com.enmanuelbergling.technicaltest.ui.theme.ContactsTheme

@Composable
fun LargerIconButton(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(modifier = Modifier
        .clip(CircleShape)
        .background(MaterialTheme.colorScheme.primaryContainer)
        .clickable { onClick() }
        .then(modifier)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = imageVector.toString(),
            modifier = Modifier
                .padding(DimensionTokens.Small)
                .size(DimensionTokens.LargerIcon)
        )
    }
}

@Preview
@Composable
private fun LargerIconButtonPrev() {
    ContactsTheme {
        LargerIconButton(
            Icons.Rounded.Call
        ) {}
    }
}