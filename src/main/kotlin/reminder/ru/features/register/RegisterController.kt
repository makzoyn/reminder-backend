package reminder.ru.features.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import reminder.ru.database.tokens.TokenDTO
import reminder.ru.database.tokens.Tokens
import reminder.ru.database.users.UserDTO
import reminder.ru.database.users.Users
import reminder.ru.utils.isValidEmail
import java.util.*

class RegisterController(private val call: ApplicationCall) {

    //ассинхронная функция регистрации
    suspend fun registerNewUser() {
        val registerReceiveRemote = call.receive<RegisterReceiveRemote>()
        if (!registerReceiveRemote.email.isValidEmail()) {
            call.respond(HttpStatusCode.BadRequest, "Email is not valid")
        }
        //проверяем есть ли такой юзер
        val userDTO = Users.fetchUser(registerReceiveRemote.login)
        //если юзер существует, то отправляем статус код, что юзер существует
        if (userDTO != null) {
            call.respond(HttpStatusCode.Conflict, "User already exists")
        } else {
            //если нет, то генерируем токен, вставляем пользователя в бд
            val token = UUID.randomUUID().toString()

            try {

            } catch (e: ExposedSQLException) {
                call.respond(HttpStatusCode.Conflict, "User already exists")
            }
            Users.insert(
                UserDTO(
                    login = registerReceiveRemote.login,
                    password = registerReceiveRemote.password,
                    email = registerReceiveRemote.email
                )
            )
            //вставляем запись токена в бд токенов
            Tokens.insert(
                TokenDTO(
                    rowId = UUID.randomUUID().toString(),
                    login = registerReceiveRemote.login,
                    token = token
                )
            )
            //возвращаем респонд, что логин произошел
            call.respond(RegisterResponseRemote(token = token))
        }

    }
}