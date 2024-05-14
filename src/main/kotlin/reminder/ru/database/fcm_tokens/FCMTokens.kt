package reminder.ru.database.fcm_tokens

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object FCMTokens : Table("fcm_devices") {
    private val rowId = FCMTokens.integer("id")
    val login = FCMTokens.varchar("login", 25)
    val fcmToken = FCMTokens.varchar("fcm_token", 300)

    fun insert(tokenDTO: FCMTokenDTO) {
        transaction {
            val existingToken = FCMTokens.select { login eq tokenDTO.login }.singleOrNull()
            if (existingToken != null) {
                FCMTokens.update({ login eq tokenDTO.login }) {
                    it[fcmToken] = tokenDTO.fcmToken
                }
            } else {
                FCMTokens.insert {
                    it[rowId] = tokenDTO.rowId
                    it[login] = tokenDTO.login
                    it[fcmToken] = tokenDTO.fcmToken
                }
            }
        }
    }

    fun fetchFCMToken(fcmToken: String): FCMTokenDTO? {
        return try {
            transaction {
                val fcmModel = FCMTokens.select { FCMTokens.fcmToken.eq(fcmToken) }.single()
                FCMTokenDTO(
                    rowId = fcmModel[rowId],
                    login = fcmModel[login],
                    fcmToken = fcmModel[FCMTokens.fcmToken]
                )
            }
        }
        catch (e: Exception) {
            null
        }
    }

    fun fetchFCMTokens(): List<FCMTokenDTO> {
        return try {
            transaction {
                FCMTokens.selectAll().toList()
                    .map {
                        FCMTokenDTO(
                            rowId = it[rowId],
                            fcmToken = it[fcmToken],
                            login = it[login]
                        )
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

}