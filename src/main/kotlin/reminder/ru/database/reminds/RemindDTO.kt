package reminder.ru.database.reminds



import reminder.ru.features.reminds.models.CreateRemindRequest

class RemindDTO (
    val id: Int,
    val login: String,
    val title: String,
    val description: String,
    val time: String,
    val date: String
)

fun CreateRemindRequest.mapToRemindDTO(): RemindDTO = RemindDTO(id = id)