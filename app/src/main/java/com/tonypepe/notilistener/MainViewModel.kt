package com.tonypepe.notilistener

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tonypepe.notilistener.data.AppDatabase

class MainViewModel(appDatabase: AppDatabase) : ViewModel() {
    val noticePagedLiveData = appDatabase.getAll().also { logd(it) }
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