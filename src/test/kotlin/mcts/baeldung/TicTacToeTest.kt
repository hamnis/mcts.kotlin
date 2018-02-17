package mcts.baeldung

import org.junit.Assert.assertEquals
import org.junit.Test


class TicTacToeTest {
    @Test
    fun givenEmptyBoard_whenSimulateInterAIPlay_thenGameDraw() {
        var board: Board = TicTacToeBoard()
        var player = TicTacToeBoard.P1
        val totalMoves = TicTacToeBoard.DEFAULT_BOARD_SIZE * TicTacToeBoard.DEFAULT_BOARD_SIZE
        for (i in 0 until totalMoves) {
            board = MonteCarloTreeSearch.findNextMove(board, player)
            if (board.checkStatus() != TicTacToeBoard.IN_PROGRESS) {
                break
            }
            player = 3 - player
        }
        val winStatus = board.checkStatus()

        board.printStatus()

        assertEquals(winStatus, TicTacToeBoard.DRAW)
    }
}