package com.tonypepe.notilistener.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.tonypepe.notilistener.R
import com.tonypepe.notilistener.data.notice.Notice
import com.tonypepe.notilistener.logd
import org.jetbrains.anko.sdk27.coroutines.onClick

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
    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder =
        NoticeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_notice,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        getItem(position)?.also {
            logd(it)
            holder.bindView(it)
            holder.itemView.onClick { _ -> onItemClickListener?.onItemClick(it) }
        }
    }
}

interface OnItemClickListener {
    fun onItemClick(notice: Notice)
}
