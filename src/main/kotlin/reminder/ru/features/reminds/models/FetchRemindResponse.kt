package reminder.ru.features.reminds.models

import kotlinx.serialization.SerialName
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
    val date: String?,
    val notified: Boolean,
    @SerialName("need_to_notified") val needToNotified: Boolean
)

fun List<RemindResponse>.toFetchRemindResponse() = FetchRemindResponse(
    reminds = this
)