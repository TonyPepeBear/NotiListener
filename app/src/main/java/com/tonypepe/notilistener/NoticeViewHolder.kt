package com.tonypepe.notilistener

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tonypepe.notilistener.data.Notice
import kotlinx.android.synthetic.main.item_notice.view.*

class NoticeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title = view.textView_title
    val text = view.textView_text

    fun bindView(notice: Notice) {
        title.text = notice.title
        text.text = notice.text
    }
}