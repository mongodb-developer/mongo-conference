package com.mongodb.mongoize

expect class RandomUUID() {
    val randomId: String
}


interface Platform {
    val name: String
}

expect fun getPlatform(): Platform