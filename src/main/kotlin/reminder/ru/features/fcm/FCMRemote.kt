package reminder.ru.features.fcm

import kotlinx.serialization.Serializable

@Serializable
data class FCMReceiveRemote(
    val fcmToken: String
)

@Serializable
data class FCMReceiveResponse(
    val token: String
)

@Serializable
data class FCMReceiveRemind(
    val id: Int
)