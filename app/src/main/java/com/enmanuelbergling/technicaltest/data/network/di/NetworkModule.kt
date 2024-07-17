package com.enmanuelbergling.technicaltest.data.network.di

import com.enmanuelbergling.technicaltest.data.network.paging.GetPaginatedContactsUseCaseImpl
import com.enmanuelbergling.technicaltest.data.network.repo.ContactRepoImplementation
import com.enmanuelbergling.technicaltest.data.network.service.BASE_URL
import com.enmanuelbergling.technicaltest.data.network.service.ContactService
import com.enmanuelbergling.technicaltest.domain.repo.ContactRepo
import com.enmanuelbergling.technicaltest.domain.usecase.GetPaginatedContactsUseCase
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

internal object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideContactService(retrofit: Retrofit): ContactService =
        retrofit.create(ContactService::class.java)

    @Provides
    @Singleton
    fun provideContactRepo(service: ContactService): ContactRepo =
        ContactRepoImplementation(service)

    @Provides
    @Singleton
    fun provideGetPaginatedContactsUseCase(repo: ContactRepo): GetPaginatedContactsUseCase =
        GetPaginatedContactsUseCaseImpl(repo)
}