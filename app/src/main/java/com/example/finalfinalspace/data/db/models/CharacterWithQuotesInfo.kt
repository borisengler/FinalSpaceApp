package com.example.finalfinalspace.data.db.models

import androidx.room.Embedded
import androidx.room.Relation

data class CharacterWithQuotesInfo(
    @Embedded val character: CharactersInfo,
    @Relation(
        parentColumn = "name",
        entityColumn = "by_who"
    )
    val quotes: List<QuotesInfo>
)
