package com.enmanuelbergling.technicaltest.repo

import com.enmanuelbergling.technicaltest.domain.repo.ContactRepo
import com.enmanuelbergling.technicaltest.mock.FAKE_CONTACTS

class FakeContactRepoImplementation : ContactRepo {
    override suspend fun getContactsPage(page: Int, pageSize: Int) =
        runCatching { FAKE_CONTACTS }
}