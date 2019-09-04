package com.tonypepe.notilistener.data.ignor

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface IgnoreDao {
    @Query("select * from `ignore-notice`")
    fun getAllLiveData(): LiveData<List<Ignore>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ignore: Ignore)

    @Delete
    fun delete(ignore: Ignore)
}
