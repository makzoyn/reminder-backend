package reminder.ru.features.reminds.models

import kotlinx.serialization.Serializable

@Serializable
data class CreateRemindRequest(
    val id: Int
)

@Serializable
data class CreateRemindResponse(
    val id: Int,
    val title: String,
    val description: String,
    val time: String,
    val date: String
)