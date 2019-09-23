package com.tonypepe.notilistener.data.ignor

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*

@Dao
interface IgnoreDao {
    @Query("select * from ig")
    fun getAllPagedData(): DataSource.Factory<Int, Ignore>

    @Query("select * from ig")
    fun getAllLiveData(): LiveData<List<Ignore>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ignore: Ignore)

    @Delete
    fun delete(ignore: Ignore)
}
