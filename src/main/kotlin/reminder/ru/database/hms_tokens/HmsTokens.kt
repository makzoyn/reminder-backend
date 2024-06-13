package reminder.ru.database.hms_tokens

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object HmsTokens : Table("fcm_devices") {
    private val rowId = HmsTokens.integer("id")
    val login = HmsTokens.varchar("login", 25)
    val hmsToken = HmsTokens.varchar("hms_token", 300)

    fun insert(tokenDTO: HMSTokenDTO) {
        transaction {
            val existingToken = HmsTokens.select { login eq tokenDTO.login }.singleOrNull()
            if (existingToken != null) {
                HmsTokens.update({ login eq tokenDTO.login }) {
                    it[hmsToken] = tokenDTO.hmsToken
                }
            } else {
                HmsTokens.insert {
                    it[rowId] = tokenDTO.rowId
                    it[login] = tokenDTO.login
                    it[hmsToken] = tokenDTO.hmsToken
                }
            }
        }
    }


}