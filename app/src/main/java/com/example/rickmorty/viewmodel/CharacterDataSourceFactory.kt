package com.example.rickmorty.viewmodel

import androidx.paging.DataSource
import com.example.rickmorty.character.CharacterDataSource
import com.example.rickmorty.model.GetCharacterByIdResponse
import com.example.rickmorty.model.GetCharacterPageResponse
import com.example.rickmorty.repository.CharacterRepository
import kotlinx.coroutines.CoroutineScope


class CharacterDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val characterRepository: CharacterRepository
) : DataSource.Factory<Int, GetCharacterByIdResponse>() {
    override fun create(): DataSource<Int, GetCharacterByIdResponse> {
        return CharacterDataSource(coroutineScope, characterRepository)
    }
}