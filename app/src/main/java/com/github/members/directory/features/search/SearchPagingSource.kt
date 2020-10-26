package com.github.members.directory.features.search

import android.content.Context
import android.content.SharedPreferences
import androidx.paging.PagingSource
import com.github.members.baseplate_persistence.AppDatabase
import com.github.members.directory.api.ApiServices
import com.github.members.directory.data.mapper.SearchMapper
import com.github.members.directory.data.vo.SearchProfile
import com.github.members.directory.di.providesSharedOnline
import com.github.members.directory.di.providesSharedPreferences
import retrofit2.HttpException
import timber.log.Timber

private const val STARTING_PAGE_INDEX = 1
class SearchPagingSource(
        private val query: String,
        private val apiServices: ApiServices,
        private val appDatabase: AppDatabase,
        private val context: Context
) : PagingSource<Int, SearchProfile>() {

    private val isLocalStorage = providesSharedOnline(context) ?: false
    private val mapper = SearchMapper.getInstance()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchProfile> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = apiServices.getSearchProfile(query, position)
            val dataResult = response.items
            if(!isLocalStorage) {
                dataResult.forEach { item ->
                    val userMap = mapper.fromData(item)
                    appDatabase.searchDao().insertSearchDao(userMap)
                }
            }
            LoadResult.Page(
                    data = dataResult,
                    prevKey = if (position == STARTING_PAGE_INDEX) null else position -1,
                    nextKey = if (dataResult.isEmpty()) null else position + 1
            )

        } catch (ex: Exception) {
            LoadResult.Error(ex)
        } catch (ex: HttpException) {
            LoadResult.Error(ex)
        }
    }
}