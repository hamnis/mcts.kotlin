package mcts

import org.junit.Assert.assertEquals
import org.junit.Test


class TicTacToeTest {
    @Test
    fun givenEmptyBoard_whenSimulateInterAIPlay_thenGameDraw() {
        val mcts = MonteCarloTreeSearch<Int>()
        var board: Board<Int> = TicTacToeBoard()
        var player = TicTacToeBoard.Companion.P1
        val totalMoves = TicTacToeBoard.Companion.DEFAULT_BOARD_SIZE * TicTacToeBoard.Companion.DEFAULT_BOARD_SIZE
        for (i in 0 until totalMoves) {
            board = mcts.findNextMove(board, player)
            if (board.checkStatus() != Status.InProgress) {
                break
            }
            player = board.opponent(player)
        }
        val winStatus = board.checkStatus()

        board.printStatus()

        assertEquals(Status.Draw, winStatus)
    }

    @Test
    fun favorWinningMove() {
        val board = TicTacToeBoard(arrayOf(intArrayOf(2, 2, 0), intArrayOf(1, 1, 0), intArrayOf(2, 2, 0)))
        val movedBoard = MonteCarloTreeSearch<Int>().findNextMove(board, 1)

        assertEquals(Status.Win(1), movedBoard.checkStatus())
        movedBoard.printStatus()
        movedBoard.printBoard()
    }
}
