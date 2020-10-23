package com.github.members.baseplate_persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DBMembers.TABLE_NAME_MEMBERS)
data class DBMembers(
    @PrimaryKey(autoGenerate = true)
    val membersId: Int,
    val login: String? = "",
    val id: Int = 0,
    val node_id: String? = "",
    val avatar_url: String? = "",
    val gravatar_id: String? = "",
    val url: String? = "",
    val html_url: String? = "",
    val followers_url: String? = "",
    val following_url: String? = "",
    val gists_url: String? = "",
    val starred_url: String? = "",
    val subscriptions_url: String? = "",
    val organizations_url: String? = "",
    val repos_url: String? = "",
    val events_url: String? = "",
    val received_events_url: String? = "",
    val type: String? = "",
    val site_admin: Boolean = false) {

    constructor(): this(0,
        "",
        0,
        "",
        "",
        "",
        "",
    "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        false)

    companion object {
        const val TABLE_NAME_MEMBERS = "members"
    }
}