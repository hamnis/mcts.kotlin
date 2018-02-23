package mcts.mcts

object UCT {

    fun uctValue(totalVisit: Int, nodeWinScore: Double, nodeVisit: Int): Double {
        return if (nodeVisit == 0) {
            Integer.MAX_VALUE.toDouble()
        } else nodeWinScore / nodeVisit.toDouble() + 1.41 * Math.sqrt(Math.log(totalVisit.toDouble()) / nodeVisit.toDouble())
    }

    internal fun findBestNodeWithUCT(node: Node): Node {
        val parentVisit = node.visitCount
        return node.children.maxBy { uctValue(parentVisit, it.winScore, it.visitCount) }!!
    }
}
