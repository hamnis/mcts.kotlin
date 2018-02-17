package mcts.baeldung

class Tree {
    var root: Node

    constructor() {
        root = Node()
    }

    constructor(root: Node) {
        this.root = root
    }

    fun addChild(parent: Node, child: Node) {
        parent.children.add(child)
    }

}