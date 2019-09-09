package com.tonypepe.notilistener

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.tonypepe.notilistener.data.AppDatabase
import com.tonypepe.notilistener.data.ignor.Ignore
import com.tonypepe.notilistener.data.notice.Notice
import kotlinx.coroutines.runBlocking

class NoticeListenerService : NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        if (sbn == null) return
        logd(sbn)
        val appDatabase = AppDatabase.getInstance(this@NoticeListenerService)
        val noticeDao = appDatabase.noticeDao()
        val ignoreDao = appDatabase.ignoreDao()
        val notification = sbn.notification
        val ignoreList = ignoreDao.getAllLiveData().also { logd(it.value) }
        notification.extras?.apply {
            val title = getString(Notification.EXTRA_TITLE, "")
            val message = getString(Notification.EXTRA_TEXT, "")
            logd(ignoreList.value)
            ignoreList.observeForever {
                if (!it.contains(Ignore(sbn.packageName))) {
                    runBlocking {
                        noticeDao.insert(Notice(title, message, sbn.packageName).also { logd(it) })
                    }
                }
            }
        }
    }
}
