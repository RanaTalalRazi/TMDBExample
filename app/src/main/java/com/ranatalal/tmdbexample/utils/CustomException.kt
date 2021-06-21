package com.ranatalal.tmdbexample.utils

class CustomException(
    override var message: String
) : Exception() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val e = CustomException("some message")
            println(e.message)
        }
    }
}