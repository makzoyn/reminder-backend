package reminder.ru.features.reminds.models

import kotlinx.serialization.Serializable

@Serializable
data class FetchRemindRequest (
    val token: String
)