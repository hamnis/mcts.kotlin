package mcts

object UCT {

    fun uctValue(totalVisit: Int, nodeWinScore: Double, nodeVisit: Int): Double {
        return if (nodeVisit == 0) {
            Integer.MAX_VALUE.toDouble()
        } else nodeWinScore / nodeVisit.toDouble() + 1.41 * Math.sqrt(Math.log(totalVisit.toDouble()) / nodeVisit.toDouble())
    }

    internal fun <Player> findBestNodeWithUCT(node: Node<Player>): Node<Player> {
        val parentVisit = node.state.visitCount
        return node.children.maxBy { uctValue(parentVisit, it.state.winScore, it.state.visitCount) }!!
    }
}
