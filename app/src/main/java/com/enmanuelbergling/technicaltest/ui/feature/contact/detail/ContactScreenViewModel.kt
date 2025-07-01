package com.enmanuelbergling.technicaltest.ui.feature.contact.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.enmanuelbergling.technicaltest.domain.entity.Contact
import com.enmanuelbergling.technicaltest.ui.feature.contact.navigation.ContactDestination
import com.enmanuelbergling.technicaltest.ui.feature.contact.navigation.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val contactState: MutableState<Contact> =
        mutableStateOf(savedStateHandle.toRoute<ContactDestination>().toDomain())
}