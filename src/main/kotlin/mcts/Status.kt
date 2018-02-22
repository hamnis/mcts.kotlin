package mcts

sealed class Status<in Player> {
    object InProgress : Status<Any>()
    object Draw : Status<Any>()
    data class Win<Player>(val player: Player) : Status<Player>()
}

