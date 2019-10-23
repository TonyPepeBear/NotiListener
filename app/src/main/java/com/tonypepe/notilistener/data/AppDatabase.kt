package com.tonypepe.notilistener.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tonypepe.notilistener.data.ignor.Ignore
import com.tonypepe.notilistener.data.ignor.IgnoreDao
import com.tonypepe.notilistener.data.notice.Notice
import com.tonypepe.notilistener.data.notice.NoticeDao
import kotlinx.coroutines.runBlocking

@Database(entities = [Notice::class, Ignore::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noticeDao(): NoticeDao
    abstract fun ignoreDao(): IgnoreDao

    companion object {
        private var mInstance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (mInstance == null) {
                mInstance = Room.databaseBuilder(context, AppDatabase::class.java, "notice-db")
                    .build()
            }
            return mInstance!!
        }
    }

    fun getAllNoticeTitle() = LivePagedListBuilder(noticeDao().getAllTitle(), 30)
        .build()

    fun getAllNoticeTitleBySearch(title: String) =
        LivePagedListBuilder(noticeDao().getAllTitleBySearch(title), 30)
            .build()


    fun getByTitle(title: String): LiveData<PagedList<Notice>> =
        LivePagedListBuilder(noticeDao().getByTitle(title), 30)
            .build()

    fun deleteNoticeByTitle(title: String) {
        runBlocking {
            noticeDao().deleteByTitle(title)
        }
    }

    fun deleteAllNotice() {
        runBlocking {
            noticeDao().deleteAllNotice()
        }
    }

    fun deleteNoticeByPackage(pak: String) {
        runBlocking {
            noticeDao().deleteByPackage(pak)
        }
    }

    fun getAllIgnore() = LivePagedListBuilder(ignoreDao().getAllPagedData(), 30)
        .build()

    fun insertIgnore(ignore: Ignore) {
        runBlocking {
            ignoreDao().insert(ignore)
        }
    }

    fun deleteSingleNotice(notice: Notice) {
        runBlocking {
            noticeDao().delete(notice)
        }
    }

    fun insertNotice(notice: Notice) {
        runBlocking {
            noticeDao().insert(notice)
        }
    }
}
