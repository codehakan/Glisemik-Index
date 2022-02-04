package com.hakanbey.glisemikindeks.util

import androidx.lifecycle.MutableLiveData
import com.hakanbey.glisemikindeks.model.Category
import com.hakanbey.glisemikindeks.model.Food
import com.hakanbey.glisemikindeks.service.Database

// Code with ❤️
//┌──────────────────────────┐
//│ Created by Hakan AKKAYA  │
//│ ──────────────────────── │
//│ contact@hakanakkaya.net  │            
//│ ──────────────────────── │
//│ 29.01.2022               │
//└──────────────────────────┘

object Util {
    var dataStatus = MutableLiveData<Boolean>(false)

    /**
     * Verileri aldığımız internet sitesinin adresi
     */
    const val HTML_DATA_URL = "http://kolaydoktor.com/saglik-icin-yasam/diyet-ve-beslenme/besinlerin-glisemik-indeks-tablosu/0503/1"

    var foodIdForDetail: Int = -1

    /**
     * Düşük Glisemik Index Sınırı (Dahil)
     */
    const val GLISEMIK_INDEX_LOW_MAX: Int = 56

    /**
     * Orta Glisemik Index Sınırı (Dahil)
     */
    const val GLISEMIK_INDEX_MEDIUM_MAX: Int = 70
}