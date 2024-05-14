package reminder.ru.models


data class PushNotificationModel(
    val fcmToken: String,
    val title: String,
    val description: String?,
    val id: Int,
    val time: String?,
    val date: String?
)
