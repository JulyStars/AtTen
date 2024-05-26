package com.stslex.atten.core.coroutine.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher

interface AppDispatcher {
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
    val main: MainCoroutineDispatcher
}
