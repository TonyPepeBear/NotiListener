package com.tonypepe.notilistener.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tonypepe.notilistener.R
import com.tonypepe.notilistener.ui.NoticeAdapter
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.alert

class DetailActivity : AppCompatActivity() {
    lateinit var viewModel: DetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        val noticeTitle = intent.getStringExtra("TITLE")
        fab.setOnClickListener {
            alert {
                title = getString(R.string.are_you_sure)
                message = getString(R.string.delete_all)
                positiveButton(R.string.ok) {
                    finish()
                    viewModel.appDatabase.deleteNoticeByTitle(noticeTitle!!)
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
        val adapter = NoticeAdapter().also { recycler.adapter = it }
        viewModel.data.observe(this, Observer {
            adapter.submitList(it)
        })
    }
}
