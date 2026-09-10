package com.simats.com.network.response

data class FamilyMemberItem(
    val id: String,
    val username: String,
    val phone: String?,
    val status: String,
    val device: String?,
    val battery: Int
)

data class FamilyMembersResponse(
    val members: List<FamilyMemberItem>
)
