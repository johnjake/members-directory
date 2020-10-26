package com.github.members.directory.data.mapper

import com.github.members.baseplate_persistence.model.DBSearch
import com.github.members.directory.data.vo.SearchProfile

class SearchMapper {
    fun fromStorage(from: DBSearch): SearchProfile {
        return SearchProfile(
                login = from.login,
                id = from.id,
                node_id = from.node_id,
                avatar_url = from.avatar_url,
                gravatar_id = from.gravatar_id,
                url = from.url,
                html_url = from.html_url,
                followers_url = from.followers_url,
                following_url = from.following_url,
                gists_url = from.gists_url,
                starred_url = from.starred_url,
                subscriptions_url = from.subscriptions_url,
                organizations_url = from.organizations_url,
                repos_url = from.repos_url,
                events_url = from.events_url,
                received_events_url = from.received_events_url,
                type = from.type,
                site_admin = from.site_admin,
                score = from.score
        )
    }
    fun fromData(from: SearchProfile): DBSearch {
        return DBSearch(
                searchId = 0,
                login = from.login,
                id = from.id,
                node_id = from.node_id,
                avatar_url = from.avatar_url,
                gravatar_id = from.gravatar_id,
                url = from.url,
                html_url = from.html_url,
                followers_url = from.followers_url,
                following_url = from.following_url,
                gists_url = from.gists_url,
                starred_url = from.starred_url,
                subscriptions_url = from.subscriptions_url,
                organizations_url = from.organizations_url,
                repos_url = from.repos_url,
                events_url = from.events_url,
                received_events_url = from.received_events_url,
                type = from.type,
                site_admin = from.site_admin,
                score = from.score)
    }

    companion object {
        const val GITHUB_MEMBERS = "github_search"

        private var INSTANCE: SearchMapper? = null

        fun getInstance(): SearchMapper =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: SearchMapper().also { INSTANCE = it }
                }
    }
}