package mcts.baeldung


class MonteCarloTreeSearch {
    var level: Int = 0
    private var oponent: Int = 0

    private val millisForCurrentLevel: Int
        get() = 2 * (this.level - 1) + 1

    init {
        this.level = 3
    }

    fun findNextMove(board: Board, playerNo: Int): Board {
        val start = System.currentTimeMillis()
        val end = start + 60 * millisForCurrentLevel

        oponent = 3 - playerNo
        val tree = Tree()
        val rootNode = tree.root
        rootNode.state.board = (board)
        rootNode.state.playerNo = (oponent)

        while (System.currentTimeMillis() < end) {
            // Phase 1 - Selection
            val promisingNode = selectPromisingNode(rootNode)
            // Phase 2 - Expansion
            if (promisingNode.state.board!!.checkStatus() == Board.IN_PROGRESS)
                expandNode(promisingNode)

            // Phase 3 - Simulation
            var nodeToExplore = promisingNode
            if (promisingNode.getChildArray().size > 0) {
                nodeToExplore = promisingNode.randomChildNode
            }
            val playoutResult = simulateRandomPlayout(nodeToExplore)
            // Phase 4 - Update
            backPropogation(nodeToExplore, playoutResult)
        }

        val winnerNode = rootNode.childWithMaxScore
        tree.root = winnerNode
        return winnerNode.state.board!!
    }

    private fun selectPromisingNode(rootNode: Node): Node {
        var node = rootNode
        while (node.getChildArray().size != 0) {
            node = UCT.findBestNodeWithUCT(node)
        }
        return node
    }

    private fun expandNode(node: Node) {
        val possibleStates = node.state.allPossibleStates
        possibleStates.forEach { state ->
            val newNode = Node(state)
            newNode.parent = node
            newNode.state.playerNo = (node.state.opponent)
            node.childArray.add(newNode)
        }
    }

    private fun backPropogation(nodeToExplore: Node, playerNo: Int) {
        var tempNode: Node? = nodeToExplore
        while (tempNode != null) {
            tempNode.state.incrementVisit()
            if (tempNode.state.playerNo == playerNo)
                tempNode.state.addScore(WIN_SCORE.toDouble())
            tempNode = tempNode.parent
        }
    }

    private fun simulateRandomPlayout(node: Node): Int {
        val tempNode = Node(node)
        val tempState = tempNode.state
        var boardStatus = tempState.board!!.checkStatus()

        if (boardStatus == oponent) {
            tempNode.parent!!.state.winScore = (Integer.MIN_VALUE).toDouble()
            return boardStatus
        }
        while (boardStatus == Board.IN_PROGRESS) {
            tempState.togglePlayer()
            tempState.randomPlay()
            boardStatus = tempState.board!!.checkStatus()
        }

        return boardStatus
    }

    companion object {

        private val WIN_SCORE = 10
    }

}
