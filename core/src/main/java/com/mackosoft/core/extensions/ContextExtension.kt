package com.mackosoft.core.extensions

import android.content.Context
import android.util.TypedValue

fun Context.fromDipToPixel(dipValue: Float): Float =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dipValue,
        resources.displayMetrics
    )