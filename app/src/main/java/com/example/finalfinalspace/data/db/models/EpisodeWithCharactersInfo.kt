package com.example.finalfinalspace.data.db.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class EpisodeWithCharactersInfo(
    @Embedded val episode: EpisodesInfo,
    @Relation(
        parentColumn = "episodeId",
        entityColumn = "characterId",
        associateBy = Junction(CharInEpiInfo::class)
    )
    val characters: List<CharactersInfo>
)
