package com.example.rickmorty.epoxy

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.rickmorty.R
import com.example.rickmorty.databinding.ModelEpisodeListItemBinding
import com.example.rickmorty.databinding.ModelEpisodeListTitleBinding
import com.example.rickmorty.episodes.EpisodeUiModel
import com.example.rickmorty.model.Character

class EpisodeListEpoxyController(private val onEpisodeClicked: (Int) -> Unit): PagingDataEpoxyController<EpisodeUiModel> (){
    override fun buildItemModel(currentPosition: Int, item: EpisodeUiModel?): EpoxyModel<*> {
        return when(item!!){
            is EpisodeUiModel.Item ->{
                val episode = (item as EpisodeUiModel.Item).episode
                EpisodeListItemEpoxyModel(
                    episodes = episode,
                    onClick = { episodeId->
                            onEpisodeClicked(episodeId)
                    }
                ).id("episodes_${episode.id}")
            }

            is EpisodeUiModel.Header ->{
                val headerText = (item as EpisodeUiModel.Header).text
                EpisodeListTitleEpoxyModel(headerText).id("header_${headerText}")
            }
        }

    }
    data class EpisodeListItemEpoxyModel(
        val episodes: Character.Episode,
        val onClick : (Int) -> Unit
    ): ViewBindingKotlinModel<ModelEpisodeListItemBinding>(R.layout.model_episode_list_item){
        override fun ModelEpisodeListItemBinding.bind() {
            episodeNameTextView.text = episodes.name
            episodeAirDateTextView.text = episodes.airDate
            episodeNumberTextView.text = episodes.getFormattedSeasonTruncated()

            root.setOnClickListener { onClick(episodes.id) }
        }

    }

    data class EpisodeListTitleEpoxyModel(
        val title: String
    ) : ViewBindingKotlinModel<ModelEpisodeListTitleBinding>(R.layout.model_episode_list_title) {

        override fun ModelEpisodeListTitleBinding.bind() {
            textView.text = title
        }
    }
}