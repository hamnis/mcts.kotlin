package javabin

enum class Player {
    One, Two;

    val opponent: Player by lazy {
        when (this) {
            One -> Two
            Two -> One
        }
    }
}