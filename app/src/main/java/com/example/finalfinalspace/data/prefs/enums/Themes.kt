package com.example.finalfinalspace.data.prefs.enums

enum class Themes(val value: String) {
    SYSTEM_DEFAULT("1"),
    LIGHT("2"),
    DARK("3");

    companion object {
        fun fromString(value: String) = Themes.values().first { it.value == value }
        fun toString(value: Themes): String {
            return when (value) {
                SYSTEM_DEFAULT -> "1"
                LIGHT -> "2"
                else -> "3"
            }
        }
    }
}
