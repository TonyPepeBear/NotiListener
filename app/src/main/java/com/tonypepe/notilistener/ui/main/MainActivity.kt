package com.tonypepe.notilistener.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tonypepe.notilistener.R
import com.tonypepe.notilistener.data.notice.Notice
import com.tonypepe.notilistener.logd
import com.tonypepe.notilistener.ui.NoticeAdapter
import com.tonypepe.notilistener.ui.OnItemClickListener
import com.tonypepe.notilistener.ui.detail.DetailActivity
import com.tonypepe.notilistener.ui.ignore.IgnoreActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.intentFor

class MainActivity : AppCompatActivity(), OnItemClickListener, SearchView.OnQueryTextListener {
    lateinit var viewModel: MainViewModel
    lateinit var adapter: NoticeAdapter
    lateinit var searcher: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener {
            alert {
                title = getString(R.string.are_you_sure)
                message = getString(R.string.delete_all)
                positiveButton(R.string.ok) {
                    viewModel.appDatabase.deleteAllNotice()
                    Snackbar.make(recycler, R.string.delete_success, Snackbar.LENGTH_SHORT).show()
                }
                negativeButton(R.string.no) {}
            }.show()
        }
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)
        recycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        adapter = NoticeAdapter().also {
            recycler.adapter = it
        }
        adapter.onItemClickListener = this
        viewModel = ViewModelProviders.of(
            this,
            MainViewModelFactory.createFactory(this)
        )
            .get(MainViewModel::class.java)
        viewModel.noticePagedLiveData.observe(this, Observer {
            adapter.submitList(it)
        })
    }
    override fun onItemLongClick(notice: Notice) {
        alert {
            title = getString(R.string.are_you_sure)
            message = getString(R.string.delete_all)
            positiveButton(getString(R.string.ok)) {
                viewModel.appDatabase.deleteNoticeByTitle(notice.title)
                Snackbar.make(recycler, R.string.delete_success, Snackbar.LENGTH_LONG).show()
            }
            negativeButton(getString(R.string.no)) {}
        }.show()
    }

    override fun onItemClick(notice: Notice) {
        startActivity(
            intentFor<DetailActivity>("TITLE" to notice.title)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        searcher = menu.findItem(R.id.menu_search).actionView as SearchView
        logd("search")
        searcher.setOnSearchClickListener {
            logd("search click")
        }
        searcher.setOnQueryTextListener(this)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
                    .also { startActivity(it) }
                true
            }
            R.id.action_ignore_list -> {
                startActivity(intentFor<IgnoreActivity>())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        logd("onQueryTextChange: $newText")
        viewModel.setSearch(newText)
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        logd("onQueryTextSubmit: $query")
        return false
    }
}
