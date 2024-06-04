package reminder.ru.database.reminds

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction


object Reminds : Table("reminds") {
    val id = Reminds.integer("id")
    private var title = Reminds.varchar("title", 50)
    private var description = Reminds.varchar("description", 300).nullable()
    private var time = Reminds.varchar("time", 10).nullable()
    private var date = Reminds.varchar("date", 10).nullable()
    private val login = Reminds.varchar("login", 25)
    private val notified = Reminds.bool("notified")
    private val needToNotified = Reminds.bool("need_to_notified")

    fun insert(remindDTO: RemindDTO) {
        transaction {
            Reminds.insert {
                it[id] = remindDTO.id
                it[title] = remindDTO.title
                it[description] = remindDTO.description
                it[time] = remindDTO.time
                it[date] = remindDTO.date
                it[notified] = remindDTO.notified
                it[login] = remindDTO.login
                it[needToNotified] = remindDTO.needToNotified
            }
        }
    }

    fun update(remindDTO: UpdateRemindDTO) {
        transaction {
            Reminds.update({ Reminds.id eq remindDTO.id }) {
                it[title] = remindDTO.title
                it[description] = remindDTO.description
                it[time] = remindDTO.time
                it[date] = remindDTO.date
                it[needToNotified] = remindDTO.needToNotified
            }
        }
    }

    fun updateNotified(id: Int, notified: Boolean) {
        transaction {
            Reminds.update( { Reminds.id eq id } ) {
                it[Reminds.notified] = notified
            }
        }
    }

    fun delete(id: Int) {
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
                        notified = it[notified],
                        login = it[login],
                        needToNotified = it[needToNotified]
                    )
                }
            }
        } catch (e: Exception){
            emptyList()
        }
    }

}