package com.tonypepe.notilistener.data.ignor

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ignore-notice")
data class Ignore(
    @PrimaryKey val pak: String
)