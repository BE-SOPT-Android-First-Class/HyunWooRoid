package com.l2hyunwoo.android.presentation.util

import android.app.Activity
import kotlin.properties.ReadOnlyProperty

fun intExtra(defaultValue: Int = -1) = ReadOnlyProperty<Activity, Int> { thisRef, property ->
    thisRef.intent.extras?.getInt(
        property.name,
        defaultValue
    ) ?: defaultValue
}