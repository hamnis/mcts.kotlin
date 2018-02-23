package javabin

sealed class Status {
    data class InProgress(val positions: List<Position>) : Status()
    abstract class Terminal : Status()
    object Draw : Terminal()
    data class Win(val player: Player) : Terminal()
}

