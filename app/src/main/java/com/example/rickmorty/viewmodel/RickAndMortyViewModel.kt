package com.example.rickmorty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.domain.RickAndMortyCache
import com.example.rickmorty.domain.mappers.CharacterMapper
import com.example.rickmorty.model.Character
import com.example.rickmorty.model.GetCharacterByIdResponse
import com.example.rickmorty.repository.RickAndMortyRepository
import kotlinx.coroutines.launch

class RickAndMortyViewModel : ViewModel() {
    private val rickAndMortyRepository = RickAndMortyRepository()

    private val _characterByIdLiveData = MutableLiveData<Character?>()
    val characterByIdLiveData: LiveData<Character?> = _characterByIdLiveData


    fun refreshCharacter(characterId: Int) {

        //Otherwise, we need to make the network call
        viewModelScope.launch {
            val character = rickAndMortyRepository.getCharacterById(characterId)
            _characterByIdLiveData.postValue(character)

        }
    }
}