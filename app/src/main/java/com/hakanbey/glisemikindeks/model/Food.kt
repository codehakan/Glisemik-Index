package com.hakanbey.glisemikindeks.model

// Code with ❤️
//┌──────────────────────────┐
//│ Created by Hakan AKKAYA  │
//│ ──────────────────────── │
//│ contact@hakanakkaya.net  │            
//│ ──────────────────────── │
//│ 29.01.2022               │
//└──────────────────────────┘

data class Food(
    var id: Int? = null,
    var name: String? = null,
    var index: Int? = null,
    var carbonHydrateIn100Gram: Float? = null,
    var calorieIn100Gram: Int? = null,
    var categoryId: Int? = null
)