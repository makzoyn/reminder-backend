package reminder.ru.features.user.models

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserRequest(
    val login: String? = null,
    val email: String? = null,
    val password: String? = null
)

fun UpdateUserRequest.mapToUserUpdateModel() = UserUpdateModel(
    login = login,
    email = email,
    password = password
)