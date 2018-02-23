package mcts

import mcts.util.Random

class State(var board: Board, var player: Player, var visitCount: Int = 0, var winScore: Double = 0.0) {

    fun allPossibleStates(availablePositions: List<Position>): List<State> {
        val possibleStates = mutableListOf<State>()
        availablePositions.forEach { p ->
            val newState = State(this.board.performMove(player.opponent, p), player.opponent)
            possibleStates.add(newState)
        }
        return possibleStates
    }

    fun copy(): State = State(this.board, this.player, this.visitCount, this.winScore)

    internal fun incrementVisit() {
        this.visitCount++
    }

    internal fun addScore(score: Double) {
        if (this.winScore != NO_WIN_SCORE)
            this.winScore += score
    }

    internal fun randomPlay(random: Random, availablePositions: List<Position>) {
        //val availablePositions = this.board.emptyPositions
        val totalPossibilities = availablePositions.size
        val selectRandom = random.nextInt(totalPossibilities)
        this.board = this.board.performMove(this.player, availablePositions[selectRandom])
    }

    internal fun togglePlayer() {
        this.player = this.player.opponent
    }

    companion object {
        const val NO_WIN_SCORE = Integer.MIN_VALUE.toDouble()
    }
}
