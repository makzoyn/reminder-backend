package reminder.ru

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import reminder.ru.features.login.configureLoginRouting
import reminder.ru.features.register.configureRegisterRouting
import reminder.ru.features.reminds.configureRemindsRouting
import reminder.ru.plugins.*

fun main() {
    Database.connect("jdbc:postgresql://localhost:5432/reminder", driver = "org.postgresql.Driver",
        user = "postgres", password = "marakas231")
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRemindsRouting()
    configureSerialization()
    configureLoginRouting()
    configureRegisterRouting()
    configureRouting()
}
