package com.tonypepe.notilistener

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.tonypepe.notilistener.data.AppDatabase
import com.tonypepe.notilistener.data.Notice
import kotlinx.coroutines.runBlocking

class NoticeListenerService : NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        if (sbn == null) return
        logd(sbn)
        val notification = sbn.notification
        notification.extras?.apply {
            val title = getString(Notification.EXTRA_TITLE, "")
            val message = getString(Notification.EXTRA_TEXT, "")
            logd("title: $title / message: $message")
            val noticeDao = AppDatabase.getInstance(this@NoticeListenerService).noticeDao()
            runBlocking {
                noticeDao.insert(Notice(title, message))

            }
        }
    }
}
