package com.amatazov.legendary.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amatazov.legendary.common.ChatMessageBackground
import com.amatazov.legendary.common.ChatMessageBackgroundMine
import com.amatazov.legendary.ui.models.chat.ChatMessage
import com.amatazov.legendary.ui.models.chat.ChatState
import com.amatazov.legendary.ui.models.chat.InputState
import kotlinx.collections.immutable.ImmutableList

@Composable
fun ChatScreen(
    vm: ChatViewModel = viewModel()
) {
    val state = vm.uiStateObservable.collectAsState(ChatState())

    LaunchedEffect(Unit) {
        vm.init()
    }

    Content(
        state = state.value,
        onInputChanged = { text ->
            vm.onInputChanged(text)
        },
        onSend = {
            vm.onSend()
        }
    )
}

@Composable
private fun Content(
    state: ChatState,
    onInputChanged: (text: String) -> Unit,
    onSend: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        MessageListUi(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            chatItems = state.messages
        )
        InputUi(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            state = state.input,
            onInputChanged = onInputChanged,
            onSend = onSend
        )
    }
}

@Composable
private fun MessageListUi(
    modifier: Modifier = Modifier,
    chatItems: ImmutableList<ChatMessage>
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        this.items(
            items = chatItems,
            key = { item -> item.id },
        ) { item ->
            ChatMessageUi(item = item)
        }

    }
}

@Composable
private fun ChatMessageUi(
    modifier: Modifier = Modifier,
    item: ChatMessage
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(
                    start = if (item.isMine) 32.dp else 0.dp,
                    end = if (!item.isMine) 32.dp else 0.dp,
                )
                .background(
                    color = if (item.isMine) {
                        ChatMessageBackgroundMine
                    } else {
                        ChatMessageBackground
                    },
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .wrapContentSize()
                .align(if (item.isMine) Alignment.End else Alignment.Start)
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    ),
                text = item.text
            )
        }
    }
}

@Composable
private fun InputUi(
    modifier: Modifier = Modifier,
    state: InputState,
    onInputChanged: (text: String) -> Unit,
    onSend: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        if (state.isThinking) {
            Text(
                modifier = Modifier,
                text = state.textThinkingHint
            )
        }
        val text = rememberSaveable { mutableStateOf(state.text) }
        TextField(
            modifier = Modifier
                .height(100.dp)
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            value = text.value,
            onValueChange = {
                text.value = it
                onInputChanged(it)
            }
        )
        Button(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterHorizontally),
            onClick = onSend
        ) {
            Text(
                modifier = Modifier,
                text = state.textSendButton
            )
        }
    }
}