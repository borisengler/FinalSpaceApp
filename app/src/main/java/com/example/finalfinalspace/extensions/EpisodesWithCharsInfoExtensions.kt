package com.example.finalfinalspace.extensions

import com.example.finalfinalspace.data.api.models.EpisodesWithCharsInfo
import com.example.finalfinalspace.data.db.models.EpisodesInfo

fun EpisodesWithCharsInfo.toDatabase() = EpisodesInfo(
    id,
    name,
    airDate,
    director,
    writer,
    imageUrl,
)
