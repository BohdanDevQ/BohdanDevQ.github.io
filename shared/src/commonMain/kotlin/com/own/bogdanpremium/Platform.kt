package com.own.bogdanpremium

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform