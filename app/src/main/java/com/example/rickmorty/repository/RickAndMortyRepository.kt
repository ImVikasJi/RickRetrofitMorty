package com.example.rickmorty.repository

import com.example.rickmorty.api.RetrofitInstance
import com.example.rickmorty.domain.RickAndMortyCache
import com.example.rickmorty.domain.mappers.CharacterMapper
import com.example.rickmorty.model.Character
import com.example.rickmorty.model.GetCharacterByIdResponse
import com.example.rickmorty.model.GetEpisodeByIdResponse
import retrofit2.Response

class RickAndMortyRepository {

    suspend fun getCharacterById(characterId: Int): Character? {

        val cachedCharacter = RickAndMortyCache.characterMap[characterId]
        if(cachedCharacter != null){
            return  cachedCharacter
        }


        val request = RetrofitInstance.apiClient.getCharacterById(characterId)

        if (request.failed || !request.isSuccessFul) {
            return null
        }

        val networkEpisodes = getEpisodesFromCharacterResponse(request.body)

        val character =  CharacterMapper.buildForm(
            response = request.body,
            episodes = networkEpisodes
        )
        RickAndMortyCache.characterMap[characterId] = character
        return character
    }

    private suspend fun getEpisodesFromCharacterResponse(characterByIdResponse: GetCharacterByIdResponse)
            : List<GetEpisodeByIdResponse> {
        val episodeRange = characterByIdResponse.episode.map {
            it.substring(startIndex = it.lastIndexOf("/") + 1)
        }.toString()
        val request = RetrofitInstance.apiClient.getEpisodeRange(episodeRange)
        if (request.failed || !request.isSuccessFul) {
            return emptyList()
        }
        return request.body

    }

}
