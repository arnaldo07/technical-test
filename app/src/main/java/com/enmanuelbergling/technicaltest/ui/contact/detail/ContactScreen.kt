package com.enmanuelbergling.technicaltest.ui.contact.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.enmanuelbergling.technicaltest.domain.entity.Coordinates

@Composable
fun ContactRoute(onBack: ()->Unit, onLocate: (Coordinates)->Unit) {

    Text(text = "Contact details")
}