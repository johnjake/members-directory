package com.github.members.directory.data.mapper

import com.github.members.baseplate_persistence.model.DBProfiles
import com.github.members.directory.data.vo.Profiles

class ProfileMapper {

    fun fromStorage(from: DBProfiles): Profiles {
        return Profiles(
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
                name = from.name,
                company = from.company,
                blog = from.blog,
                location = from.location,
                email = from.email,
                hireable = from.hireable,
                bio = from.bio,
                twitter_username = from.twitter_username,
                public_repos = from.public_repos,
                public_gists = from.public_gists,
                following = from.following,
                created_at = from.created_at,
                updated_at = from.updated_at)
    }

    fun fromData(from: Profiles): DBProfiles {
        return DBProfiles(
                profileId = 0,
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
                name = from.name,
                company = from.company,
                blog = from.blog,
                location = from.location,
                email = from.email,
                hireable = from.hireable,
                bio = from.bio,
                twitter_username = from.twitter_username,
                public_repos = from.public_repos,
                public_gists = from.public_gists,
                following = from.following,
                created_at = from.created_at,
                updated_at = from.updated_at)
    }


    companion object {
        const val GITHUB_MEMBERS = "github_profile"

        private var INSTANCE: ProfileMapper? = null

        fun getInstance(): ProfileMapper =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: ProfileMapper().also { INSTANCE = it }
                }
    }
}