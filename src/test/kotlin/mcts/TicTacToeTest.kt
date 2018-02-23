package mcts

import mcts.util.JVMRandom
import mcts.util.millis
import org.junit.Assert.assertEquals
import org.junit.Test


class TicTacToeTest {
    @Test
    fun givenEmptyBoard_whenSimulateInterAIPlay_thenGameDraw() {
        var board: Board = TicTacToeBoard()
        var player: Player = Player.One
        val totalMoves = TicTacToeBoard.Companion.DEFAULT_BOARD_SIZE * TicTacToeBoard.Companion.DEFAULT_BOARD_SIZE
        for (i in 0 until totalMoves) {
            board = MonteCarloTreeSearch.findNextMove(board, player, JVMRandom, 500.millis())
            if (board.checkStatus() != Status.InProgress) {
                break
            }
            player = player.opponent
        }
        val winStatus = board.checkStatus()

        board.printStatus()

        assertEquals(Status.Draw, winStatus)
    }

    @Test
    fun favorWinningMove() {
        val board = TicTacToeBoard(arrayOf(
                arrayOf(Placement.Occupied(Player.Two), Placement.Occupied(Player.Two), Placement.Empty),
                arrayOf(Placement.Occupied(Player.One), Placement.Occupied(Player.One), Placement.Empty),
                arrayOf(Placement.Occupied(Player.Two), Placement.Occupied(Player.Two), Placement.Empty)
        ))
        val movedBoard = MonteCarloTreeSearch.findNextMove(board, Player.One, JVMRandom, 500.millis())

        assertEquals(Status.Win(Player.One), movedBoard.checkStatus())
        movedBoard.printStatus()
        movedBoard.printBoard()
    }
}
