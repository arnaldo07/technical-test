package com.enmanuelbergling.technicaltest.domain.usecase

import androidx.paging.PagingData
import com.enmanuelbergling.technicaltest.domain.entity.Contact
import kotlinx.coroutines.flow.Flow

interface GetPaginatedContactsUseCase {

    operator fun invoke(): Flow<PagingData<Contact>>
}