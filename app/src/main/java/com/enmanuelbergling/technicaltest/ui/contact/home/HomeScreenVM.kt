package com.enmanuelbergling.technicaltest.ui.contact.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.enmanuelbergling.technicaltest.domain.usecase.GetPaginatedContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenVM @Inject constructor(
    getPaginatedContactsUseCase: GetPaginatedContactsUseCase,
) : ViewModel() {

    val contacts = getPaginatedContactsUseCase().cachedIn(viewModelScope)
}