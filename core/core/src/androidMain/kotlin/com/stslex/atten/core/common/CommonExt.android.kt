package com.stslex.atten.core.common

import com.stslex.atten.core.BuildConfig
import java.util.UUID

actual val randomUuid: String
    get() = UUID.randomUUID().toString()

actual val isDebug: Boolean
    get() = BuildConfig.DEBUG