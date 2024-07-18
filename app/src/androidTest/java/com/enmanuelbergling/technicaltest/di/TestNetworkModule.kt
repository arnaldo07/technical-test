package com.enmanuelbergling.technicaltest.di

import com.enmanuelbergling.technicaltest.data.network.di.NetworkModule
import com.enmanuelbergling.technicaltest.data.network.paging.GetPaginatedContactsUseCaseImpl
import com.enmanuelbergling.technicaltest.domain.repo.ContactRepo
import com.enmanuelbergling.technicaltest.domain.usecase.GetPaginatedContactsUseCase
import com.enmanuelbergling.technicaltest.repo.FakeContactRepoImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
internal object TestNetworkModule {

    @Provides
    @Singleton
    fun provideContactRepo(): ContactRepo =
        FakeContactRepoImplementation()

    @Provides
    @Singleton
    fun provideGetPaginatedContactsUseCase(repo: ContactRepo): GetPaginatedContactsUseCase =
        GetPaginatedContactsUseCaseImpl(repo)
}