package mcts


object UTC {

    fun value(totalVisit: Int, winScore: Double, visits: Int): Double {
        return if (visits == 0) Integer.MAX_VALUE.toDouble() else {
            winScore / visits.toDouble() + 1.41 * Math.sqrt(Math.log(totalVisit.toDouble()) / visits.toDouble())
        }
    }

    fun findBestNode(node: TreeNode): TreeNode {
/*        val parentVisit = node.getState().getVisitCount()
        return Collections.max(
                node.getChildArray(),
                Comparator.comparing<T, U> { c ->
                    uctValue(parentVisit,
                            c.getState().getWinScore(), c.getState().getVisitCount())
                })*/
        return node
    }
}