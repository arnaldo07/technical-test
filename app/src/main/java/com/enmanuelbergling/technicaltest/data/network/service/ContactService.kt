package com.enmanuelbergling.technicaltest.data.network.service

import com.enmanuelbergling.technicaltest.data.network.dto.ContactsPageDTO
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ContactService {

    @GET("/api")
    suspend fun getContactsPage(
       @Query("page") page: Int,
       @Query("results") pageSize: Int,
       @Query("seed") seed: String = "abc"
    ): ContactsPageDTO
}