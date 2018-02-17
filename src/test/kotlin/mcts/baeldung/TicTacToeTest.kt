package mcts.baeldung

import org.junit.Assert.assertEquals
import org.junit.Test


class TicTacToeTest {
    @Test
    fun givenEmptyBoard_whenSimulateInterAIPlay_thenGameDraw() {
        var board = Board()
        var player = Board.P1
        val totalMoves = Board.DEFAULT_BOARD_SIZE * Board.DEFAULT_BOARD_SIZE
        for (i in 0 until totalMoves) {
            board = MonteCarloTreeSearch.findNextMove(board, player)
            if (board.checkStatus() != Board.IN_PROGRESS) {
                break
            }
            player = 3 - player
        }
        val winStatus = board.checkStatus()

        board.printStatus()

        assertEquals(winStatus, Board.DRAW)
    }
}