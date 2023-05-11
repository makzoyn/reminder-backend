package reminder.ru.features.reminds

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.request.*

fun Application.configureRemindsRouting() {
    routing {
        post("/reminds/create") {
            val remindsController = RemindsController(call)
            remindsController.createRemind()
        }
        post("/reminds/fetch") {
            RemindsController(call).performSearch()
        }
        put("/reminds/update/{id}"){
            RemindsController(call).updateRemind()
        }
        delete("/reminds/delete/{id}") {
            RemindsController(call).deleteRemind()
        }

    }
}
