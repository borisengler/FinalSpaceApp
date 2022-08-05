package com.example.finalfinalspace.data.db.models

interface QuoteOrCharacter {

    val uid: String
    override fun equals(other: Any?): Boolean
    override fun hashCode(): Int
}
