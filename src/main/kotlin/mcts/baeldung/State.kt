package mcts.baeldung

import java.util.ArrayList


class State(val board: Board, var playerNo: Int = 0, var visitCount: Int = 0, var winScore: Double = 0.0) {

    val allPossibleStates: List<State>
        get() {
            val possibleStates = ArrayList<State>()
            val availablePositions = this.board.emptyPositions
            availablePositions.forEach { p ->
                val newState = State(this.board.copy(), board.opponent(playerNo))
                newState.board.performMove(newState.playerNo, p)
                possibleStates.add(newState)
            }
            return possibleStates
        }

    fun copy(): State = State(this.board.copy(), this.playerNo, this.visitCount, this.winScore)

    internal fun incrementVisit() {
        this.visitCount++
    }

    internal fun addScore(score: Double) {
        if (this.winScore != NO_WIN_SCORE)
            this.winScore += score
    }

    internal fun randomPlay() {
        val availablePositions = this.board.emptyPositions
        val totalPossibilities = availablePositions.size
        val selectRandom = (Math.random() * (totalPossibilities - 1 + 1)).toInt()
        this.board.performMove(this.playerNo, availablePositions[selectRandom])
    }

    internal fun togglePlayer() {
        this.playerNo = board.opponent(this.playerNo)
    }

    companion object {
        val NO_WIN_SCORE = Integer.MIN_VALUE.toDouble()
    }
}