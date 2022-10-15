package com.mongodb.mongoize

import java.util.*

actual class RandomUUID {

    actual val randomId: String
        get() = UUID.randomUUID().toString()
}