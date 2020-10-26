package com.github.members.baseplate_persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DBSearch.TABLE_NAME_SEARCH)
data class DBSearch(
        @PrimaryKey(autoGenerate = true)
        val searchId: Int,
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
        val site_admin: Boolean = false,
        val score : Int = 0
) {
    constructor(): this(
            0,
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
            false,
            0)
    companion object {
        const val TABLE_NAME_SEARCH = "table_search"
    }
}