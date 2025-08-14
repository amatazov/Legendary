package com.amatazov.legendary.data.repository

class ChatGptRepository {

    private val key =
        ""

    suspend fun completeChat(userPrompt: String): String {
//        val response: ChatCompletionResponse =
//            client.get("https://api.openai.com/v1/responses") {
//                header(HttpHeaders.Authorization, "Bearer $key")
//                contentType(ContentType.Application.Json)
//                setBody(
//                    ChatRequestNew(
//                        model = "gpt-5",
//                        input = userPrompt,
//                        store = true
//                    )
//                )
//            }
//                .body()

        return "Съешь еще этих мягких французских булочек"
    }
}