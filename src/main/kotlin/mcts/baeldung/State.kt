package mcts.baeldung

import java.util.ArrayList


class State {
    internal var board: Board? = null
    internal var playerNo: Int = 0
    var visitCount: Int = 0
    internal var winScore: Double = 0.toDouble()

    internal val opponent: Int
        get() = 3 - playerNo

    val allPossibleStates: List<State>
        get() {
            val possibleStates = ArrayList<State>()
            val availablePositions = this.board!!.emptyPositions
            availablePositions.forEach { p ->
                val newState = State(this.board!!)
                newState.playerNo = 3 - this.playerNo
                newState.board!!.performMove(newState.playerNo, p)
                possibleStates.add(newState)
            }
            return possibleStates
        }

    constructor() {
        board = Board()
    }

    constructor(state: State) {
        this.board = Board(state.board!!)
        this.playerNo = state.playerNo
        this.visitCount = state.visitCount
        this.winScore = state.winScore
    }

    constructor(board: Board) {
        this.board = Board(board)
    }

    internal fun incrementVisit() {
        this.visitCount++
    }

    internal fun addScore(score: Double) {
        if (this.winScore != Integer.MIN_VALUE.toDouble())
            this.winScore += score
    }

    internal fun randomPlay() {
        val availablePositions = this.board!!.emptyPositions
        val totalPossibilities = availablePositions.size
        val selectRandom = (Math.random() * (totalPossibilities - 1 + 1)).toInt()
        this.board!!.performMove(this.playerNo, availablePositions[selectRandom])
    }

    internal fun togglePlayer() {
        this.playerNo = 3 - this.playerNo
    }
}