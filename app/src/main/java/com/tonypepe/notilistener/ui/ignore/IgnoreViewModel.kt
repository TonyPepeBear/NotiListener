package com.tonypepe.notilistener.ui.ignore

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tonypepe.notilistener.data.AppDatabase

class IgnoreViewModel(database: AppDatabase) : ViewModel() {
    val ignoreList = database.getAllIgnore()
}

class IgnoreViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    companion object {
        fun getInstance(context: Context) = IgnoreViewModelFactory(context)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AppDatabase::class.java)
            .newInstance(AppDatabase.getInstance(context))
    }

}