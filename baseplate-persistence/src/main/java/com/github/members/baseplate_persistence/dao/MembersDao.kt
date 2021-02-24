package com.github.members.baseplate_persistence.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.members.baseplate_persistence.model.DBMembers
import com.github.members.baseplate_persistence.model.DBSearch

@Dao
abstract class MembersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMembers(members: DBMembers)

    @Query("INSERT INTO members (login, id, node_id, avatar_url, gravatar_id, url, html_url, followers_url, following_url, gists_url, starred_url, subscriptions_url, organizations_url, repos_url, events_url, received_events_url, type, site_admin) SELECT :login, :id, :node_id, :avatar_url, :gravatar_id, :url, :html_url, :followers_url, :following_url, :gists_url, :starred_url, :subscriptions_url, :organizations_url, :repos_url, :events_url, :received_events_url, :type, :site_admin WHERE NOT EXISTS ( SELECT id FROM members WHERE id = :id)")
    abstract fun insertMemberNotExist(login: String,
                                      id: Int,
                                      node_id: String,
                                      avatar_url: String,
                                      gravatar_id: String,
                                      url: String,
                                      html_url: String,
                                      followers_url: String,
                                      following_url: String,
                                      gists_url: String,
                                      starred_url: String,
                                      subscriptions_url: String,
                                      organizations_url: String,
                                      repos_url: String,
                                      events_url: String,
                                      received_events_url: String,
                                      type: String,
                                      site_admin: Boolean)

    @Query("DELETE FROM members")
    abstract suspend fun clearAllKeys()

    @Query("SELECT * FROM members")
    abstract fun getRepositoryMembers(): PagingSource<Int, DBMembers>

    @Query("SELECT * FROM members WHERE login LIKE :username")
    abstract suspend fun getSearchFromDb(username: String): List<DBMembers>

}