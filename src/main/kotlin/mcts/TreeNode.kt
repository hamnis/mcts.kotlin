package mcts

import java.util.Random

class TreeNode {
    companion object {
        val random = Random()
        val nAction = 5
        val epsilon = (1e-6).toDouble()

        fun utc(us: TreeNode, them: TreeNode): Double {
            return them.totValue / (them.visits + epsilon) +
                    Math.sqrt(Math.log(us.visits + 1) / (them.visits + epsilon)) +
                    random.nextDouble() * epsilon
        }
    }

    var children = emptyArray<TreeNode>()
    var visits = 0.0
    var totValue = 0.0

    fun selectAction() {
        var visited = emptyList<TreeNode>()
        var curr = this
        visited += this
        while (!curr.leaf()) {
            curr = curr.select()
            visited += curr
        }
        curr.expand()
        val newNode = curr.select()
        visited += newNode
        val value = rollout(newNode)
        visited.forEach{it.updateStats(value)}
    }

    fun expand() {
        children = Array(nAction, {i -> TreeNode()})
    }

    fun select(): TreeNode {
        return children.fold(this to Double.MIN_VALUE, {(selected, bestValue), current ->
            val utcValue = utc(this, current)
            if (utcValue > bestValue) {
                current to utcValue
            } else {
                selected to bestValue
            }
        }).first
    }

    fun leaf() = children.isEmpty()

    fun rollout(tn: TreeNode) = random.nextInt(2).toDouble()

    fun updateStats(value: Double) {
        visits += 1
        totValue += value
    }

    fun arity(): Int = children.size
}