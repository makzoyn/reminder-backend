package reminder.ru.features.reminds.models

import kotlinx.serialization.Serializable

@Serializable
data class FetchRemindResponse(
    val reminds: List<RemindResponse>
)

@Serializable
data class RemindResponse(
    val id: Int,
    val title: String,
    val description: String?,
    val time: String?,
    val date: String?
)

fun List<RemindResponse>.toFetchRemindResponse() = FetchRemindResponse(
    reminds = this
)