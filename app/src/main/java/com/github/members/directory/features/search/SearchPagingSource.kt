package com.github.members.directory.features.search

import androidx.paging.PagingSource
import com.github.members.directory.api.ApiServices
import com.github.members.directory.data.vo.SearchProfile
import retrofit2.HttpException

private const val STARTING_PAGE_INDEX = 1
class SearchPagingSource(
        private val query: String,
        private val apiServices: ApiServices
) : PagingSource<Int, SearchProfile>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchProfile> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = apiServices.getSearchProfile(query, position)
            val dataResult = response.items

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