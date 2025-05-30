package de.syntax_institut.androidabschlussprojekt.data.remote.model

data class DreamAnalyzeRequest (
    val messages: List<UserMessage>,
    val model: String
)

data class UserMessage(
    val role: String,
    val content: String
)
