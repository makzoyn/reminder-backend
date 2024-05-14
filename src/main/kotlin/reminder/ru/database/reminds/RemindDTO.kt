package reminder.ru.database.reminds


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
    val alarmId: Int?,
    val login: String
)

fun CreateRemindRequest.mapToRemindDTO(login: String): RemindDTO = RemindDTO(
    id = Random(System.currentTimeMillis()).nextInt(Integer.MAX_VALUE),
    title = title,
    description = description,
    time = time,
    date = date,
    alarmId = Random(System.currentTimeMillis()).nextInt(Integer.MAX_VALUE),
    login = login
)

fun UpdateRemindRequest.mapToUprateRemindDTO(id: Int, login: String): RemindDTO = RemindDTO(
    id = id,
    title = title,
    description = description,
    time = time,
    date = date,
    alarmId = alarmId,
    login = login
)



fun RemindDTO.mapToCreateRemindResponse() : CreateRemindResponse =
    CreateRemindResponse(
    id = id,
    title = title,
    description = description,
    time = time,
    date = date
)

fun RemindDTO.mapToRemindResponse() : RemindResponse =
    RemindResponse(
        id = id,
        title = title,
        description = description,
        time = time,
        date = date
    )
