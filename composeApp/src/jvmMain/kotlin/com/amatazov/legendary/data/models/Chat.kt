package com.amatazov.legendary.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatMessage(
    val role: String,
    val content: String
)

@Serializable
data class ChatChoice(
    val index: Int,
    val message: ChatMessage,
    @SerialName("finish_reason")
    val finishReason: String? = null
)

@Serializable
data class ChatCompletionResponse(
    val id: String? = null,
    val type: String? = null,
    val role: String? = null,
    val content: String? = null
) {
    @Serializable
    data class Content(
        val type: String? = null,
        val text: String? = null
    )
}

@Serializable
data class ChatRequest(
    val model: String,
    val messages: List<ChatMessage>,
    val temperature: Double = 0.7
)

@Serializable
data class ChatRequestNew(
    val model: String,
    val input: String,
    val store: Boolean
)