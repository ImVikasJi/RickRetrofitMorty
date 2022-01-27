package com.example.rickmorty.api

import com.example.rickmorty.model.GetCharacterByIdResponse
import com.example.rickmorty.model.GetCharacterPageResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyService {

    @GET("character/{character-id}")
    suspend fun getCharacterById(
        @Path("character-id") characterId: Int
    ): Response<GetCharacterByIdResponse>

    @GET("character")
    suspend fun getCharacterPage(
        @Query("page") pageIndex: Int
    ): Response<GetCharacterPageResponse>

}