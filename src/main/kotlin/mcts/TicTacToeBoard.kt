package mcts

sealed class Placement {
    object Empty : Placement()
    data class Occupied(val player: Player) : Placement()
}

class TicTacToeBoard(private val boardValues: Array<Array<Placement>> = boardofSize(DEFAULT_BOARD_SIZE), override val currentPlayer: Player, private val totalMoves: Int = 0) : Board {
    private fun emptyPositions(): List<Position> {
        val size = this.boardValues.size
        val emptyPositions = mutableListOf<Position>()
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (boardValues[i][j] == Placement.Empty)
                    emptyPositions.add(Position(i, j))
            }
        }
        return emptyPositions
    }

    override fun withMove(player: Player, p: Position): Board {
        val copy = TicTacToeBoard(Array(boardValues.size, { boardValues[it].copyOf() }), player,totalMoves + 1)
        copy.boardValues[p.x][p.y] = Placement.Occupied(player)
        return copy
    }

    override fun withPlayer(player: Player): Board {
        return TicTacToeBoard(boardValues, player, totalMoves)
    }

    override fun checkStatus(): Status {
        val boardSize = boardValues.size
        val maxIndex = boardSize - 1
        val diag1 = emptyPlacements(boardSize)
        val diag2 = emptyPlacements(boardSize)

        for (i in 0 until boardSize) {
            val row = boardValues[i]
            val col = emptyPlacements(boardSize)
            for (j in 0 until boardSize) {
                col[j] = boardValues[j][i]
            }

            val checkRowForWin = checkForWin(row)
            if (checkRowForWin is Placement.Occupied)
                return Status.Win(checkRowForWin.player)

            val checkColForWin = checkForWin(col)
            if (checkColForWin is Placement.Occupied)
                return Status.Win(checkColForWin.player)

            diag1[i] = boardValues[i][i]
            diag2[i] = boardValues[maxIndex - i][i]
        }

        val checkDia1gForWin = checkForWin(diag1)
        if (checkDia1gForWin is Placement.Occupied)
            return Status.Win(checkDia1gForWin.player)

        val checkDiag2ForWin = checkForWin(diag2)
        if (checkDiag2ForWin is Placement.Occupied)
            return Status.Win(checkDiag2ForWin.player)

        val positions = emptyPositions()
        return if (positions.isNotEmpty()) Status.InProgress(positions) else Status.Draw
    }

    private fun checkForWin(row: Array<Placement>): Placement {
        var isEqual = true
        val size = row.size
        var previous = row[0]
        for (i in 0 until size) {
            if (previous != row[i]) {
                isEqual = false
                break
            }
            previous = row[i]
        }
        return if (isEqual) previous else Placement.Empty
    }

    override fun printBoard() {
        fun toSymbol(pos: Placement): String = when (pos) {
            Placement.Occupied(Player.One) -> "X"
            Placement.Occupied(Player.Two) -> "O"
            else -> "."
        }

        val size = this.boardValues.size
        for (i in 0 until size) {
            for (j in 0 until size) {
                print(toSymbol(boardValues[i][j]) + " ")
            }
            println()
        }
    }

    override fun printStatus() {
        when (this.checkStatus()) {
            Status.Win(Player.One) -> println("Player 1 wins")
            Status.Win(Player.Two) -> println("Player 2 wins")
            Status.Draw -> println("Game Draw")
            is Status.InProgress -> println("Game In progress")
            else -> IllegalStateException("Not a valid status")
        }
        println(" in $totalMoves moves")
    }

    companion object {
        val DEFAULT_BOARD_SIZE = 3

        fun emptyPlacements(size: Int) = Array<Placement>(size, { Placement.Empty })

        private fun boardofSize(size: Int) = Array(size, { emptyPlacements(size) } )

        fun ofSize(size: Int): TicTacToeBoard {
            return TicTacToeBoard(boardofSize(size), Player.One)
        }
    }
}

