package com.clearing.core_ui.extention

fun Int?.orZero() : Int {
    return this ?: 0
}
