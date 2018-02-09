package mcts.js

import org.junit.Assert.assertEquals
import org.junit.Test


class TicTacToeGameTest {
    @Test
    fun favorWinningMove() {
        val board: Map<Int, TTT.Placement> = TicTacToeGame.Board(
                TTT.Placement.OPlayer, TTT.Placement.OPlayer, TTT.Placement.Empty,
                TTT.Placement.XPlayer, TTT.Placement.XPlayer, TTT.Placement.Empty,
                TTT.Placement.OPlayer, TTT.Placement.OPlayer, TTT.Placement.Empty
        )

        val mcts = MCTS(TicTacToeGame(board, TTT.Placement.XPlayer), 1000, java.util.Random(0L))
        assertEquals(5, mcts.selectMove())
    }
}