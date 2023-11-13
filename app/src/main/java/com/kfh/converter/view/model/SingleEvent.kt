package com.kfh.converter.view.model

open class SingleEvent<out T>(private val content: T, val isCleared: Boolean = false) {

    private var isObserved = false

    fun isObserved(): T? =
        if (isObserved) null
        else {
            isObserved = true
            content
        }

    fun getContent() = content
}
