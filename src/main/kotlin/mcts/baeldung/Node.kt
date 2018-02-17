package mcts.baeldung

import java.util.ArrayList
import java.util.Collections
import java.util.Comparator


class Node {
    var state: State
    var parent: Node? = null
    internal var childArray: MutableList<Node>

    val randomChildNode: Node
        get() {
            val noOfPossibleMoves = this.childArray.size
            val selectRandom = (Math.random() * (noOfPossibleMoves - 1 + 1)).toInt()
            return this.childArray[selectRandom]
        }

    val childWithMaxScore: Node
        get() = Collections.max<Node>(this.childArray, Comparator.comparing<Node, Int> { c -> c.state.visitCount })

    constructor() {
        this.state = State()
        childArray = ArrayList()
    }

    constructor(state: State) {
        this.state = state
        childArray = ArrayList()
    }

    constructor(state: State, parent: Node, childArray: MutableList<Node>) {
        this.state = state
        this.parent = parent
        this.childArray = childArray
    }

    constructor(node: Node) {
        this.childArray = ArrayList()
        this.state = State(node.state)
        if (node.parent != null)
            this.parent = node.parent
        val childArray = node.getChildArray()
        for (child in childArray) {
            this.childArray.add(Node(child))
        }
    }

    fun getChildArray(): List<Node> {
        return childArray
    }

    fun setChildArray(childArray: MutableList<Node>) {
        this.childArray = childArray
    }

}
