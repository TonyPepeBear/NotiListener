package com.tonypepe.notilistener.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tonypepe.notilistener.R
import com.tonypepe.notilistener.data.notice.Notice
import com.tonypepe.notilistener.ui.NoticeAdapter
import com.tonypepe.notilistener.ui.OnItemClickListener
import com.tonypepe.notilistener.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.intentFor

class MainActivity : AppCompatActivity(), OnItemClickListener {
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener {}
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)
        val adapter = NoticeAdapter().also {
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

    override fun onItemClick(notice: Notice) {
        startActivity(
            intentFor<DetailActivity>("TITLE" to notice.title)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
                    .also { startActivity(it) }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
