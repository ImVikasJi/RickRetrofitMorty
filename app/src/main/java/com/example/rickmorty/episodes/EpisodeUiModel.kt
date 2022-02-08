package com.example.rickmorty.episodes

import com.example.rickmorty.model.Character

sealed class EpisodeUiModel {
    class Item(val episode: Character.Episode) : EpisodeUiModel()
    class Header(val text: String) : EpisodeUiModel()
}