package mcts.js

import java.util.Random

class Node<Player, Move>(val game: Game<Player, Move>, val parent: Node<Player, Move>?, val move: Move?, var visits: Int, var wins: Int) {

    fun childWithMove(game: Game<Player, Move>, move: Move) =
            Node(game, this, move, 0, 0)

    val children by lazy {
        val newGame = if (move != null) game.withMove(move) else game
        newGame.possibleMoves.map { childWithMove(newGame, it) }
    }

    override fun toString(): String =
            "[A: $move; " + "W/V: $wins/$visits} = ${wins.toDouble() / visits}; " + "C: ${children.size}"

    companion object {
        fun <Player, Move> root(game: Game<Player, Move>): Node<Player, Move> =
                Node(game, null, null, 0, 0)

        fun <Player, Move> comparator(random: Random): Comparator<Node<Player, Move>> {
            val base = Comparator.comparing<Node<Player, Move>, Double> {
                when {
                    it.game.random -> random.nextInt(2).toDouble()
                    it.visits == 0 -> Double.POSITIVE_INFINITY
                    it.parent != null ->
                        // See https://en.wikipedia.org/wiki/Monte_Carlo_tree_search#Exploration_and_exploitation
                        (it.wins / it.visits) + Math.sqrt(2 * Math.log(it.parent.visits.toDouble()) / it.visits)
                    else -> 0.0
                }
            }
            return Comparator { n1, n2 ->
                val compare = base.compare(n1, n2)
                when (compare) {
                    0 -> random.nextInt(2)
                    else -> compare
                }
            }
        }
    }
}