package com.example.rickmorty.model

data class Character(
    val episodeList: List<Episode> = listOf(),
    val gender: String = "",
    val id: Int = 0,
    val image: String = "",
    val location: Location = Location(),
    val name: String = "",
    val origin: Origin = Origin(),
    val species: String = "",
    val status: String = "",
) {
    data class Location(
        val name: String = "",
        val url: String = ""
    )

    data class Origin(
        val name: String = "",
        val url: String = ""
    )

    data class Episode(
        val id: Int = 0,
        val name: String = "",
        val airDate: String = "",
        val seasonNumber : Int = 0,
        val episodeNumber: Int = 0,
        val characters: List<Character> = emptyList()
    ){
        fun getFormattedSeason(): String {
            return "Season $seasonNumber Episode $episodeNumber"
        }

        fun getFormattedSeasonTruncated(): String {
            return "S.$seasonNumber E.$episodeNumber"
        }
    }
}
