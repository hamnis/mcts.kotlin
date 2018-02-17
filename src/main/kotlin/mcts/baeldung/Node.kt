package mcts.baeldung

import java.util.Collections
import java.util.Comparator


class Node(var state: State = State(), var parent: Node? = null, val children: MutableList<Node> = mutableListOf()) {

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
        return Node(State(this.state), this.parent, children)
    }

/*    fun getChildArray(): List<Node> {
        return children
    }*/

    /*fun setChildArray(childArray: MutableList<Node>) {
        this.children = childArray
    }*/

    companion object {

    }
}
