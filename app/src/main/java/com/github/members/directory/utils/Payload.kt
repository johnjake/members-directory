package com.github.members.directory.utils

data class Payload(
    val type: String = "",
    val uid: String = "",
    val influencer_uid: String = "",
    val status: String = "",
    val from_uid: String = "",
    val to_uid: String = "",
    val from_influencer_uid: String = "",
    val to_influencer_uid: String = "",
    val qid: String = "",
    val fid: String = "",
    val last_active: String = "",
    val last_active_at: Long = 0L
)