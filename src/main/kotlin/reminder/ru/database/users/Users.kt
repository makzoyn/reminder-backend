package reminder.ru.database.users

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Users : Table("users") {
    //определяем поля
    private val login = Users.varchar("login", 25)
    private val password = Users.varchar("password", 25)
    private val email = Users.varchar("email", 25)

    //определяем основные операции
    fun insert(userDTO: UserDTO) {
        transaction {
            Users.insert {
                it[login] = userDTO.login
                it[password] = userDTO.password
                it[email] = userDTO.email ?: ""
            }
        }
    }

    //получение пользователя
    fun fetchUser(login: String): UserDTO? {
        return try {
            transaction {
                val userModel = Users.select { Users.login.eq(login) }.single()
                UserDTO(
                    login = userModel[Users.login],
                    password = userModel[password],
                    email = userModel[email]
                )
            }
        }
        catch (e: Exception) {
            null
        }
    }
}