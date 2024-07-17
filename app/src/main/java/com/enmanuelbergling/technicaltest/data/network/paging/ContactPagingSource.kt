package com.enmanuelbergling.technicaltest.data.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.enmanuelbergling.technicaltest.domain.entity.Contact
import com.enmanuelbergling.technicaltest.domain.repo.ContactRepo

class ContactPagingSource(
    private val repo: ContactRepo,
) : PagingSource<Int, Contact>() {
    override fun getRefreshKey(state: PagingState<Int, Contact>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Contact> {
        val position = params.key ?: 1

        val response = repo.getContactsPage(position, PAGE_SIZE)

        response.onSuccess { contacts ->
            //the api does not expose a the total of pages
            val nextKey = position + 1

            return LoadResult.Page(
                data = contacts,
                prevKey = if (position == 1) null else position - 1,
                nextKey = nextKey
            )
        }

        val exception = response.exceptionOrNull()?.let {
            Exception(it)
        } ?: Exception("An error just happened")

        return LoadResult.Error(exception)
    }
}