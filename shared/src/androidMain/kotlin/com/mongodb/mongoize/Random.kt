package com.mongodb.mongoize

import java.util.UUID

actual class RandomUUID {

    actual val randomId: String
        get() = UUID.randomUUID().toString()
}