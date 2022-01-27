package com.example.rickmorty.repository

import com.example.rickmorty.api.RetrofitInstance
import com.example.rickmorty.model.GetCharacterByIdResponse
import com.example.rickmorty.model.GetCharacterPageResponse

class CharacterRepository {

    suspend fun getCharacterPage(pageIndex: Int): GetCharacterPageResponse?{
        val response = RetrofitInstance.apiClient.getCharactersPage(pageIndex)

        if(response.failed || !response.isSuccessFul)
            return  null

        return response.body

    }
}