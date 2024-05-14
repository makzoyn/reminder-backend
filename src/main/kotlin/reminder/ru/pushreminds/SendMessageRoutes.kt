package reminder.ru.pushreminds

import com.google.firebase.messaging.FirebaseMessaging
import reminder.ru.models.PushNotificationModel


fun sendNotification(notificationModel: PushNotificationModel) {

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