package mcts.baeldung

interface Board {
    val emptyPositions: List<Position>
    fun performMove(player: Int, p: Position): Board
    fun checkStatus(): Int
    fun opponent(player: Int): Int
    fun printBoard()
    fun printStatus()
}

data class Position(val x: Int = 0, val y: Int = 0)