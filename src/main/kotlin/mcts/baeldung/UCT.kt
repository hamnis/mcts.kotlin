package mcts.baeldung

import java.util.*


object UCT {

    fun uctValue(totalVisit: Int, nodeWinScore: Double, nodeVisit: Int): Double {
        return if (nodeVisit == 0) {
            Integer.MAX_VALUE.toDouble()
        } else nodeWinScore / nodeVisit.toDouble() + 1.41 * Math.sqrt(Math.log(totalVisit.toDouble()) / nodeVisit.toDouble())
    }

    internal fun findBestNodeWithUCT(node: Node): Node {
        val parentVisit = node.state.visitCount
        return Collections.max(
                node.getChildArray(),
                Comparator.comparing<Node, Double> { c -> uctValue(parentVisit, c.state.winScore, c.state.visitCount) })
    }
}