package ru.alex.panov.data.model

data class Session(
    val id: String,
    val speaker: String,
    val date: String,
    val timeInterval: String,
    val description: String,
    val imageUrl: String
)