package de.mindmarket.ivyleemaster.util.presentation

import kotlinx.serialization.Serializable


object Destination {
    @Serializable
    data object Auth

    @Serializable
    data object Home

    @Serializable
    data object Login

    @Serializable
    data object Register

    @Serializable
    data object Task

    @Serializable
    data object Idea

    @Serializable
    data object Settings

    @Serializable
    data object AddIdea
}