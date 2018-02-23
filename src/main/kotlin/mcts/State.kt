package mcts

class State(var board: Board, var player: Player) {

    fun allPossibleStates(player: Player, availablePositions: List<Position>): List<State> {
        return availablePositions.map { State(this.board.withMove(player, it), player) }
    }

    fun copy(): State = State(this.board, this.player)
}
