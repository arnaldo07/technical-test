package com.enmanuelbergling.technicaltest.ui.feature.contact.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.PagingData
import com.enmanuelbergling.technicaltest.domain.entity.Contact
import com.enmanuelbergling.technicaltest.domain.entity.Coordinates

@Composable
fun ContactRoute(onBack: ()->Unit, onLocate: (Coordinates)->Unit) {

    Text(text = "Contact details")
}