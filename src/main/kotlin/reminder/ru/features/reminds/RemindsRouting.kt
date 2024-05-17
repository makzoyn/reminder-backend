package reminder.ru.features.reminds

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRemindsRouting() {
    routing {
        post("/reminds/create") {
            val remindsController = RemindsController(call)
            remindsController.createRemind()
        }
        get("/reminds/fetch") {
            RemindsController(call).performSearch()
        }
        get("/reminds/fetch/{id}") {
            RemindsController(call).getRemindById()
        }
        patch("/reminds/update/{id}"){
            RemindsController(call).updateRemind()
        }
        post("/reminds/delete") {
            RemindsController(call).deleteRemind()
        }
    }
}
