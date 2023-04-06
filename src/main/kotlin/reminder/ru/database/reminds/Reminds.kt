package reminder.ru.database.reminds

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction



object Reminds: Table("reminds") {
    private val id = Reminds.integer("id_remind").autoIncrement()
    private val login = Reminds.varchar("login", 25)
    private var title = Reminds.varchar("title", 75)
    private var description = Reminds.varchar("description", 300)
    private var time = Reminds.varchar("time", 10)
    private var date = Reminds.varchar("date", 10)

    fun insert(remindDTO: RemindDTO) {
        transaction {
            Reminds.insert {
                it[id] = remindDTO.id
                it[login] = remindDTO.login
                it[title] = remindDTO.title
                it[description] = remindDTO.description
                it[time] = remindDTO.time
                it[date] = remindDTO.date
            }
        }
    }

    fun fetchRemind(login: String): RemindDTO? {
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
    }

    fun fetchAllReminds(login: String) : List<RemindDTO> {
        return try {
            Reminds.selectAll().toList().map {
                RemindDTO(
                    id = it[id],
                    login = it[Reminds.login],
                    title = it[title],
                    description = it[description],
                    time = it[time],
                    date = it[date]
                )
            }
        } catch (e: Exception){
            emptyList()
        }
    }
}