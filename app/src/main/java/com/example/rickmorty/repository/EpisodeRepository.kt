package com.example.rickmorty.repository

import com.example.rickmorty.api.RetrofitInstance
import com.example.rickmorty.domain.mappers.EpisodeMapper
import com.example.rickmorty.model.Character
import com.example.rickmorty.model.GetCharacterByIdResponse
import com.example.rickmorty.model.GetEpisodeByIdResponse
import com.example.rickmorty.model.GetEpisodePageResponse

class EpisodeRepository {

    suspend fun fetchEpisode(pageIndex: Int): GetEpisodePageResponse?{
        val response = RetrofitInstance.apiClient.getEpisodesPage(pageIndex)

        if(response.failed || !response.isSuccessFul)
            return  null

        return response.body
    }

    suspend fun getEpisodeById(episodeId: Int): Character.Episode? {
        val request = RetrofitInstance.apiClient.getEpisodeId(episodeId)

        if (!request.isSuccessFul) {
            return null
        }

        val characterList = getCharactersFromEpisodeResponse(request.body)
        return EpisodeMapper.buildForm(
            episodeByIdResponse = request.body,
            networkCharacters = characterList
        )
    }

    private suspend fun getCharactersFromEpisodeResponse(
        episodeByIdResponse: GetEpisodeByIdResponse
    ): List<GetCharacterByIdResponse> {
        val characterList = episodeByIdResponse.characters.map {
            it.substring(startIndex = it.lastIndexOf("/") + 1)
        }

        val request = RetrofitInstance.apiClient.getMultipleCharacters(characterList)
        return request.bodyNullable ?: emptyList()
    }
}