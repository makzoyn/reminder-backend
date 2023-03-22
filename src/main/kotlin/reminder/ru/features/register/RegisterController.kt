package reminder.ru.features.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import reminder.ru.cache.InMemoryCache
import reminder.ru.cache.TokenCache
import reminder.ru.database.users.Users
import java.util.*

class RegisterController(val call: ApplicationCall) {

    suspend fun registerNewUser(
        registerReceiveRemote: RegisterReceiveRemote
    ){
        val isUserExist = Users.fetchUser(registerReceiveRemote.login)
        if(!isUserExist){
            call.respond(HttpStatusCode.Conflict, "User already exists")
        }


        val token = UUID.randomUUID().toString()
        InMemoryCache.userList.add(receive)
        InMemoryCache.token.add(TokenCache(login = receive.login, token = token))

        call.respond(RegisterResponseRemote(token = token))
    }
}