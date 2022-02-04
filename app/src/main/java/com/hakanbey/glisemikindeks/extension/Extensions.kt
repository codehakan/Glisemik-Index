package com.hakanbey.glisemikindeks.extension

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

// Code with ❤️
//┌──────────────────────────┐
//│ Created by Hakan AKKAYA  │
//│ ──────────────────────── │
//│ contact@hakanakkaya.net  │            
//│ ──────────────────────── │
//│ 04.02.2022               │
//└──────────────────────────┘

fun saveToolTipVersion(context: Context, key: String?, value: Int) {
    val sharedPref = context.getSharedPreferences(key, Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.putInt(key, value)
    editor.apply()
}

fun getToolTipVersion(context: Context, key: String?): Int {
    val sharedPref = context.getSharedPreferences(key, Context.MODE_PRIVATE)
    return sharedPref.getInt(key, 0)
}