package mcts.tutorial


object UCT {

    fun value(totalVisit: Int, winScore: Double, visits: Int): Double {
        return if (visits == 0) Integer.MAX_VALUE.toDouble() else {
            winScore / visits.toDouble() + 1.41 * Math.sqrt(Math.log(totalVisit.toDouble()) / visits.toDouble())
        }
    }

    fun findBestNode(node: Node): Node {
        val parentVisits = node.state.visits
        return node.children.maxWith(Comparator.comparingDouble {
            value(parentVisits, it.state.win, it.state.visits)
        })!!
    }
}