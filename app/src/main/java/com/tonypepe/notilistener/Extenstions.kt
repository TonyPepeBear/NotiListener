package com.tonypepe.notilistener

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import org.jetbrains.anko.toast

fun Any.logd(message: Any?) {
    Log.d(this::class.java.simpleName, message.toString())
}

fun Activity.copyToClipboard(label: String, text: String) {
    val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val plainText = ClipData.newPlainText(label, text)
    toast("copy to clipboard")
    clipboardManager.setPrimaryClip(plainText)
}
