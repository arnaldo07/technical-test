package com.enmanuelbergling.technicaltest.data.network.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.enmanuelbergling.technicaltest.domain.entity.Contact
import com.enmanuelbergling.technicaltest.domain.repo.ContactRepo
import com.enmanuelbergling.technicaltest.domain.usecase.GetPaginatedContactsUseCase
import kotlinx.coroutines.flow.Flow

internal class GetPaginatedContactsUseCaseImpl(
    private val repo: ContactRepo,
) : GetPaginatedContactsUseCase {

    override fun invoke(): Flow<PagingData<Contact>> = Pager(
        config = PagingConfig(PAGE_SIZE),
        pagingSourceFactory = {
            ContactPagingSource(
                repo
            )
        }
    ).flow
}