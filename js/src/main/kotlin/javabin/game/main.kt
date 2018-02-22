package javabin.game

import javabin.util.JsRandom
import javabin.util.millis
import javabin.mcts.*
import javabin.*

fun main(args: Array<String>) {
    var player: Player = Player.One
    var board: Board = TicTacToeBoard(currentPlayer = player)
    val totalMoves = TicTacToeBoard.Companion.DEFAULT_BOARD_SIZE * TicTacToeBoard.Companion.DEFAULT_BOARD_SIZE
    for (i in 0 until totalMoves) {
        board = MonteCarloTreeSearch.findNextMove(board, player, JsRandom, 500.millis())
        if (board.checkStatus() !is Status.InProgress) {
            break
        }
        player = player.opponent
    }
    val status = board.checkStatus()

    TicTacToePrinter.printStatus(status)
}
