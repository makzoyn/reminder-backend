package reminder.ru.pushreminds

import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import kotlinx.serialization.Serializable

@Serializable
data class SendMessageDTO(
    val to: String?,
    val notification: NotificationBody,
    val data: NotificationData
)

@Serializable
data class NotificationBody(
    val title: String,
    val body: String?
)

@Serializable
data class NotificationData(
    val id: Int
)

fun SendMessageDTO.toMessage(): Message {
    val messageBuilder = Message.builder()
        .setNotification(
            Notification.builder()
                .setTitle(notification.title)
                .setBody(notification.body)
                .build()
        )

    val dataMap = mutableMapOf<String, String>()
    dataMap["id"] = data.id.toString()
    messageBuilder.putAllData(dataMap)

    if (to == null) {
        messageBuilder.setTopic("all")
    } else {
        messageBuilder.setToken(to)
    }

    return messageBuilder.build()
}
