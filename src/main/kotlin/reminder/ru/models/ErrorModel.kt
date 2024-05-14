package reminder.ru.models

import kotlinx.serialization.Serializable

@Serializable
data class ErrorModel(
    val title: String,
    val message: String
)
