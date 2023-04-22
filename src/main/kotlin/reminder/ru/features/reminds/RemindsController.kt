package reminder.ru.features.reminds

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import reminder.ru.database.reminds.Reminds
import reminder.ru.database.users.Users
import reminder.ru.features.login.LoginReceiveRemote
import reminder.ru.utils.TokenCheck
import reminder.ru.features.reminds.models.CreateRemindRequest

class RemindsController(private val call: ApplicationCall) {
     suspend fun createRemind() {
         val token = call.request.headers["Bearer-Autorization"]
         if(TokenCheck.isTokenValid(token.orEmpty())){
             val request = call.receive<CreateRemindRequest>()
             val remind = request.mapToRemindDTO()
             Reminds.insert(remind)
             call.respond(remind.mapToCreateRemindResponse())
         }
         else {
             call.respond(HttpStatusCode.Unauthorized, "Token expired")
         }
     }

    suspend fun performSearch() {
        val receive = call.receive<LoginReceiveRemote>()
        val userDTO= Users.fetchUser(receive.login)
        val token = call.request.headers["Bearer-Autorization"]
        if(TokenCheck.isTokenValid(token.orEmpty())){
            call.respond(Reminds.fetchReminds(userDTO?.login ?: "empty"))
        }
        else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }

    suspend fun updateRemind() {
        //TODO realize this function
    }
}