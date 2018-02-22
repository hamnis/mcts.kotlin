package mcts

sealed class Status {
    object InProgress : Status()
    object Draw : Status()
    data class Win(val player: Player) : Status()
}

