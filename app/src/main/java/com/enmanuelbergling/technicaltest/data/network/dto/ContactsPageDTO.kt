package com.enmanuelbergling.technicaltest.data.network.dto


import com.google.gson.annotations.SerializedName

internal data class ContactsPageDTO(
    @SerializedName("results")
    val results: List<ContactDTO>
)