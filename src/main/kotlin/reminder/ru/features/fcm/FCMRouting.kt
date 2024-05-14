package reminder.ru.features.fcm

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureFCMRouting() {
    routing {
        post("/device/register") {
            val fcmController = FCMController(call)
            fcmController.registerFCMToken()
        }
        post("/devices/delete-after-push") {
            val fcmController = FCMController(call)
            fcmController.deleteAfterPush()
        }
    }
}
