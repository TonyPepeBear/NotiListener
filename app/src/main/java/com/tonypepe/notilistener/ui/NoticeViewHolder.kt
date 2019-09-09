package com.tonypepe.notilistener.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tonypepe.notilistener.data.notice.Notice
import kotlinx.android.synthetic.main.item_notice.view.*
import java.text.SimpleDateFormat

class NoticeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title = view.textView_title
    val text = view.textView_text
    val time = view.textView_time

    fun bindView(notice: Notice) {
        title.text = notice.title
        text.text = notice.text
        time.text = SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(notice.time)
    }
}