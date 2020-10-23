package com.github.members.directory.features.members.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.github.members.baseplate_persistence.AppDatabase
import com.github.members.baseplate_persistence.model.DBMembers
import com.github.members.baseplate_persistence.model.DBRemoteKeys
import com.github.members.directory.api.ApiServices
import com.github.members.directory.data.mapper.MembersMapper
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

@ExperimentalPagingApi
class PagingMediatorMembers(
    private val apiServices: ApiServices,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, DBMembers>() {

    private val mapper: MembersMapper = MembersMapper.getInstance()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DBMembers>
    ): MediatorResult {
        val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                if(pageKeyData!= null)
                    pageKeyData as Int
                else 0
            }
        }

        try {
            val response = apiServices.getGithubUsers(page)
            val isEndOfList = response.isEmpty()
            appDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {

                    appDatabase.remoteKeysDao().clearRemoteKeys()
                    appDatabase.membersDao().clearAllKeys()
                }
                val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val dbKeys = response.map {
                    DBRemoteKeys(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                appDatabase.remoteKeysDao().insertAll(dbKeys)
                response.forEach { movie ->
                    val domainMovie = mapper.fromData(movie)
                    appDatabase.membersDao().insertMembers(domainMovie)
                }
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }
    /**
     * this returns the page key or the final end of list success result
     */
    @SuppressLint("LogNotTimber")
    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, DBMembers>): Any? {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getClosestRemoteKey(state)
                remoteKeys?.nextKey?.minus(1) ?: DEFAULT_PAGE_INDEX
            }
            LoadType.APPEND -> {
                try {
                    val remoteKeys = getLastRemoteKey(state)
                    remoteKeys?.nextKey
                }catch (ex: Exception) {
                    Log.e("PREPEND", "message: ${ex.message}")
                }
            }
            LoadType.PREPEND -> {
                try {
                    val remoteKeys = getFirstRemoteKey(state)
                    //end of list condition reached
                    remoteKeys?.prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    remoteKeys.prevKey
                } catch (ex: Exception) {
                    Log.e("PREPEND", "message: ${ex.message}")
                }
            }
        }
    }
    /**
     * get the last remote key inserted which had the data
     */
    private suspend fun getLastRemoteKey(state: PagingState<Int, DBMembers>): DBRemoteKeys? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { movie -> appDatabase.remoteKeysDao().remoteKeysId(movie.id) }
    }
    /**
     * get the first remote key inserted which had the data
     */
    private suspend fun getFirstRemoteKey(state: PagingState<Int, DBMembers>): DBRemoteKeys? {
        return state.pages
            .firstOrNull() { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { movie -> appDatabase.remoteKeysDao().remoteKeysId(movie.id) }
    }
    /**
     * get the closest remote key inserted which had the data
     */
    private suspend fun getClosestRemoteKey(state: PagingState<Int, DBMembers>): DBRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                appDatabase.remoteKeysDao().remoteKeysId(repoId)
            }
        }
    }

    companion object {
       const val DEFAULT_PAGE_INDEX = 1
    }
}

