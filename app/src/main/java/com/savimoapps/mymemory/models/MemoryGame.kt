package com.savimoapps.mymemory.models

import com.savimoapps.mymemory.utils.DEFAULT_ICONS

class MemoryGame(private val boardSize: BoardSize,
                 private val customImages: List<String>?) {

    val cards: List<MemoryCard> = if (customImages == null) {
        val chosenImages = DEFAULT_ICONS.shuffled().take(boardSize.getNumPairs())
        val randomizedImages = (chosenImages + chosenImages).shuffled()
        randomizedImages.map { MemoryCard(it) }

    }else{
        val randomizedImages = (customImages + customImages).shuffled()
        randomizedImages.map { MemoryCard(it.hashCode(),it) }
    }
    var numPairsFound = 0
    private var numCardFlips = 0
    private var indexOfSingleSelectedCard: Int? = null
    var foundMatch = false

    //flips the card
    fun flipCard(position: Int): Boolean {
        numCardFlips++
        val card = cards[position]
        //case
        //0 cards previously flipped over
        if (indexOfSingleSelectedCard == null) {
            //0 or 2 cards previously flipped over
            restoreCards()
            indexOfSingleSelectedCard = position
        } else {
            //1 card previously flipped over
            foundMatch = checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }

        //2 cards previously flipped over
        card.isFaceUp = !card.isFaceUp
        return foundMatch
    }

    //checks for matched cards
    private fun checkForMatch(position1: Int, position2: Int): Boolean {
        if (cards[position1].identifier != cards[position2].identifier) {
            return false
        }
        cards[position1].isMatched = true
        cards[position2].isMatched = true
        numPairsFound++
        return true
    }

    //restores cards to default state
    private fun restoreCards() {
        for (card in cards) {
            if (!card.isMatched) {
                card.isFaceUp = false
            }
        }
    }

    //checks whether the user has won the game
    fun haveWonGame(): Boolean {
        return numPairsFound == boardSize.getNumPairs()
    }

    //checks whether the card is faced up or down
    fun isCardFaceUp(position: Int): Boolean {
        return cards[position].isFaceUp
    }

    // calculates the number of moves
    fun getNumMoves(): Int {
        return numCardFlips / 2
    }

}