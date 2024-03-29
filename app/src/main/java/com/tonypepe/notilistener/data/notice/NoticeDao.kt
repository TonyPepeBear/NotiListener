package com.tonypepe.notilistener.data.notice

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface NoticeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notice: Notice)

    @Query("select * from notice order by time desc")
    fun getAll(): DataSource.Factory<Int, Notice>

    @Query("select * from notice group by title having count() > 1 order by time desc")
    fun getAllTitle(): DataSource.Factory<Int, Notice>

    @Query("select * from notice where title like '%' || :message || '%' or pak like '%' || :message || '%' or text like '%' || :message || '%' group by title having count() > 1 order by time desc")
    fun getAllTitleBySearch(message: String): DataSource.Factory<Int, Notice>

    @Query("select * from notice where title = :title order by time desc")
    fun getByTitle(title: String): DataSource.Factory<Int, Notice>

    @Query("delete from notice where title = :title")
    suspend fun deleteByTitle(title: String)

    @Query("delete from notice where pak = :pak")
    suspend fun deleteByPackage(pak: String)

    @Query("delete from notice")
    suspend fun deleteAllNotice()

    @Delete
    suspend fun delete(notice: Notice)
}
