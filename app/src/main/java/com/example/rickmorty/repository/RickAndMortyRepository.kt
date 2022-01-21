package com.example.rickmorty.repository

import com.example.rickmorty.api.RetrofitInstance
import com.example.rickmorty.model.GetCharacterByIdResponse
import retrofit2.Response

class RickAndMortyRepository {

    suspend fun getCharacterById(characterId: Int): GetCharacterByIdResponse? {
     val response = RetrofitInstance.apiClient.getCharacterById(characterId)

        if(response.isSuccessful) return response.body()!!

        return null
    }
}