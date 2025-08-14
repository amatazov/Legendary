package com.amatazov.legendary.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amatazov.legendary.data.repository.ChatGptRepository
import com.amatazov.legendary.ui.models.chat.ChatMessage
import com.amatazov.legendary.ui.models.chat.ChatState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.util.UUID

class ChatViewModel() : ViewModel() {

    val chatGptRepository: ChatGptRepository = ChatGptRepository()

    private val uiStateSubject: MutableStateFlow<ChatState> = MutableStateFlow(ChatState())
    val uiStateObservable: Flow<ChatState>
        get() = uiStateSubject.distinctUntilChanged { old, new ->
            old == new
        }

    private val currentState get() = uiStateSubject.value

    fun init() {

    }

    fun onInputChanged(text: String) {
        viewModelScope.launch {
            uiStateSubject.emit(currentState.changeInput(text))
        }
    }

    fun onSend() {
        viewModelScope.launch {
            uiStateSubject.emit(currentState.changeThinking(isThinking = true))
            uiStateSubject.emit(currentState.addChatGptMessage(
                text = currentState.input.text,
                isMine = true
            ))
            try {
                val res = chatGptRepository.completeChat(currentState.input.text)
                uiStateSubject.emit(
                    currentState.addChatGptMessage(
                        text = res,
                        isMine = false
                    )
                )
                uiStateSubject.emit(currentState.changeThinking(isThinking = false))
            } catch (ex: Exception) {
                val message = ex.message
            }
        }
    }

    private fun getMessages(count: Int): List<ChatMessage> {
        val list = mutableListOf<ChatMessage>()
        repeat(count) {
            list.add(getChatMessage())
        }
        return list
    }

    private fun getChatMessage(): ChatMessage {
        return ChatMessage(
            id = UUID.randomUUID().toString(),
            text = "Расскажи какой-нибудь рандомный факт, " +
                    "Расскажи какой-нибудь рандомный факт, " +
                    "Расскажи какой-нибудь рандомный факт, " +
                    "Расскажи какой-нибудь рандомный факт, ",
            isMine = true
        )
    }

}