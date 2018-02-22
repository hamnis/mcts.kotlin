package mcts

import java.util.Collections
import java.util.Comparator


class Node(val state: State, val parent: Node? = null, val children: MutableList<Node> = mutableListOf()) {
    val randomChildNode: Node
        get() {
            val noOfPossibleMoves = this.children.size
            val selectRandom = (Math.random() * (noOfPossibleMoves - 1 + 1)).toInt()
            return this.children[selectRandom]
        }

    val childWithMaxScore: Node
        get() = Collections.max<Node>(this.children, Comparator.comparing<Node, Int> { c -> c.state.visitCount })

    fun copy(): Node {
        val children = this.children.mapTo(mutableListOf()) { it.copy() }
        return Node(this.state.copy(), this.parent, children)
    }
}
