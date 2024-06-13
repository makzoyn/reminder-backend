package reminder.ru.features.user

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import reminder.ru.database.tokens.Tokens
import reminder.ru.database.users.Users
import reminder.ru.features.user.models.UpdateUserRequest
import reminder.ru.features.user.models.mapToUserResponse
import reminder.ru.features.user.models.mapToUserUpdateModel
import reminder.ru.models.ErrorModel
import reminder.ru.utils.TokenCheck

class UserController(private val call: ApplicationCall) {

    private fun findLoginByToken(token: String): String? {
        return try {
            transaction {
                Tokens.select { Tokens.token eq token }
                    .map { it[Tokens.login] }
                    .firstOrNull()
            }
        } catch (e: Exception) {
            null
        }
    }


    suspend fun updateUser() {
        val token = call.request.headers["Bearer-Authorization"]
        if (TokenCheck.isTokenValid(token.orEmpty())) {
            val id = call.parameters["id"]?.toInt()
            val request = call.receive<UpdateUserRequest>()
            val userUpdate = request.mapToUserUpdateModel()
            id?.let {
                Users.update(userUpdate, it)
            }
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.Unauthorized, ErrorModel("Token expired", "Need to authorization"))
        }
    }

    suspend fun getUser() {
        val token = call.request.headers["Bearer-Authorization"]
        if (TokenCheck.isTokenValid(token.orEmpty())) {
            val login = findLoginByToken(token!!)
                ?: return call.respond(
                    HttpStatusCode.NotFound,
                    ErrorModel("User Not Found", "User Not Found")
                )
            val user = Users.fetchUser(login)?.mapToUserResponse()
            if (user != null)
                call.respond(HttpStatusCode.OK, user)
            else
                call.respond(
                    HttpStatusCode.NotFound,
                    ErrorModel("User Not Found", "User Not Found")
                )
        } else {
            call.respond(HttpStatusCode.Unauthorized, ErrorModel("Token expired", "Need to authorization"))
        }
    }
}