package com.example.rickmorty.api

import com.example.rickmorty.SimpleResponse
import com.example.rickmorty.model.GetCharacterByIdResponse
import com.example.rickmorty.model.GetCharacterPageResponse
import com.example.rickmorty.model.GetEpisodeByIdResponse
import com.example.rickmorty.model.GetEpisodePageResponse
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class ApiClient(
    private val rickAndMortyService: RickAndMortyService
) {
    suspend fun getCharacterById(characterId: Int): SimpleResponse<GetCharacterByIdResponse> {
        return safeApiCall { rickAndMortyService.getCharacterById(characterId) }
    }

    suspend fun getCharactersPage(pageIndex: Int) : SimpleResponse<GetCharacterPageResponse>{
        return safeApiCall { rickAndMortyService.getCharacterPage(pageIndex) }
    }

    suspend fun getCharactersPage(
        characterName: String,
        pageIndex: Int
    ): SimpleResponse<GetCharacterPageResponse> {
        return safeApiCall { rickAndMortyService.getCharactersPage(characterName, pageIndex) }
    }

    suspend fun getEpisodeId(episode: Int): SimpleResponse<GetEpisodeByIdResponse>{
        return safeApiCall { rickAndMortyService.getEpisodeId(episode) }
    }

    suspend fun getEpisodeRange(episodeRange: String): SimpleResponse<List<GetEpisodeByIdResponse>>{
        return safeApiCall { rickAndMortyService.getEpisodeRange(episodeRange) }
    }

    suspend fun getEpisodesPage(pageIndex: Int): SimpleResponse<GetEpisodePageResponse>{
        return safeApiCall { rickAndMortyService.getEpisodesPage(pageIndex) }
    }

    suspend fun getMultipleCharacters(characterList: List<String>): SimpleResponse<List<GetCharacterByIdResponse>> {
        return safeApiCall { rickAndMortyService.getMultipleCharacters(characterList) }
    }

    //Function to get
    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}