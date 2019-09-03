package com.tonypepe.notilistener.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Notice(
    @PrimaryKey val time: Long,
    val title: String,
    val text: String
) {
    constructor(title: String, text: String) : this(Date().time, title, text)
}
