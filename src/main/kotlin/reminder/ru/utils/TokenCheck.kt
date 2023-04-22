package reminder.ru.utils

import reminder.ru.database.tokens.Tokens


object TokenCheck {

    fun isTokenValid(token: String): Boolean = Tokens.fetchTokens().firstOrNull { it.token == token } != null

}