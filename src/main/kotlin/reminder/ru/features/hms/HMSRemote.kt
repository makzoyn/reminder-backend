package reminder.ru.features.hms

import kotlinx.serialization.Serializable

@Serializable
data class HMSReceiveRemote(
    val hmsToken: String
)

@Serializable
data class HMSReceiveResponse(
    val token: String
)

@Serializable
data class HMSReceiveRemind(
    val id: Int
)