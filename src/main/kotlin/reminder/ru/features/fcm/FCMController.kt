package reminder.ru.features.fcm

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import reminder.ru.database.fcm_tokens.FCMTokenDTO
import reminder.ru.database.fcm_tokens.FCMTokens
import reminder.ru.database.reminds.Reminds
import reminder.ru.database.tokens.Tokens
import reminder.ru.utils.TokenCheck
import java.util.*

class FCMController(private val call: ApplicationCall) {

    suspend fun registerFCMToken() {
        val token = call.request.headers["Bearer-Authorization"]
        if (TokenCheck.isTokenValid(token.orEmpty())) {
            val login = findLoginByToken(token!!)
            val fcmTokenReceiveRemote = call.receive<FCMReceiveRemote>()

            FCMTokens.insert(
                FCMTokenDTO(
                    rowId = Random(System.currentTimeMillis()).nextInt(Integer.MAX_VALUE),
                    login = login!!,
                    fcmToken = fcmTokenReceiveRemote.fcmToken
                )
            )
            call.respond(FCMReceiveResponse(token = fcmTokenReceiveRemote.fcmToken))
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }

    suspend fun deleteAfterPush() {
        val token = call.request.headers["Bearer-Authorization"]
        if (TokenCheck.isTokenValid(token.orEmpty())) {
            val receiveRemote = call.receive<FCMReceiveRemind>()
            Reminds.delete(receiveRemote.id)
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
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
}