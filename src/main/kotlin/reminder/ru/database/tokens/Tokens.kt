package reminder.ru.database.tokens

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Tokens : Table("tokens") {
    private val id = Tokens.varchar("id", 50)
    val login = Tokens.varchar("login", 25)
    val token = Tokens.varchar("token", 50)

    fun insert(tokenDTO: TokenDTO) {
        transaction {
            val existingToken = Tokens.select { login eq tokenDTO.login }.singleOrNull()
            if (existingToken != null) {
                Tokens.update({ login eq tokenDTO.login }) {
                    it[token] = tokenDTO.token
                }
            } else {
                Tokens.insert {
                    it[id] = tokenDTO.rowId
                    it[login] = tokenDTO.login
                    it[token] = tokenDTO.token
                }
            }
        }
    }

    fun fetchTokens(): List<TokenDTO> {
        return try {
            transaction {
                Tokens.selectAll().toList()
                    .map {
                        TokenDTO(
                            rowId = it[Tokens.id],
                            token = it[token],
                            login = it[login]
                        )
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

}