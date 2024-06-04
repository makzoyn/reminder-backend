package reminder.ru.features.reminds.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateRemindRequest(
    val title: String,
    val description: String? = null,
    val time: String? = null,
    val date: String? = null,
    @SerialName("need_to_notified") val needToNotified: Boolean
)

@Serializable
data class CreateRemindResponse(
    val id: Int,
    val title: String,
    val description: String?,
    val time: String?,
    val date: String?,
    val notified: Boolean,
    @SerialName("need_to_notified") val needToNotified: Boolean
)

@Serializable
data class DeleteRemindsRequest(
    val ids: List<Int>
)
