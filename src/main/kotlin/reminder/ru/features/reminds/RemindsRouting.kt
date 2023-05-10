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
        get("/reminds/fetch") {
            RemindsController(call).performSearch()
    }

    }
}
//routing {
//    post("/reminders/fetch") {
//        val remindsController: RemindsController(call)
//        remindsController.performSearch()
//
//    }
//    post("/reminders/edit") {
//        val remindsController: RemindsController(call)
//        remindsController.updateRemind()
//
//    }
//    post("reminds/create") {
//        val remindsController: RemindsController(call)
//        remindsController.createRemind()
//    }
//}