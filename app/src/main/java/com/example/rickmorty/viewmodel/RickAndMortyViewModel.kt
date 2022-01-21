package com.example.rickmorty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.model.GetCharacterByIdResponse
import com.example.rickmorty.repository.RickAndMortyRepository
import kotlinx.coroutines.launch

class RickAndMortyViewModel : ViewModel() {
    private val rickAndMortyRepository = RickAndMortyRepository()

    private val _characterByIdLiveData = MutableLiveData<GetCharacterByIdResponse>()
    val characterByIdLiveData: LiveData<GetCharacterByIdResponse> = _characterByIdLiveData

    fun refreshCharacter(characterId: Int){
        viewModelScope.launch {
            val response =  rickAndMortyRepository.getCharacterById(characterId)
            _characterByIdLiveData.postValue(response!!)
        }
    }
}