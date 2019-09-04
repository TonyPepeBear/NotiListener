package com.tonypepe.notilistener.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tonypepe.notilistener.R
import com.tonypepe.notilistener.ui.NoticeAdapter
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_main.*

class DetailActivity : AppCompatActivity() {
    lateinit var viewModel: DetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        fab.setOnClickListener {}
        val title = intent.getStringExtra("TITLE")
        viewModel = ViewModelProviders
            .of(this, DetailViewModelFactory.createFactory(this))
            .get(DetailViewModel::class.java)
        viewModel.title.value = title
        recycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@DetailActivity)
        }
        val adapter = NoticeAdapter().also { recycler.adapter = it }
        viewModel.data.observe(this, Observer {
            adapter.submitList(it)
        })
    }
}
