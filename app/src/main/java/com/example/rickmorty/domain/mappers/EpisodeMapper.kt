package com.example.rickmorty.domain.mappers

import com.example.rickmorty.model.Character
import com.example.rickmorty.model.GetCharacterByIdResponse
import com.example.rickmorty.model.GetEpisodeByIdResponse

object EpisodeMapper {

    fun buildForm(
        episodeByIdResponse: GetEpisodeByIdResponse,
        networkCharacters: List<GetCharacterByIdResponse> = emptyList()
    ): Character.Episode {
        return Character.Episode(
            id = episodeByIdResponse.id,
            name = episodeByIdResponse.name,
            airDate = episodeByIdResponse.air_date,
            seasonNumber = getSeasonNumberFromEpisodeString(episodeByIdResponse.episode),
            episodeNumber = getEpisodeNumberFromEpisodeString(episodeByIdResponse.episode),
            characters = networkCharacters.map {
                CharacterMapper.buildForm(it)
            }
        )
    }

    private fun getSeasonNumberFromEpisodeString(episode: String): Int {
        val endIndex = episode.indexOfFirst { it.equals('e', true) }
        if (endIndex == -1) {
            return 0
        }
        return episode.substring(1, endIndex).toIntOrNull() ?: 0
    }

    private fun getEpisodeNumberFromEpisodeString(episode: String): Int {
        val startIndex = episode.indexOfFirst { it.equals('e', true) }
        if (startIndex == -1) {
            return 0
        }
        return episode.substring(startIndex + 1).toIntOrNull() ?: 0
    }
}