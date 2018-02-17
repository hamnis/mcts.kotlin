package mcts.baeldung

import java.util.ArrayList


class State(var board: Board = Board(), var playerNo: Int = 0, var visitCount: Int = 0, var winScore: Double = 0.0) {

    internal val opponent: Int
        get() = 3 - playerNo

    val allPossibleStates: List<State>
        get() {
            val possibleStates = ArrayList<State>()
            val availablePositions = this.board.emptyPositions
            availablePositions.forEach { p ->
                val newState = State(this.board.copy(), 3 - this.playerNo)
                newState.board.performMove(newState.playerNo, p)
                possibleStates.add(newState)
            }
            return possibleStates
        }

    constructor(board: Board) : this(board.copy(), 0, 0, 0.0)

    fun copy(): State = State(this.board.copy(), this.playerNo, this.visitCount, this.winScore)

    internal fun incrementVisit() {
        this.visitCount++
    }

    internal fun addScore(score: Double) {
        if (this.winScore != Integer.MIN_VALUE.toDouble())
            this.winScore += score
    }

    internal fun randomPlay() {
        val availablePositions = this.board.emptyPositions
        val totalPossibilities = availablePositions.size
        val selectRandom = (Math.random() * (totalPossibilities - 1 + 1)).toInt()
        this.board.performMove(this.playerNo, availablePositions[selectRandom])
    }

    internal fun togglePlayer() {
        this.playerNo = 3 - this.playerNo
    }
}