package com.tonypepe.notilistener.data.notice

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

    @Query("select * from notice group by title having count() > 1 order by time desc")
    fun getAllTitle(): DataSource.Factory<Int, Notice>

    @Query("select * from notice where title = :title order by time desc")
    fun getByTitle(title: String): DataSource.Factory<Int, Notice>
}
