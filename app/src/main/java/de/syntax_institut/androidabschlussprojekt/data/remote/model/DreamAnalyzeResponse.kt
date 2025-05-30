package de.syntax_institut.androidabschlussprojekt.data.remote.model

data class DreamAnalyzeResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: GPTMessage
)


data class GPTMessage(
    val role: String,
    val content: String
)
