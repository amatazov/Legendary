package com.amatazov.legendary.ui.models.chat

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import java.util.UUID

data class ChatState(
    val messages: ImmutableList<ChatMessage> = persistentListOf(),
    val input: InputState = InputState()
) {
    fun changeInput(text: String) = copy(
        input = input.copy(
            text = text
        )
    )

    fun changeThinking(isThinking: Boolean) = copy(
        input = input.copy(
            isThinking = isThinking
        )
    )

    fun addChatGptMessage(text: String, isMine: Boolean) = copy(
        messages = this.messages.toMutableList().apply {
            add(
                ChatMessage(
                    id = UUID.randomUUID().toString(),
                    text = text,
                    isMine = isMine
                )
            )
        }.toImmutableList()
    )
}

data class ChatMessage(
    val id: String,
    val text: String,
    val isMine: Boolean
)

data class InputState(
    val text: String = "",
    val isThinking: Boolean = false,
    val textThinkingHint: String = "Тихо, нейронка думает...",
    val textSendButton: String = "Отправить",
)