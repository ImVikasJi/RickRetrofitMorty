package com.example.rickmorty.domain.mappers

import com.example.rickmorty.model.Character
import com.example.rickmorty.model.GetCharacterByIdResponse
import com.example.rickmorty.model.GetEpisodeByIdResponse

object CharacterMapper {

    fun buildForm(response: GetCharacterByIdResponse,episodes: List<GetEpisodeByIdResponse>): Character {
        return Character(
            episodeList = episodes.map {
                EpisodeMapper.buildForm(it)
            },  // todo()
            gender = response.gender,
            id = response.id,
            image = response.image,
            location = Character.Location(
                name = response.location.name,
                url = response.location.url
            ),
            name = response.name,
            origin = Character.Origin(
                name = response.origin.name,
                url = response.origin.url
            ),
            species = response.species,
            status = response.status
        )
    }
}