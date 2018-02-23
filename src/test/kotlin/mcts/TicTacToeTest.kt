package mcts

import mcts.util.JVMRandom
import mcts.util.millis
import org.junit.Assert.assertEquals
import org.junit.Test


class TicTacToeTest {
    @Test
    fun givenEmptyBoard_whenSimulateInterAIPlay_thenGameDraw() {
        givenEmptyBoard_whenSimulateInterAIPlay_thenGameDraw(TicTacToeBoard.DEFAULT_BOARD_SIZE)
        //givenEmptyBoard_whenSimulateInterAIPlay_thenGameDraw(4)
        //givenEmptyBoard_whenSimulateInterAIPlay_thenGameDraw(6)
    }

    fun givenEmptyBoard_whenSimulateInterAIPlay_thenGameDraw(size: Int) {
        var board: Board = TicTacToeBoard.ofSize(size)
        var player: Player = Player.One
        val totalMoves = size * size
        for (i in 0 until totalMoves) {
            board = MonteCarloTreeSearch.findNextMove(board, player, JVMRandom, 500.millis())
            if (board.checkStatus() !is Status.InProgress) {
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
        ), Player.One)
        val movedBoard = MonteCarloTreeSearch.findNextMove(board, Player.One, JVMRandom, 500.millis())

        assertEquals(Status.Win(Player.One), movedBoard.checkStatus())
        movedBoard.printStatus()
        movedBoard.printBoard()
    }
}
