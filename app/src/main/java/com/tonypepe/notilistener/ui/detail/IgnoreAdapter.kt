package com.tonypepe.notilistener.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tonypepe.notilistener.R
import com.tonypepe.notilistener.data.ignor.Ignore
import kotlinx.android.synthetic.main.item_ignore.view.*

class IgnoreAdapter : PagedListAdapter<Ignore, IgnoreViewHolder>(
    object : DiffUtil.ItemCallback<Ignore>() {
        override fun areItemsTheSame(oldItem: Ignore, newItem: Ignore): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Ignore, newItem: Ignore): Boolean {
            return oldItem.pak == newItem.pak
        }
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IgnoreViewHolder {
        return IgnoreViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ignore, parent, false)
        )
    }

    override fun onBindViewHolder(holder: IgnoreViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindView(it)
        }
    }

}

class IgnoreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val textViewPak = view.textView_pak

    fun bindView(ignore: Ignore) {
        textViewPak.text = ignore.pak
    }
}
