package reminder.ru.features.user.models

data class UserUpdateModel(
    val login: String? = null,
    val email: String? = null,
    val password: String? = null
)