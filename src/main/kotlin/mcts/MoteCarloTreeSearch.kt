package mcts

import mcts.util.Duration
import mcts.util.Random

object MonteCarloTreeSearch {
    private val WIN_SCORE = 10.0

    fun findNextMove(board: Board, player: Player, random: Random, duration: Duration): Board {
        val start = System.currentTimeMillis()
        val end = start + duration.toMillis()

        val opponent = player.opponent
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
                nodeToExplore = promisingNode.randomChildNode(random)
            }
            val playoutResult = simulateRandomPlayout(nodeToExplore, opponent, random)
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
            newNode.state.player = node.state.player.opponent
            node.children.add(newNode)
        }
    }

    private fun backPropogation(nodeToExplore: Node, player: Status) {
        var tempNode: Node? = nodeToExplore
        while (tempNode != null) {
            tempNode.state.incrementVisit()
            if (player is Status.Win && tempNode.state.player == player.player)
                tempNode.state.addScore(WIN_SCORE)
            tempNode = tempNode.parent
        }
    }

    private fun simulateRandomPlayout(node: Node, opponent: Player, random: Random): Status {
        val tempNode = node.copy()
        val tempState = tempNode.state
        var boardStatus = tempState.board.checkStatus()

        if (boardStatus == Status.Win(opponent)) {
            tempNode.parent?.state?.winScore = State.NO_WIN_SCORE
            return boardStatus
        }
        while (boardStatus == Status.InProgress) {
            tempState.togglePlayer()
            tempState.randomPlay(random)
            boardStatus = tempState.board.checkStatus()
        }

        return boardStatus
    }
}
