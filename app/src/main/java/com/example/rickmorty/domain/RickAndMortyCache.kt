package com.example.rickmorty.domain

import com.example.rickmorty.model.Character

object RickAndMortyCache {
    val characterMap = mutableMapOf<Int, Character>()
}