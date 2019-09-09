package com.tonypepe.notilistener.data.ignor

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ig")
data class Ignore(
    @PrimaryKey val pak: String
)