package mcts

interface Board<Player> {
    val emptyPositions: List<Position>
    fun performMove(player: Player, p: Position): Board<Player>
    fun checkStatus(): Status<Player>
    fun opponent(player: Player): Player
    fun printBoard()
    fun printStatus()
}

data class Position(val x: Int = 0, val y: Int = 0)
