package com.tonypepe.notilistener.ui.ignore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tonypepe.notilistener.R
import com.tonypepe.notilistener.ui.detail.IgnoreAdapter
import kotlinx.android.synthetic.main.activity_ignore.*
import kotlinx.android.synthetic.main.content_ignore.*

class IgnoreActivity : AppCompatActivity() {
    lateinit var viewModel: IgnoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ignore)
        setSupportActionBar(toolbar)
        fab.hide()
        viewModel = ViewModelProviders.of(this, IgnoreViewModelFactory.getInstance(this))
            .get(IgnoreViewModel::class.java)
        val adapter = IgnoreAdapter()
        viewModel.ignoreList.observe(this, Observer {
            adapter.submitList(it)
        })
        recycler.apply {
            layoutManager = LinearLayoutManager(this@IgnoreActivity)
            setHasFixedSize(true)
            this.adapter = adapter
        }
    }

}
