package mcts.baeldung


class MonteCarloTreeSearch<Player> {
    private val WIN_SCORE = 10.0

    private fun millisForCurrentLevel(level: Int): Int = 2 * (level - 1) + 1

    fun findNextMove(board: Board<Player>, player: Player, level: Int = 3): Board<Player> {
        val start = System.currentTimeMillis()
        val end = start + 60 * millisForCurrentLevel(level)

        val opponent = board.opponent(player)
        val rootNode = Node(State(board, opponent))

        while (System.currentTimeMillis() < end) {
            // Phase 1 - Selection
            val promisingNode = selectPromisingNode(rootNode)
            // Phase 2 - Expansion
            if (promisingNode.state.board.checkStatus() == Status.InProgress)
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

    private fun selectPromisingNode(rootNode: Node<Player>): Node<Player> {
        var node = rootNode
        while (node.children.isNotEmpty()) {
            node = UCT.findBestNodeWithUCT(node)
        }
        return node
    }

    private fun expandNode(node: Node<Player>) {
        val possibleStates = node.state.allPossibleStates
        possibleStates.forEach { state ->
            val newNode = Node(state, node)
            newNode.state.player = state.board.opponent(node.state.player)
            node.children.add(newNode)
        }
    }

    private fun backPropogation(nodeToExplore: Node<Player>, player: Status<Player>) {
        var tempNode: Node<Player>? = nodeToExplore
        while (tempNode != null) {
            tempNode.state.incrementVisit()
            if (player is Status.Win<Player> && tempNode.state.player == player.player)
                tempNode.state.addScore(WIN_SCORE)
            tempNode = tempNode.parent
        }
    }

    private fun simulateRandomPlayout(node: Node<Player>, opponent: Player): Status<Player> {
        val tempNode = node.copy()
        val tempState = tempNode.state
        var boardStatus = tempState.board.checkStatus()

        if (boardStatus == Status.Win(opponent)) {
            tempNode.parent?.state?.winScore = State.NO_WIN_SCORE
            return boardStatus
        }
        while (boardStatus == Status.InProgress) {
            tempState.togglePlayer()
            tempState.randomPlay()
            boardStatus = tempState.board.checkStatus()
        }

        return boardStatus
    }
}
