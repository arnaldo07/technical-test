package com.enmanuelbergling.technicaltest.domain.repo

import com.enmanuelbergling.technicaltest.domain.entity.Contact

interface ContactRepo {

    suspend fun getContactsPage(
        page: Int,
        pageSize: Int,
    ): Result<List<Contact>>
}