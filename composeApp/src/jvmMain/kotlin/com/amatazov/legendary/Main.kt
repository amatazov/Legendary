package com.amatazov.legendary

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.amatazov.legendary.data.KtorClient
import com.amatazov.legendary.ui.App

fun main() = application {
    Window(
        onCloseRequest = {
            KtorClient.client.close()
            exitApplication()
        },
        title = "Legendary",
    ) {
        App()
    }
}