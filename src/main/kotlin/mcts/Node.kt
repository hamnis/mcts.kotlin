package mcts

import java.util.Collections
import java.util.Comparator


class Node<Player>(val state: State<Player>, val parent: Node<Player>? = null, val children: MutableList<Node<Player>> = mutableListOf()) {
    val randomChildNode: Node<Player>
        get() {
            val noOfPossibleMoves = this.children.size
            val selectRandom = (Math.random() * (noOfPossibleMoves - 1 + 1)).toInt()
            return this.children[selectRandom]
        }

    val childWithMaxScore: Node<Player>
        get() = Collections.max<Node<Player>>(this.children, Comparator.comparing<Node<Player>, Int> { c -> c.state.visitCount })

    fun copy(): Node<Player> {
        val children = this.children.mapTo(mutableListOf()) { it.copy() }
        return Node(this.state.copy(), this.parent, children)
    }
}
