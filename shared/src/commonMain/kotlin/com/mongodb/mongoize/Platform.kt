package com.mongodb.mongoize

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform