package reminder.ru.features.reminds

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import reminder.ru.database.fcm_tokens.FCMTokens
import reminder.ru.database.reminds.*
import reminder.ru.database.tokens.Tokens
import reminder.ru.features.reminds.models.*
import reminder.ru.models.ErrorModel
import reminder.ru.models.PushNotificationModel
import reminder.ru.pushreminds.sendNotification
import reminder.ru.utils.TokenCheck
import java.time.Duration
import java.time.LocalDateTime

class RemindsController(private val call: ApplicationCall) {
    suspend fun createRemind() {
        val token = call.request.headers["Bearer-Authorization"]
        if (TokenCheck.isTokenValid(token.orEmpty())) {
            val request = call.receive<CreateRemindRequest>()
            val login = findLoginByToken(token!!)
            val remind = request.mapToRemindDTO(login!!)
            Reminds.insert(remind)
            call.respond(remind.mapToCreateRemindResponse())
            val fcmToken = findFcmTokenByLogin(login)
            scheduleNotification(
                PushNotificationModel(
                    fcmToken = fcmToken,
                    title = remind.title,
                    description = remind.description,
                    id = remind.id,
                    time = remind.time,
                    date = remind.date
                )
            )
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }

    private fun scheduleNotification(note: PushNotificationModel) {
        val notificationTime = combineDateTime(date = note.date!!, time = note.time!!)
        val currentTime = LocalDateTime.now()
        val delaySeconds = Duration.between(currentTime, notificationTime).toSeconds()
        CoroutineScope(Dispatchers.IO).launch {
            delay(delaySeconds * 1000)
            sendNotification(note)
        }
    }

    private fun combineDateTime(date: String, time: String): LocalDateTime {
        val combinedDateTime = try {

            val dateParts = date.split(".")
            val timeParts = time.split(":")


            val year = dateParts[2].toInt()
            val month = dateParts[1].toInt()
            val day = dateParts[0].toInt()
            val hour = timeParts[0].toInt()
            val minute = timeParts[1].toInt()
            val second = timeParts[2].toInt()

            val dateTime = LocalDateTime.of(year, month, day, hour, minute, second)
            dateTime

        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
        return combinedDateTime

    }

    private fun findFcmTokenByLogin(login: String): String {
        return try {
            transaction {
                FCMTokens.select { FCMTokens.login eq login }
                    .map { it[FCMTokens.fcmToken] }
                    .firstOrNull() ?: ""
            }
        } catch (e: Exception) {
            ""
        }
    }


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

    suspend fun deleteRemind() {
        val token = call.request.headers["Bearer-Authorization"]
        if (TokenCheck.isTokenValid(token.orEmpty())) {
            val request = call.receive<DeleteRemindsRequest>()
            request.ids.map {
                Reminds.delete(it)
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
            val remind = id?.let {
                request.mapToUprateRemindDTO(it)
            }
            if (remind != null) {
                Reminds.update(remind)
            }
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.Unauthorized, ErrorModel("Token expired", "Need to authorization"))
        }
    }

    suspend fun getNotifiedReminds() {
        val token = call.request.headers["Bearer-Authorization"]
        if (TokenCheck.isTokenValid(token.orEmpty())) {
            val login = findLoginByToken(token!!)
            val reminds = Reminds.fetchReminds().filter { it.login == login }.filter { it.needToNotified }
            val remindResponses = reminds.map { it.mapToRemindResponse() }.toFetchRemindResponse()
            call.respond(remindResponses)
        } else {
            call.respond(HttpStatusCode.Unauthorized, ErrorModel("Token expired", "Need to authorization"))
        }
    }

    suspend fun getNotifiedRemindsByTitle() {
        val token = call.request.headers["Bearer-Authorization"]
        if (TokenCheck.isTokenValid(token.orEmpty())) {
            val login = findLoginByToken(token!!)
            val titleQuery = call.request.queryParameters["title"]

            if (titleQuery != null) {
                val reminds = Reminds.fetchReminds()
                    .filter { it.login == login }
                    .filter { it.needToNotified }
                    .filter { it.title.contains(titleQuery, ignoreCase = true) }

                val remindResponses = reminds.map { it.mapToRemindResponse() }.toFetchRemindResponse()
                call.respond(remindResponses)
            } else {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorModel("Missing title query parameter", "Please provide a title query parameter")
                )
            }
        } else {
            call.respond(HttpStatusCode.Unauthorized, ErrorModel("Token expired", "Need to authorization"))
        }
    }

    suspend fun getNotesByTitle() {
        val token = call.request.headers["Bearer-Authorization"]
        if (TokenCheck.isTokenValid(token.orEmpty())) {
            val login = findLoginByToken(token!!)
            val titleQuery = call.request.queryParameters["title"]

            if (titleQuery != null) {
                val reminds = Reminds.fetchReminds()
                    .filter { it.login == login }
                    .filter { it.needToNotified.not() }
                    .filter { it.title.contains(titleQuery, ignoreCase = true) }

                val remindResponses = reminds.map { it.mapToRemindResponse() }.toFetchRemindResponse()
                call.respond(remindResponses)
            } else {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorModel("Missing title query parameter", "Please provide a title query parameter")
                )
            }
        } else {
            call.respond(HttpStatusCode.Unauthorized, ErrorModel("Token expired", "Need to authorization"))
        }
    }

    suspend fun getNotes() {
        val token = call.request.headers["Bearer-Authorization"]
        if (TokenCheck.isTokenValid(token.orEmpty())) {
            val login = findLoginByToken(token!!)
            val reminds = Reminds.fetchReminds().filter { it.login == login }.filter { it.needToNotified.not() }
            val remindResponses = reminds.map { it.mapToRemindResponse() }.toFetchRemindResponse()
            call.respond(remindResponses)
        } else {
            call.respond(HttpStatusCode.Unauthorized, ErrorModel("Token expired", "Need to authorization"))
        }
    }

    suspend fun getRemindById() {
        val token = call.request.headers["Bearer-Authorization"]
        if (TokenCheck.isTokenValid(token.orEmpty())) {
            val login = findLoginByToken(token!!)
            val id = call.parameters["id"]?.toInt()
            val remind = Reminds.fetchReminds().filter { it.login == login }
                .firstOrNull { it.id == id }?.mapToRemindResponse()
            if (remind != null) {
                call.respond(HttpStatusCode.OK, remind)
            } else {
                call.respond(HttpStatusCode.OK)
            }
        } else {
            call.respond(HttpStatusCode.Unauthorized, ErrorModel("Token expired", "Need to authorization"))
        }
    }

}