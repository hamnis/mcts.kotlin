package mcts.tutorial


data class Node(val state: State, val parent: Node?, val children: MutableList<Node>) {
    fun clone(): Node {
        return Node(state.copy(), parent, children.map { it.clone() }.toMutableList())
    }

    val randomChildNode: Node
        get() {
            val noOfPossibleMoves = this.children.size
            val selectRandom = (Math.random() * (noOfPossibleMoves - 1 + 1)).toInt()
            return this.children[selectRandom]
        }

    fun childWithMaxScore(): Node? {
        return children.maxBy { it.state.visits }
    }
}

class Tree(val root: Node)


data class State(val board: Board, var player: Int, var visits: Int, var win: Double) {
    fun allPossibleStates(): List<State> {
        var possibleStates = emptyList<State>()
        val availablePositions = this.board.emptyPositions
        availablePositions.forEach { p ->
            val newState = State(this.board, 3 - player, 0, 0.0)
            newState.board.performMove(newState.player, p)
            possibleStates += newState
        }
        return possibleStates
    }

    fun randomPlay() {
        val availablePositions = this.board.emptyPositions
        val totalPossibilities = availablePositions.size
        val selectRandom = (Math.random() * (totalPossibilities - 1 + 1)).toInt()
        this.board.performMove(this.player, availablePositions.get(selectRandom))
    }

    /*fun withPlayer(opponent: Int): State {
        return copy(player = opponent)
    }*/

    fun incrementVisit() {
        visits += 1
    }

    fun addScore(score: Int) {
        if (score != Int.MIN_VALUE) {
            win += score
        }
    }

    fun togglePlayer() {
        player = 3 - player
    }
}

data class Position(val x: Int, val y: Int)

interface Board {
    companion object {
        val IN_PROGRESS = -1
    }

    fun checkStatus(): Int

    val emptyPositions: List<Position>
    fun performMove(playerNo: Int, p: Position)
    fun printStatus()
}

object MCTS {
    val WIN_SCORE = 10

    val level: Int = 3
    //val opponent: Int = 0

    fun findNextMove(board: Board, player: Int): Board {
        val end = System.currentTimeMillis() + 60 + (2 * (level-1) + 1)
        val opponent = 3 - player
        val tree = Node(State(board, opponent, 0, 0.0), null, mutableListOf())
        while (System.currentTimeMillis() < end) {
            val promisingNode = selectPromisingNode(tree)
            if (promisingNode.state.board.checkStatus() == Board.IN_PROGRESS) {
                expandNode(promisingNode)
            }
            var nodeToExplore = promisingNode
            if (!nodeToExplore.children.isEmpty()) nodeToExplore = promisingNode.randomChildNode
            val playoutResult = simulateRandomPlayout(nodeToExplore, opponent)
            backPropogation(nodeToExplore, playoutResult)
        }

        val winnerNode = tree.childWithMaxScore()
        return winnerNode?.state?.board.let { if (it != null) it else tree.state.board }
    }

    private fun backPropogation(node: Node, player: Int) {
        var tempNode: Node? = node
        while (tempNode != null) {
            tempNode.state.incrementVisit()
            if (tempNode.state.player == player) {
                tempNode.state.addScore(WIN_SCORE)
            }
            tempNode = tempNode.parent
        }
    }

    private fun simulateRandomPlayout(node: Node, opponent: Int): Int {
        val tempNode = node.clone()
        val tempState = tempNode.state
        var boardStatus = tempState.board.checkStatus()
        if (boardStatus == opponent && tempNode.parent != null) {
            tempNode.parent.state.win = Int.MIN_VALUE.toDouble()
            return boardStatus
        }
        while (boardStatus == Board.IN_PROGRESS) {
            tempState.togglePlayer()
            tempState.randomPlay()
            boardStatus = tempState.board.checkStatus()
        }
        return boardStatus
    }

    private fun expandNode(node: Node) {
        val possibleStates = node.state.allPossibleStates()
        possibleStates.forEach { state ->
            val newNode = Node(state.copy(player = 3 - node.state.player), node, mutableListOf())
            node.children + newNode
        }
    }

    private fun selectPromisingNode(node: Node): Node {
        var n = node
        while (n.children.isNotEmpty()) {
            n = UCT.findBestNode(n)
        }
        return n
    }
}