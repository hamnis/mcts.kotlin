package javabin.game

import javabin.*

class TTT(size: Int) : Game<TicTacToeBoard> {
    override val start: TicTacToeBoard = TicTacToeBoard.ofSize(size)

    override fun currentPlayer(board: TicTacToeBoard): Player = board.currentPlayer

    override fun statusOf(board: TicTacToeBoard): Status = board.checkStatus()

    override fun move(pos: Position, board: TicTacToeBoard): TicTacToeBoard = board.withMove(board.currentPlayer.opponent, pos)

    override fun printBoard(board: TicTacToeBoard) {
        fun toSymbol(pos: Placement): String = when (pos) {
            Placement.Occupied(Player.One) -> "X"
            Placement.Occupied(Player.Two) -> "O"
            else -> "."
        }

        val size = board.boardValues.size
        for (i in 0 until size) {
            for (j in 0 until size) {
                print(toSymbol(board.boardValues[i][j]) + " ")
            }
            println()
        }
    }

    override fun printStatus(status: Status) {
        when (status) {
            Status.Win(Player.One) -> println("Player 1 wins")
            Status.Win(Player.Two) -> println("Player 2 wins")
            Status.Draw -> println("Game Draw")
            is Status.InProgress -> println("Game In progress")
            else -> IllegalStateException("Not a valid status")
        }
    }
}
