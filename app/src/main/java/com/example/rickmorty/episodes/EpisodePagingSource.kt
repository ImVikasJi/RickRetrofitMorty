package com.example.rickmorty.episodes

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickmorty.api.RetrofitInstance
import com.example.rickmorty.domain.mappers.EpisodeMapper
import com.example.rickmorty.model.Character
import com.example.rickmorty.repository.CharacterRepository
import com.example.rickmorty.repository.EpisodeRepository
import kotlinx.coroutines.CoroutineScope

class EpisodePagingSource(
    private val episodeRepository: EpisodeRepository
) : PagingSource<Int, EpisodeUiModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodeUiModel> {
        val pageNumber = params.key ?: 1
        val previousKey = if (pageNumber == 1) null else pageNumber - 1

        val pageResponse = RetrofitInstance.apiClient.getEpisodesPage(pageNumber)

        pageResponse.exception?.let {
            return LoadResult.Error(it)
        }

        return LoadResult.Page(
            data = pageResponse.body.results.map { response ->
                EpisodeUiModel.Item(EpisodeMapper.buildForm(response))
            },
            prevKey = previousKey, // Only paging forward.
            nextKey = getPageIndexFromNext(pageResponse.body.info.next)
        )
    }

    override fun getRefreshKey(state: PagingState<Int, EpisodeUiModel>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private fun getPageIndexFromNext(next: String?): Int? {
        return next?.split("?page=")?.get(1)?.toInt()
    }
}