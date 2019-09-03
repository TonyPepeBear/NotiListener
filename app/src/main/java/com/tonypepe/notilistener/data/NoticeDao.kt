package com.tonypepe.notilistener.data

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoticeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notice: Notice)

    @Query("select * from notice order by time desc")
    fun getAll(): DataSource.Factory<Int, Notice>
}