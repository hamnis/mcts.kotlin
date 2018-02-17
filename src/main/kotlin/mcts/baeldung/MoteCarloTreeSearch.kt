package mcts.baeldung


object MonteCarloTreeSearch {
    private val WIN_SCORE = 10

    private fun millisForCurrentLevel(level: Int): Int = 2 * (level - 1) + 1

    fun findNextMove(board: Board, playerNo: Int, level: Int = 3): Board {
        val start = System.currentTimeMillis()
        val end = start + 60 * millisForCurrentLevel(level)

        val opponent = 3 - playerNo
        val rootNode = Node()
        rootNode.state.board = board
        rootNode.state.playerNo = opponent

        while (System.currentTimeMillis() < end) {
            // Phase 1 - Selection
            val promisingNode = selectPromisingNode(rootNode)
            // Phase 2 - Expansion
            if (promisingNode.state.board.checkStatus() == Board.IN_PROGRESS)
                expandNode(promisingNode)

            // Phase 3 - Simulation
            var nodeToExplore = promisingNode
            if (promisingNode.children.isNotEmpty()) {
                nodeToExplore = promisingNode.randomChildNode
            }
            val playoutResult = simulateRandomPlayout(nodeToExplore, opponent)
            // Phase 4 - Update
            backPropogation(nodeToExplore, playoutResult)
        }

        val winnerNode = rootNode.childWithMaxScore
        return winnerNode.state.board
    }

    private fun selectPromisingNode(rootNode: Node): Node {
        var node = rootNode
        while (node.children.isNotEmpty()) {
            node = UCT.findBestNodeWithUCT(node)
        }
        return node
    }

    private fun expandNode(node: Node) {
        val possibleStates = node.state.allPossibleStates
        possibleStates.forEach { state ->
            val newNode = Node(state, node)
            newNode.state.playerNo = (node.state.opponent)
            node.children.add(newNode)
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

    private fun simulateRandomPlayout(node: Node, opponent: Int): Int {
        val tempNode = node.copy()
        val tempState = tempNode.state
        var boardStatus = tempState.board.checkStatus()

        if (boardStatus == opponent) {
            tempNode.parent!!.state.winScore = (Integer.MIN_VALUE).toDouble()
            return boardStatus
        }
        while (boardStatus == Board.IN_PROGRESS) {
            tempState.togglePlayer()
            tempState.randomPlay()
            boardStatus = tempState.board.checkStatus()
        }

        return boardStatus
    }
}
