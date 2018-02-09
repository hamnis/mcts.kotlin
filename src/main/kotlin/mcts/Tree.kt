package mcts

sealed class Tree

class Branch(children: List<Tree>) : Tree()
class Leaf : Tree()
