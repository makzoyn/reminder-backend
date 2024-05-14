package reminder.ru.features.reminds.models

import kotlinx.serialization.Serializable

@Serializable
data class UpdateRemindRequest(
    val title: String,
    val description: String,
    val time: String,
    val date: String,
    val alarmId: Int
)
