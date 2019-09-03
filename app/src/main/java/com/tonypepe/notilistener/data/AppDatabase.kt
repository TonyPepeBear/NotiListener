package com.tonypepe.notilistener.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Notice::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noticeDao(): NoticeDao
    fun getAll(): LiveData<PagedList<Notice>> =
        LivePagedListBuilder(noticeDao().getAll(), 30)
            .build()

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
}