package mcts.baeldung

interface Board {
    val emptyPositions: List<Position>

    fun copy(): TicTacToeBoard
    fun performMove(player: Int, p: Position)
    fun checkStatus(): Int
    fun printBoard()
    fun printStatus()
}

data class Position(val x: Int = 0, val y: Int = 0)