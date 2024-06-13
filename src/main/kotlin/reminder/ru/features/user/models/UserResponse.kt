package reminder.ru.features.user.models

import kotlinx.serialization.Serializable
import reminder.ru.database.users.UserDTO

@Serializable
data class UserResponse(
    val login: String,
    val email: String?,
)

fun UserDTO.mapToUserResponse() = UserResponse(
    login = login,
    email = email
)