package com.example.finalfinalspace.datamanagment.episodes

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.finalfinalspace.datamanagment.charInEpi.CharInEpiInfo
import com.example.finalfinalspace.datamanagment.characters.CharactersInfo

data class EpisodeWithCharactersInfo(
    @Embedded val episode: EpisodesInfo,
    @Relation(
        parentColumn = "episodeId",
        entityColumn = "characterId",
        associateBy = Junction(CharInEpiInfo::class)
    )
    val characters: List<CharactersInfo>
)
