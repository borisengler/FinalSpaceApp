package com.example.finalfinalspace.data.db.models

interface QuoteOrCharacter {

    val uid: String
    val type: Int
    override fun equals(other: Any?): Boolean
    override fun hashCode(): Int
}
