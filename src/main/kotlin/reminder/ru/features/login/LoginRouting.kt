package reminder.ru.features.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*
import reminder.ru.cache.InMemoryCache
import reminder.ru.cache.TokenCache

fun Application.configureLoginRouting() {
    routing {
        post("/login") {
            val receive = call.receive<LoginReceiveRemote>()
            val first = InMemoryCache.userList.firstOrNull { it.login == receive.login}

            if (first == null){
                call.respond(HttpStatusCode.BadRequest, "User not found")
            }
            else{
                if (first.password == receive.password){
                    if(InMemoryCache.userList.map { it.login }.contains(receive.login)){
                        val token = UUID.randomUUID().toString()
                        InMemoryCache.token.add(TokenCache(login = receive.login, token = token))
                        call.respond(LoginResponseRemote(token = token))
                        return@post
                    }
                }
                else
                {
                    call.respond(HttpStatusCode.BadRequest, "Invalid password")
                }
            }



            call.respond(HttpStatusCode.BadRequest)
        }
    }
}
