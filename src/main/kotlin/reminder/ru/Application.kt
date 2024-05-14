package reminder.ru

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import reminder.ru.features.fcm.configureFCMRouting
import reminder.ru.features.login.configureLoginRouting
import reminder.ru.features.register.configureRegisterRouting
import reminder.ru.features.reminds.configureRemindsRouting
import reminder.ru.plugins.*

fun main() {
    Database.connect("jdbc:postgresql://localhost:5433/reminders", driver = "org.postgresql.Driver",
        user = "postgres", password = "1234")
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRemindsRouting()
    configureSerialization()
    configureLoginRouting()
    configureRegisterRouting()
    configureRouting()
    configureFCMRouting()

    val servicesKey = this::class.java.classLoader.getResourceAsStream("firebase_key.json")
    val option = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(servicesKey))
        .build()

    FirebaseApp.initializeApp(option)
}
