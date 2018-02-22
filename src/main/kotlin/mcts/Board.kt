package mcts

interface Board {
    val emptyPositions: List<Position>
    fun performMove(player: Player, p: Position): Board
    fun checkStatus(): Status
    fun printBoard()
    fun printStatus()
}

data class Position(val x: Int = 0, val y: Int = 0)
