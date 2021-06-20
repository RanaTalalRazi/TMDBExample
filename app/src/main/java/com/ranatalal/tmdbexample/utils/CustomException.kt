package com.ranatalal.tmdbexample.utils

class CustomException(  // Overrides Exception's getMessage()
    override var message: String
) : Exception() {

    companion object {
        // Testing
        @JvmStatic
        fun main(args: Array<String>) {
            val e = CustomException("some message")
            println(e.message)
        }
    }
}