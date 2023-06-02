package reminder.ru.database.reminds


import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.idParam
import reminder.ru.features.reminds.models.*
import java.util.*

@Serializable
class RemindDTO(
    val id: Int,
    val title: String,
    val description: String,
    val time: String,
    val date: String,
    val alarmId: Int,
    val login: String
)

fun CreateRemindRequest.mapToRemindDTO(): RemindDTO = RemindDTO(
    id = Random(System.currentTimeMillis()).nextInt(),
    title = title,
    description = description,
    time = time,
    date = date,
    alarmId = alarmId,
    login = login
)

fun UpdateRemindRequest.mapToUprateRemindDTO(id: Int): RemindDTO = RemindDTO(
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
    date = date,
    alarmId = alarmId
)

fun RemindDTO.mapToRemindResponse() : RemindResponse =
    RemindResponse(
        id = id,
        title = title,
        description = description,
        time = time,
        date = date,
        alarmId = alarmId
    )