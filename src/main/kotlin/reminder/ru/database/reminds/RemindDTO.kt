package reminder.ru.database.reminds


import kotlinx.serialization.Serializable
import reminder.ru.features.reminds.models.CreateRemindRequest
import reminder.ru.features.reminds.models.CreateRemindResponse
import reminder.ru.features.reminds.models.UpdateRemindRequest
import java.util.*

@Serializable
class RemindDTO(
    val id: String,
    val title: String,
    val description: String,
    val time: String,
    val date: String,
    val alarmId: String,
    val login: String
)

fun CreateRemindRequest.mapToRemindDTO(): RemindDTO = RemindDTO(
    id = UUID.randomUUID().toString(),
    title = title,
    description = description,
    time = time,
    date = date,
    alarmId = UUID.randomUUID().toString(),
    login = login
)

fun UpdateRemindRequest.mapToUprateRemindDTO(id: String): RemindDTO = RemindDTO(
    id = id,
    title = title,
    description = description,
    time = time,
    date = date,
    alarmId = UUID.randomUUID().toString(),
    login = login
)


fun RemindDTO.mapToCreateRemindResponse() : CreateRemindResponse =
    CreateRemindResponse(
    id = id,
    title = title,
    description = description,
    time = time,
    date = date,
    alarmId = alarmId
)