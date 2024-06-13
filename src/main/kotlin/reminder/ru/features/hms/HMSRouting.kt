package reminder.ru.features.hms

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureFCMRouting() {
    routing {
        post("/device/register") {
            val fcmController = HMSController(call)
            fcmController.registerFCMToken()
        }
        post("/devices/delete-after-push") {
            val fcmController = HMSController(call)
            fcmController.setNotifiedAfterPush()
        }
    }
}
