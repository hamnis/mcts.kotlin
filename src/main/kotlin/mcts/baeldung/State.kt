package mcts.baeldung

class State<Player>(var board: Board<Player>, var player: Player, var visitCount: Int = 0, var winScore: Double = 0.0) {

    val allPossibleStates: List<State<Player>>
        get() {
            val possibleStates = mutableListOf<State<Player>>()
            val availablePositions = this.board.emptyPositions
            availablePositions.forEach { p ->
                val newState = State(this.board.performMove(board.opponent(player), p), board.opponent(player))
                possibleStates.add(newState)
            }
            return possibleStates
        }

    fun copy(): State<Player> = State(this.board, this.player, this.visitCount, this.winScore)

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
        this.board = this.board.performMove(this.player, availablePositions[selectRandom])
    }

    internal fun togglePlayer() {
        this.player = board.opponent(this.player)
    }

    companion object {
        val NO_WIN_SCORE = Integer.MIN_VALUE.toDouble()
    }
}