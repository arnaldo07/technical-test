package com.enmanuelbergling.technicaltest.data.network.repo

import com.enmanuelbergling.technicaltest.data.network.mappers.toDomain
import com.enmanuelbergling.technicaltest.data.network.service.ContactService
import com.enmanuelbergling.technicaltest.domain.entity.Contact
import com.enmanuelbergling.technicaltest.domain.repo.ContactRepo

internal class ContactRepoImplementation(
    private val service: ContactService,
) : ContactRepo {
    override suspend fun getContactsPage(page: Int, pageSize: Int): Result<List<Contact>> =
        runCatching {
            service.getContactsPage(page, pageSize).results.map { it.toDomain() }
        }
}