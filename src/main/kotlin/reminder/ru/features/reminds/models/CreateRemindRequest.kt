package reminder.ru.features.reminds.models

import kotlinx.serialization.Serializable

@Serializable
data class CreateRemindRequest(
    val title: String,
    val description: String,
    val time: String,
    val date: String,
    val login: String
)

@Serializable
data class CreateRemindResponse(
    val id: String,
    val title: String,
    val description: String,
    val time: String,
    val date: String,
    val alarmId: String
)