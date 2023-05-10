package reminder.ru.database.reminds

import io.ktor.http.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction


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
    /*fun updateRemind(login: String): RemindDTO? {
        return try {
            transaction {
                val remindModel = Reminds.select { Reminds.login.eq(login) }.single()
                RemindDTO(
                    id = remindModel[Reminds.id],
                    login = remindModel[Reminds.login],
                    title = remindModel[title],
                    description = remindModel[description],
                    time = remindModel[time],
                    date = remindModel[date]
                )
            }
        }
        catch (e: Exception) {
            null
        }
    }*/
//    fun updateRemind()
    /*fun fetchReminds(login: String): List<RemindDTO> {
        return try {
            if (login != "empty") {
                transaction {
                    Reminds.selectAll().toList().map {
                        RemindDTO(
                            id = it[Reminds.id],
                            title = it[title],
                            description = it[description],
                            time = it[time],
                            date = it[date],
                            alarmId = it[alarmId],
                            login = login
                        )
                    }
                }
            }
            else {
                println("Login invalid")
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }*/
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