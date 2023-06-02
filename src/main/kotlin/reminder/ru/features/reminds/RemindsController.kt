package reminder.ru.features.reminds

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import reminder.ru.database.reminds.Reminds
import reminder.ru.database.reminds.mapToCreateRemindResponse
import reminder.ru.database.reminds.mapToRemindDTO
import reminder.ru.database.reminds.mapToUprateRemindDTO
import reminder.ru.utils.TokenCheck
import reminder.ru.features.reminds.models.CreateRemindRequest
import reminder.ru.features.reminds.models.FetchRemindResponse
import reminder.ru.features.reminds.models.RemindResponse
import reminder.ru.features.reminds.models.UpdateRemindRequest

class RemindsController(private val call: ApplicationCall) {
    suspend fun createRemind() {
        val token = call.request.headers["Bearer-Authorization"]
        if (TokenCheck.isTokenValid(token.orEmpty())) {
            val request = call.receive<CreateRemindRequest>()
            val remind = request.mapToRemindDTO()
            Reminds.insert(remind)
            call.respond(remind.mapToCreateRemindResponse())
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }

    suspend fun deleteRemind() {
        val token = call.request.headers["Bearer-Authorization"]
        if (TokenCheck.isTokenValid(token.orEmpty())) {
            val id = call.parameters["id"]?.toInt()
            if (id != null) {
                Reminds.delete(id)
            }
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }

    suspend fun updateRemind() {
        val token = call.request.headers["Bearer-Authorization"]
        if (TokenCheck.isTokenValid(token.orEmpty())) {
            val id = call.parameters["id"]?.toInt()
            val request = call.receive<UpdateRemindRequest>()
            val remind = id?.let { request.mapToUprateRemindDTO(it) }
            if (remind != null) {
                Reminds.update(remind)
            }
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }

//    suspend fun performSearch() {
//        val request = call.receive<FetchRemindRequest>()
//
//        val token = call.request.headers["Bearer-Authorization"]
//        if (TokenCheck.isTokenValid(token.orEmpty())) {
//            call.respond(Reminds.fetchReminds().filter { it.login == request.login })
//        } else {
//            call.respond(HttpStatusCode.Unauthorized, "Token expired")
//        }
//    }

    suspend fun performSearch() {
        val token = call.request.headers["Bearer-Authorization"]
        val login = call.request.queryParameters["login"]
        if (TokenCheck.isTokenValid(token.orEmpty())) {
            if (login != null) {
                // Выполните необходимые операции с параметром `login`
                // Например, получите список напоминаний для данного `login`
                val reminds = Reminds.fetchReminds().filter { it.login == login }
                val remindResponses = reminds.map { it.mapToCreateRemindResponse() }

                call.respond(remindResponses)

            } else {
                call.respond(HttpStatusCode.BadRequest, "Missing login parameter")
            }
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }


    }


}