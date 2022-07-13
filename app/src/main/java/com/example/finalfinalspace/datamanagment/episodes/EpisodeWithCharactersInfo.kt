package com.example.finalfinalspace.datamanagment.episodes

import com.example.finalfinalspace.datamanagment.characters.CharactersInfo

data class EpisodeWithCharactersInfo(
    val id: Int = 0,
    val name: String,
    val airDate: String,
    val director: String,
    val writer: String,
    val imageUrl: String,
    val characters: List<CharactersInfo>
)

