package com.savimoapps.mymemory.models

enum class BoardSize(val numCards: Int) {
    EASY(8),
    MEDIUM(18),
    HARD(24);

    companion object {
        fun getByValue(value: Int) = values().first { it.numCards == value }
    }

    //get width of the board based on the size
    fun getWidth(): Int {
        return when (this) {
            EASY -> 2
            MEDIUM -> 3
            HARD -> 4
        }
    }

    fun getHeight(): Int {
        return numCards / getWidth()
    }

    //get number of pairs
    fun getNumPairs(): Int {
        return numCards / 2
    }
}