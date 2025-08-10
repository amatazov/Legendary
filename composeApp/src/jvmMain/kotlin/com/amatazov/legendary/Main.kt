package com.amatazov.legendary

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.amatazov.legendary.ui.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Legendary",
    ) {
        App()
    }
}