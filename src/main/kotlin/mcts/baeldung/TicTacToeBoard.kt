package mcts.baeldung

class TicTacToeBoard(private val boardValues: Array<IntArray> = Array(DEFAULT_BOARD_SIZE) { IntArray(DEFAULT_BOARD_SIZE) }, private var totalMoves: Int = 0) : Board {

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

    override fun copy(): TicTacToeBoard {
        val boardLength = this.boardValues.size
        val newBoardValues = Array(boardLength) { IntArray(boardLength) }
        val boardValues = this.boardValues
        val n = boardValues.size
        for (i in 0 until n) {
            val m = boardValues[i].size
            for (j in 0 until m) {
                newBoardValues[i][j] = boardValues[i][j]
            }
        }
        return TicTacToeBoard(newBoardValues)
    }

    override fun performMove(player: Int, p: Position) {
        this.totalMoves++
        boardValues[p.x][p.y] = player
    }

    override fun checkStatus(): Int {
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
                return checkRowForWin

            val checkColForWin = checkForWin(col)
            if (checkColForWin != 0)
                return checkColForWin

            diag1[i] = boardValues[i][i]
            diag2[i] = boardValues[maxIndex - i][i]
        }

        val checkDia1gForWin = checkForWin(diag1)
        if (checkDia1gForWin != 0)
            return checkDia1gForWin

        val checkDiag2ForWin = checkForWin(diag2)
        if (checkDiag2ForWin != 0)
            return checkDiag2ForWin

        return if (emptyPositions.isNotEmpty()) IN_PROGRESS else DRAW
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
        return if (isEqual)
            previous
        else
            0
    }

    override fun printBoard() {
        val size = this.boardValues.size
        for (i in 0 until size) {
            for (j in 0 until size) {
                print(boardValues[i][j].toString() + " ")
            }
            println()
        }
    }

    override fun printStatus() {
        when (this.checkStatus()) {
            P1 -> println("Player 1 wins")
            P2 -> println("Player 2 wins")
            DRAW -> println("Game Draw")
            IN_PROGRESS -> println("Game In progress")
        }
    }

    companion object {

        val DEFAULT_BOARD_SIZE = 3

        val IN_PROGRESS = -1
        val DRAW = 0
        val P1 = 1
        val P2 = 2
    }
}

