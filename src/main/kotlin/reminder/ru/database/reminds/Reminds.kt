package reminder.ru.database.reminds

import io.ktor.http.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import reminder.ru.database.users.UserDTO
import reminder.ru.database.users.Users


object Reminds : Table("reminds") {
    private val id = Reminds.varchar("id", 50)
    private var title = Reminds.varchar("title", 50)
    private var description = Reminds.varchar("description", 300)
    private var time = Reminds.varchar("time", 10)
    private var date = Reminds.varchar("date", 10)
    private var alarmId = Reminds.varchar("alarm_id", 50)
    private val login = Reminds.varchar("login", 25)

    fun insert(remindDTO: RemindDTO) {
        transaction {
            Reminds.insert {
                it[id] = remindDTO.id
                it[title] = remindDTO.title
                it[description] = remindDTO.description
                it[time] = remindDTO.time
                it[date] = remindDTO.date
                it[alarmId] = remindDTO.alarmId
                it[login] = remindDTO.login
            }
        }
    }

    //TODO realize this function right, now it's just fetch one remind
    fun update(remindDTO: RemindDTO) {
        transaction {
            Reminds.update({ Reminds.id eq remindDTO.id }) {
                it[title] = remindDTO.title
                it[description] = remindDTO.description
                it[time] = remindDTO.time
                it[date] = remindDTO.date
            }
        }
    }

    fun delete(id: String) {
        transaction {
            Reminds.deleteWhere { Reminds.id eq id }
        }
    }


    fun fetchReminds(): List<RemindDTO> {
        return try {
            transaction {
                Reminds.selectAll().toList().map{
                    RemindDTO(
                        id = it[Reminds.id],
                        title = it[title],
                        description = it[description],
                        time = it[time],
                        date = it[date],
                        alarmId = it[alarmId],
                        login = it[login]
                    )
                }
            }
        } catch (e: Exception){
            emptyList()
        }
    }

}