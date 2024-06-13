package reminder.ru.pushreminds

import com.google.firebase.messaging.FirebaseMessaging
import com.huawei.hms.messaging.PushService
import reminder.ru.models.PushNotificationModel


fun sendNotification(notificationModel: PushNotificationModel) {

    if(notificationModel.fcmToken != null) {
        val body = SendMessageDTO(
            to = notificationModel.fcmToken,
            notification = NotificationBody(
                title = notificationModel.title,
                body = notificationModel.description,
            ),
            data = NotificationData(
                id = notificationModel.id
            )
        )


        FirebaseMessaging.getInstance().send(body.toMessage())
    }
    if (notificationModel.hmsToken != null) {
        val body = SendMessageDTO(
            to = notificationModel.hmsToken,
            notification = NotificationBody(
                title = notificationModel.title,
                body = notificationModel.description,
            ),
            data = NotificationData(
                id = notificationModel.id
            )
        )

        PushService.getInstance().send(body.toMessage())
    }

}