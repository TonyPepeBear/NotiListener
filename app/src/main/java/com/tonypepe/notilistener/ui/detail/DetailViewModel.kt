package com.tonypepe.notilistener.ui.detail

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tonypepe.notilistener.data.AppDatabase
import com.tonypepe.notilistener.data.ignor.Ignore
import com.tonypepe.notilistener.data.notice.Notice

class DetailViewModel(val appDatabase: AppDatabase) : ViewModel() {
    val title = MutableLiveData<String>()
    val data = Transformations.switchMap(title) { appDatabase.getByTitle(it!!) }

    fun insertIgnore() {
        val ignore = Ignore(data.value?.get(0)?.pak ?: return)
        appDatabase.apply {
            insertIgnore(ignore)
            deleteNoticeByPackage(ignore.pak)
        }
    }

    fun delete(notice: Notice) {
        appDatabase.deleteSingleNotice(notice)
    }

    fun insertNotice(notice: Notice) {
        appDatabase.insertNotice(notice)
    }
}

class DetailViewModelFactory private constructor(val context: Context) : ViewModelProvider.Factory {
    companion object {
        fun createFactory(context: Context): DetailViewModelFactory =
            DetailViewModelFactory(context)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AppDatabase::class.java)
            .newInstance(AppDatabase.getInstance(context))
    }
}
