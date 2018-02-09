package mcts.js

object Main {
    @JvmStatic
    fun main(args: Array<String>) {

        val mcts = MCTS(TicTacToeGame(TicTacToeGame.EmptyBoard, TTT.Placement.XPlayer), 1000, java.util.Random(0L))

        while (mcts.game.winner == null) {
            val move = mcts.selectMove()
            if (move != null) {
                println("Current player: ${mcts.game.currentPlayer}. Next move: $move. Board: ${(mcts.game as TicTacToeGame).board}")

                mcts.performMove(move)
            } else {
                println("Winner is: ${mcts.game.winner}")
                println((mcts.game as TicTacToeGame).board)
            }
        }
    }
}