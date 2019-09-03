package com.tonypepe.notilistener

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.tonypepe.notilistener.data.Notice

class NoticeAdapter : PagedListAdapter<Notice, NoticeViewHolder>(
    object : DiffUtil.ItemCallback<Notice>() {
        override fun areItemsTheSame(oldItem: Notice, newItem: Notice): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Notice, newItem: Notice): Boolean {
            return oldItem.time == newItem.time
        }
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder =
        NoticeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_notice, parent, false)
        )

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        holder.bindView(getItem(position)!!)
    }
}
