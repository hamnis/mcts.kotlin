package mcts

class State(var board: Board, var player: Player, var visitCount: Int = 0, var winScore: Double = 0.0) {

    fun allPossibleStates(availablePositions: List<Position>): List<State> {
        return availablePositions.map { State(this.board.withMove(player.opponent, it), player.opponent) }
    }

    fun copy(): State = State(this.board, this.player, this.visitCount, this.winScore)

    internal fun incrementVisit() {
        this.visitCount++
    }

    internal fun addScore(score: Double) {
        if (this.winScore != NO_WIN_SCORE)
            this.winScore += score
    }

    companion object {
        const val NO_WIN_SCORE = Integer.MIN_VALUE.toDouble()
    }
}
