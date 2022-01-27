package com.example.rickmorty.api

import com.example.rickmorty.SimpleResponse
import com.example.rickmorty.model.GetCharacterByIdResponse
import com.example.rickmorty.model.GetCharacterPageResponse
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

    //Function to get
    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}