package mcts.baeldung

import java.util.ArrayList

class Board {
    var boardValues: Array<IntArray>
    internal var totalMoves: Int = 0

    val emptyPositions: List<Position>
        get() {
            val size = this.boardValues.size
            val emptyPositions = ArrayList<Position>()
            for (i in 0 until size) {
                for (j in 0 until size) {
                    if (boardValues[i][j] == 0)
                        emptyPositions.add(Position(i, j))
                }
            }
            return emptyPositions
        }

    constructor() {
        boardValues = Array(DEFAULT_BOARD_SIZE) { IntArray(DEFAULT_BOARD_SIZE) }
    }

    constructor(boardSize: Int) {
        boardValues = Array(boardSize) { IntArray(boardSize) }
    }

    constructor(boardValues: Array<IntArray>) {
        this.boardValues = boardValues
    }

    constructor(boardValues: Array<IntArray>, totalMoves: Int) {
        this.boardValues = boardValues
        this.totalMoves = totalMoves
    }

    constructor(board: Board) {
        val boardLength = board.boardValues.size
        this.boardValues = Array(boardLength) { IntArray(boardLength) }
        val boardValues = board.boardValues
        val n = boardValues.size
        for (i in 0 until n) {
            val m = boardValues[i].size
            for (j in 0 until m) {
                this.boardValues[i][j] = boardValues[i][j]
            }
        }
    }

    fun performMove(player: Int, p: Position) {
        this.totalMoves++
        boardValues[p.x][p.y] = player
    }

    fun checkStatus(): Int {
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

        return if (emptyPositions.size > 0)
            IN_PROGRESS
        else
            DRAW
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

    fun printBoard() {
        val size = this.boardValues.size
        for (i in 0 until size) {
            for (j in 0 until size) {
                print(boardValues[i][j].toString() + " ")
            }
            println()
        }
    }

    fun printStatus() {
        when (this.checkStatus()) {
            P1 -> println("Player 1 wins")
            P2 -> println("Player 2 wins")
            DRAW -> println("Game Draw")
            IN_PROGRESS -> println("Game In rogress")
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