package com.example.howtoprotectsuperearth.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Step(
    @DrawableRes val imageResourceId: Int,
    @StringRes val title: Int,
    @StringRes val step: Int,
    @StringRes val description: Int
)
