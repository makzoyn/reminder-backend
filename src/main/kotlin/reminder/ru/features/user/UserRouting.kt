package reminder.ru.features.user

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureUserRouting() {
    routing {
//        post("/reminds/create") {
//            val remindsController = RemindsController(call)
//            remindsController.createRemind()
//        }
//        get("/reminds/fetch") {
//            RemindsController(call).getNotifiedReminds()
//        }
//        get("/reminds/notes") {
//            RemindsController(call).getNotes()
//        }
//        get("/reminds/fetch/{id}") {
//            RemindsController(call).getRemindById()
//        }
//        patch("/reminds/update/{id}"){
//            RemindsController(call).updateRemind()
//        }
//        post("/reminds/delete") {
//            RemindsController(call).deleteRemind()
//        }
        get("/user"){
            UserController(call).getUser()
        }
     }
}