package com.github.members.directory.data.mapper

import com.github.members.baseplate_persistence.model.DBMembers
import com.github.members.directory.data.vo.Members


class MembersMapper private constructor() {

    fun mapFromDomain(from: Members): Map<String, Any>{
        val map = mutableMapOf<String, Any>()
        map[GITHUB_MEMBERS] = DBMembers(
            membersId = 0,
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
            site_admin = from.site_admin
            )
        return map
    }

    fun fromStorage(from: DBMembers): Members {
        return Members(
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
            site_admin = from.site_admin
        )
    }

    fun fromData(from: Members): DBMembers {
        return DBMembers(
            0,
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
            site_admin = from.site_admin)
    }

    companion object {
        const val GITHUB_MEMBERS = "github_members"

        private var INSTANCE: MembersMapper? = null

        fun getInstance(): MembersMapper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: MembersMapper().also { INSTANCE = it }
            }
    }
}