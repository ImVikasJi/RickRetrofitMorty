package com.example.rickmorty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.rickmorty.model.GetCharacterByIdResponse
import com.example.rickmorty.repository.CharacterRepository
import com.example.rickmorty.util.Constants.Companion.PAGE_SIZE
import com.example.rickmorty.util.Constants.Companion.PREFETCH_DISTANCE

class CharacterViewModel: ViewModel() {
    private val characterRepository = CharacterRepository()
    private val pageListConfig: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(PAGE_SIZE)
        .setPrefetchDistance(PREFETCH_DISTANCE)
        .build()

    private val dataSourceFactory = CharacterDataSourceFactory(viewModelScope,characterRepository)
    val characterPagedListLiveData: LiveData<PagedList<GetCharacterByIdResponse>> =
        LivePagedListBuilder(dataSourceFactory, pageListConfig).build()

}