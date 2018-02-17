package mcts.baeldung

import java.util.*


object UCT {

    fun uctValue(totalVisit: Int, nodeWinScore: Double, nodeVisit: Int): Double {
        return if (nodeVisit == 0) {
            Integer.MAX_VALUE.toDouble()
        } else nodeWinScore / nodeVisit.toDouble() + 1.41 * Math.sqrt(Math.log(totalVisit.toDouble()) / nodeVisit.toDouble())
    }

    internal fun <Player> findBestNodeWithUCT(node: Node<Player>): Node<Player> {
        val parentVisit = node.state.visitCount
        return Collections.max(
                node.children,
                Comparator.comparing<Node<Player>, Double> { c -> uctValue(parentVisit, c.state.winScore, c.state.visitCount) })
    }
}