package com.example.rickmorty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import com.example.rickmorty.episodes.EpisodePagingSource
import com.example.rickmorty.episodes.EpisodeUiModel
import com.example.rickmorty.repository.EpisodeRepository
import com.example.rickmorty.util.Constants.Companion.PAGE_SIZE
import com.example.rickmorty.util.Constants.Companion.PREFETCH_DISTANCE
import kotlinx.coroutines.flow.map

class EpisodeViewModel : ViewModel() {

    private val episodeRepository = EpisodeRepository()

    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) {
        EpisodePagingSource(episodeRepository)
    }.flow.cachedIn(viewModelScope).map {
        it.insertSeparators { model: EpisodeUiModel?, model2: EpisodeUiModel? ->
            if (model == null) {
                return@insertSeparators EpisodeUiModel.Header("Season 1")
            }

            // No footer
            if (model2 == null) {
                return@insertSeparators null
            }

            // Make sure we only care about the items (episodes)
            if (model is EpisodeUiModel.Header || model2 is EpisodeUiModel.Header) {
                return@insertSeparators null
            }

            // Little logic to determine if a separator is necessary
            val episode1 = (model as EpisodeUiModel.Item).episode
            val episode2 = (model2 as EpisodeUiModel.Item).episode
            return@insertSeparators if (episode2.seasonNumber != episode1.seasonNumber) {
                EpisodeUiModel.Header("Season ${episode2.seasonNumber}")
            } else {
                null
            }
        }
    }
}