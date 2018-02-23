package javabin

interface Game<B : Board> {
    val start: B

    fun currentPlayer(board: B): Player

    fun statusOf(board: B): Status

    fun move(pos: Position, board: B): B

    fun score(terminal: Status.Terminal, player: Player): Double =
            when (terminal) {
                Status.Win(player) -> 10.0
                Status.Draw -> Int.MIN_VALUE.toDouble()
                else -> throw IllegalArgumentException("Not a legal state")
            }
    fun printBoard(board: B)
    fun printStatus(status: Status)
}
