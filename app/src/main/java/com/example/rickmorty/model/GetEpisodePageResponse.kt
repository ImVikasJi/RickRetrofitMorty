package com.example.rickmorty.model

data class GetEpisodePageResponse(
    val info: PageInfo = PageInfo(),
    val results: List<GetEpisodeByIdResponse> = emptyList()
)