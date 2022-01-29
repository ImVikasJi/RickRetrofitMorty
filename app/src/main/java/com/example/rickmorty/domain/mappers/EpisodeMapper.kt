package com.example.rickmorty.domain.mappers

import com.example.rickmorty.model.Character
import com.example.rickmorty.model.GetEpisodeByIdResponse

object EpisodeMapper {

    fun buildForm(episodeByIdResponse: GetEpisodeByIdResponse): Character.Episode {
        return Character.Episode(
            id = episodeByIdResponse.id,
            name = episodeByIdResponse.name,
            airDate = episodeByIdResponse.air_date,
            episode = episodeByIdResponse.episode
        )
    }
}