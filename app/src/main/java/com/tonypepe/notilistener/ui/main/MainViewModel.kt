package com.tonypepe.notilistener.ui.main

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.tonypepe.notilistener.data.AppDatabase
import com.tonypepe.notilistener.data.notice.Notice

class MainViewModel(val appDatabase: AppDatabase) : ViewModel() {
    //    val noticePagedLiveData = appDatabase.getAllNoticeTitle().also { logd(it) }
    private val query = MutableLiveData<String>().also { it.value = "" }
    val noticePagedLiveData: LiveData<PagedList<Notice>> =
        Transformations.switchMap(
            query
        ) { input ->
            if (input != "")
                appDatabase.getAllNoticeTitleBySearch(input)
            else
                appDatabase.getAllNoticeTitle()
        }

    fun setSearch(newText: String?) {
        newText?.let {
            query.value = newText
        }
    }
}

class MainViewModelFactory private constructor(val context: Context) : ViewModelProvider.Factory {
    companion object {
        fun createFactory(context: Context): MainViewModelFactory {
            return MainViewModelFactory(context)
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AppDatabase::class.java)
            .newInstance(AppDatabase.getInstance(context))
    }
}