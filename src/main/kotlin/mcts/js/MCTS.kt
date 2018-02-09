package mcts.js

import java.util.Random

interface Game<Player, Move> {
    fun withMove(move: Move): Game<Player, Move>
    val possibleMoves: List<Move>
    val random: Boolean
    val currentPlayer: Player
    val winner: Player?
}


class MCTS<Player, Move>(var game: Game<Player, Move>, val rounds: Int, val random: Random) {
    fun select(node: Node<Player, Move>): Node<Player, Move> {
        node.visits += 1
        return when {
            node.children.isEmpty() -> node
            else -> select(node.children.maxWith(Node.comparator(random))!!)
        }
    }

    fun backPropagate(node: Node<Player, Move>, winner: Player?) {
        if (node.game.currentPlayer == winner) {
            node.wins += 1
        }
        node.parent?.let { backPropagate(it, winner) }
    }

    fun selectMove(): Move? {
        var round = 0
        val root = Node.root(game)

        while(round < rounds) {
            val currentNode = select(root)
            backPropagate(currentNode, currentNode.game.winner)
            round += 1
        }

        return root.children.maxBy { it.visits }?.move
    }

    fun performMove(move: Move) {
        game = game.withMove(move)
    }
}


