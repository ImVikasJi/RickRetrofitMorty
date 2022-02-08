package com.example.rickmorty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.model.Character
import com.example.rickmorty.repository.EpisodeRepository
import kotlinx.coroutines.launch

class EpisodeDetailViewModel : ViewModel() {

    private val episodeRepository = EpisodeRepository()

    private var _episodeLiveData = MutableLiveData<Character.Episode?>()
    val episodeLiveData: LiveData<Character.Episode?> = _episodeLiveData

    fun fetchEpisode(episodeId: Int) {
        viewModelScope.launch {
            val episode = episodeRepository.getEpisodeById(episodeId)

            _episodeLiveData.postValue(episode)
        }
    }
}