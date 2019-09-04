package com.tonypepe.notilistener.data.notice

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Notice(
    @PrimaryKey val time: Long,
    val title: String,
    val text: String,
    val pak: String
) {
    constructor(title: String, text: String, pak: String) : this(Date().time, title, text, pak)
}
