package com.github.members.baseplate_persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DBProfiles.TABLE_NAME_PROFILE)
data class DBProfiles(
        @PrimaryKey(autoGenerate = true)
        val profileId: Int,
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
        val name: String? = "",
        val company: String? = "",
        val blog: String? = "",
        val location: String? = "",
        val email: String? = "",
        val hireable: String? = "",
        val bio: String? = "",
        val twitter_username: String? = "",
        val public_repos: Int = 0,
        val public_gists: Int = 0,
        val followers: Int = 0,
        val following: Int = 0,
        val created_at: String? = "",
        val updated_at: String? = ""
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
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            0,
            "",
            "")

    companion object {
        const val TABLE_NAME_PROFILE = "table_profiles"
    }
}