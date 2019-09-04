package com.tonypepe.notilistener.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tonypepe.notilistener.data.AppDatabase
import com.tonypepe.notilistener.logd

class MainViewModel(val appDatabase: AppDatabase) : ViewModel() {
    val noticePagedLiveData = appDatabase.getAllNoticeTitle().also { logd(it) }
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