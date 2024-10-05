package de.mindmarket.ivyleemaster.core.presentation.navigator

import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object AuthGraph: Destination

    @Serializable
    data object HomeGraph: Destination

    @Serializable
    data object Login: Destination

    @Serializable
    data object Register: Destination

    @Serializable
    data object Task: Destination

    @Serializable
    data object Idea: Destination

    @Serializable
    data object AddIdea: Destination

    @Serializable
    data object Settings: Destination
}