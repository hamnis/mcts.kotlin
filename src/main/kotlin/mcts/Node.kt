package mcts

class Node(val state: State, val parent: Node? = null, val children: MutableList<Node> = mutableListOf()) {
    fun randomChildNode(random: Random): Node {
        val noOfPossibleMoves = this.children.size
        val selectRandom = random.nextInt(noOfPossibleMoves)
        return this.children[selectRandom]
    }

    val childWithMaxScore: Node
        get() = this.children.maxBy { it.state.visitCount }!!

    fun copy(): Node {
        val children = this.children.mapTo(mutableListOf()) { it.copy() }
        return Node(this.state.copy(), this.parent, children)
    }
}
