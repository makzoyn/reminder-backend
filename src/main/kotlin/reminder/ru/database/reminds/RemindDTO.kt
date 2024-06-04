package reminder.ru.database.reminds


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import reminder.ru.features.reminds.models.CreateRemindRequest
import reminder.ru.features.reminds.models.CreateRemindResponse
import reminder.ru.features.reminds.models.RemindResponse
import reminder.ru.features.reminds.models.UpdateRemindRequest
import java.util.*

@Serializable
class RemindDTO(
    val id: Int,
    val title: String,
    val description: String?,
    val time: String?,
    val date: String?,
    val notified: Boolean = false,
    val login: String,
    val needToNotified: Boolean
)

@Serializable
class UpdateRemindDTO(
    val id: Int,
    val title: String,
    val description: String?,
    val time: String?,
    val date: String?,
    @SerialName("need_to_notified") val needToNotified: Boolean
)

fun CreateRemindRequest.mapToRemindDTO(login: String): RemindDTO = RemindDTO(
    id = Random(System.currentTimeMillis()).nextInt(Integer.MAX_VALUE),
    title = title,
    description = description,
    time = time,
    date = date,
    notified = false,
    login = login,
    needToNotified = needToNotified
)

fun UpdateRemindRequest.mapToUprateRemindDTO(id: Int): UpdateRemindDTO = UpdateRemindDTO(
    id = id,
    title = title,
    description = description,
    time = time,
    date = date,
    needToNotified = needToNotified
)


fun RemindDTO.mapToCreateRemindResponse(): CreateRemindResponse =
    CreateRemindResponse(
        id = id,
        title = title,
        description = description,
        time = time,
        date = date,
        notified = notified,
        needToNotified = needToNotified
    )

fun RemindDTO.mapToRemindResponse(): RemindResponse =
    RemindResponse(
        id = id,
        title = title,
        description = description,
        time = time,
        date = date,
        notified = notified,
        needToNotified = needToNotified
    )
