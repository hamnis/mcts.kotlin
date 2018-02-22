package mcts

class TicTacToeBoard(private val boardValues: Array<IntArray> = Array(DEFAULT_BOARD_SIZE) { IntArray(DEFAULT_BOARD_SIZE) }, private var totalMoves: Int = 0) : Board<Int> {

    override val emptyPositions: List<Position>
        get() {
            val size = this.boardValues.size
            val emptyPositions = mutableListOf<Position>()
            for (i in 0 until size) {
                for (j in 0 until size) {
                    if (boardValues[i][j] == 0)
                        emptyPositions.add(Position(i, j))
                }
            }
            return emptyPositions
        }

    override fun performMove(player: Int, p: Position): Board<Int> {
        val copy = TicTacToeBoard(Array(boardValues.size, { boardValues[it].copyOf() }), totalMoves + 1)
        copy.boardValues[p.x][p.y] = player
        return copy
    }

    override fun checkStatus(): Status<Int> {
        val boardSize = boardValues.size
        val maxIndex = boardSize - 1
        val diag1 = IntArray(boardSize)
        val diag2 = IntArray(boardSize)

        for (i in 0 until boardSize) {
            val row = boardValues[i]
            val col = IntArray(boardSize)
            for (j in 0 until boardSize) {
                col[j] = boardValues[j][i]
            }

            val checkRowForWin = checkForWin(row)
            if (checkRowForWin != 0)
                return Status.Win(checkRowForWin)

            val checkColForWin = checkForWin(col)
            if (checkColForWin != 0)
                return Status.Win(checkColForWin)

            diag1[i] = boardValues[i][i]
            diag2[i] = boardValues[maxIndex - i][i]
        }

        val checkDia1gForWin = checkForWin(diag1)
        if (checkDia1gForWin != 0)
            return Status.Win(checkDia1gForWin)

        val checkDiag2ForWin = checkForWin(diag2)
        if (checkDiag2ForWin != 0)
            return Status.Win(checkDiag2ForWin)

        return if (emptyPositions.isNotEmpty()) Status.InProgress else Status.Draw
    }

    private fun checkForWin(row: IntArray): Int {
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
        return if (isEqual) previous else 0
    }

    override fun opponent(player: Int) = 3 - player

    override fun printBoard() {
        fun toSymbol(pos: Int): String = when(pos) {
            1 -> "X"
            2 -> "O"
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
            Status.Win(P1) -> println("Player 1 wins")
            Status.Win(P2) -> println("Player 2 wins")
            Status.Draw -> println("Game Draw")
            Status.InProgress -> println("Game In progress")
        }
    }

    companion object {
        val DEFAULT_BOARD_SIZE = 3
        val P1 = 1
        val P2 = 2
    }
}
