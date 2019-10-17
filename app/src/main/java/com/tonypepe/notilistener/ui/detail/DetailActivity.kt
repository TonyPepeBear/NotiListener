package com.tonypepe.notilistener.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tonypepe.notilistener.R
import com.tonypepe.notilistener.copyToClipboard
import com.tonypepe.notilistener.data.notice.Notice
import com.tonypepe.notilistener.ui.NoticeAdapter
import com.tonypepe.notilistener.ui.OnItemClickListener
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.alert

class DetailActivity : AppCompatActivity(), OnItemClickListener {
    companion object {
        const val NOTICE_TEXT_LABLE = "notice text"
    }

    lateinit var noticeTitle: String
    lateinit var viewModel: DetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        noticeTitle = intent.getStringExtra("TITLE") ?: ""
        fab.setOnClickListener {
            alert {
                title = getString(R.string.are_you_sure)
                message = getString(R.string.delete_all)
                positiveButton(R.string.ok) {
                    finish()
                    viewModel.appDatabase.deleteNoticeByTitle(noticeTitle)
                }
                negativeButton(R.string.no) {}
            }.show()
        }
        viewModel = ViewModelProviders
            .of(this, DetailViewModelFactory.createFactory(this))
            .get(DetailViewModel::class.java)
        viewModel.title.value = noticeTitle
        recycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@DetailActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@DetailActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        val adapter = NoticeAdapter().also {
            it.onItemClickListener = this
            recycler.adapter = it
        }
        viewModel.data.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_ignore -> {
                alert {
                    title = getString(R.string.are_you_sure)
                    message = getString(R.string.ignore_this_package)
                    positiveButton(R.string.ok) {
                        viewModel.insertIgnore()
                        finish()
                    }
                    negativeButton(R.string.no) {}
                }.show()
                true
            }
            else -> true
        }
    }

    override fun onItemClick(notice: Notice) {
        copyToClipboard(NOTICE_TEXT_LABLE, notice.text)
    }

    override fun onItemLongClick(notice: Notice) {

    }
}
