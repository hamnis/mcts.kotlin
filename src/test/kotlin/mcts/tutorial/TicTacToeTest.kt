package mcts.tutorial

import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test


class TicTacToeTest {
    @Test
    @Ignore
    fun givenEmptyBoard_whenSimulateInterAIPlay_thenGameDraw() {
        var board: Board = TicTacToe()
        var player = TicTacToe.P1
        val totalMoves = TicTacToe.DEFAULT_BOARD_SIZE * TicTacToe.DEFAULT_BOARD_SIZE
        for (i in 0 until totalMoves) {
            board = MCTS.findNextMove(board, player)
            if (board.checkStatus() != Board.IN_PROGRESS) {
                break
            }
            player = 3 - player
        }
        val winStatus = board.checkStatus()

        board.printStatus()

        assertEquals(winStatus, TicTacToe.DRAW)
    }
}