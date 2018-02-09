package mcts.js

object TTT {
    interface Player

    sealed class Placement {
        object Empty : Placement() {
            override fun toString(): String {
                return "empty"
            }
        }
        object XPlayer : Placement(), Player {
            override fun toString(): String = "X"
        }
        object OPlayer : Placement(), Player {
            override fun toString(): String = "O"
        }
    }

    data class Score(val value: Int)

    data class WinningScore(val value: Int) {
        fun matches(score: Score): Boolean {
            return score.value and value == value
        }
    }
}


class TicTacToeGame(val board: Map<Int, TTT.Placement>, override val currentPlayer: TTT.Player) : Game<TTT.Player, Int> {

    override val possibleMoves: List<Int> by lazy {
        board.flatMapTo(mutableListOf<Int>(), { (k, v) -> if (v == TTT.Placement.Empty) listOf(k) else emptyList() })
    }
    override val random: Boolean = false
    override val winner: TTT.Player? by lazy {
        val scores = mutableMapOf<TTT.Player, TTT.Score>(TTT.Placement.XPlayer to TTT.Score(0), TTT.Placement.OPlayer to TTT.Score(0))
        board.forEach { idx, u ->
            when (u) {
                is TTT.Player ->
                        scores.put(u, scores[u]!!.let { it.copy(it.value + BoardScore[idx]!!.value) })
                else -> Unit
            }
        }

        arrayOf<TTT.Player>(TTT.Placement.OPlayer, TTT.Placement.XPlayer).find { p -> WinningScores.any { it.matches(scores[p]!!) }}
    }


    override fun withMove(move: Int): Game<TTT.Player, Int> {
        return TicTacToeGame(board + (move to (currentPlayer as TTT.Placement)), otherPlayer(currentPlayer))
    }

    companion object {
        val WinningScores: List<TTT.WinningScore> =
                listOf(7, 56, 448, 73, 146, 292, 273, 84).map { TTT.WinningScore(it) }

        val BoardScore: Map<Int, TTT.Score> =
                listOf(1, 2, 4, 8, 16, 32, 64, 128, 256).mapIndexed { idx, score -> idx to TTT.Score(score) }.toMap()

        fun Board(vararg ps: TTT.Placement): Map<Int, TTT.Placement> {
            return ps.mapIndexed { idx, placement -> idx to placement }.toMap()
        }

        val EmptyBoard: Map<Int, TTT.Placement> = Board(*Array(9, { _ -> TTT.Placement.Empty }))

        fun otherPlayer(player: TTT.Player): TTT.Player =
                if (player == TTT.Placement.XPlayer) TTT.Placement.OPlayer else TTT.Placement.XPlayer
    }


}