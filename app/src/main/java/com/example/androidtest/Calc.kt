import kotlin.math.pow

var ptrL = 0
var ptrOp = 0
var ptrVal = 0
var arrayLex = arrayOfNulls<String>(200)
var opStack = arrayOfNulls<String>(200)
var valStack = arrayOfNulls<String>(200)

fun priorityRank(o: String?): Int {
    var rank = -1
    when (o) {
        "(" -> rank = 0
        "+", "-" -> rank = 1
        "*", "/" -> rank = 2
        "^" -> rank = 3
    }
    return rank
}

fun execResult() {
    val a1: Double
    val a2: Double
    var result: Double
    val v2: String? = valStack[--ptrVal]
    var v1: String? = valStack[--ptrVal]
    val operation: String? = opStack[--ptrOp]
    a1 = v1!!.toDouble()
    a2 = v2!!.toDouble()

    result = 0.0

    when (operation) {
        "+" -> result = a1 + a2
        "-" -> result = a1 - a2
        "*" -> result = a1 * a2
        "/" -> result = a1 / a2
        "^" -> result = a1.pow(a2)
    }
    v1 = result.toString()
    valStack[ptrVal++] = v1
}

fun calculateLine(formulaLine: String) {
    var current: String?
    var top: String?
    stringParsing(formulaLine)

    for (i in 0..ptrL) {
        arrayLex[i].also { current = it }
        when (current) {
            "(" -> opStack[ptrOp++] = current
            "+", "-", "*", "/", "^" -> {
                if (ptrOp == 0) {
                    opStack[ptrOp++] = current
                } else {

                    top = opStack[ptrOp - 1]

                    if (priorityRank(current) > priorityRank(top)) {
                        opStack[ptrOp++] = current
                    } else {
                        execResult()
                        opStack[ptrOp++] = current
                    }
                }
            }

            ")" -> while (true) {
                top = opStack[ptrOp - 1]
                if (top == "(") {
                    opStack[--ptrOp]
                    break
                }
                execResult()
            }
            else -> valStack[ptrVal++] = current
        }
    }

    while (ptrOp != 0) {
        execResult()
    }
}

fun stringParsing(formulaLine: String) {
    var simbol: Char
    var parseTMP = ""
    arrayLex = arrayOfNulls(200)
    ptrL = 0
    var i = 0
    while (i < formulaLine.length) {
        simbol = formulaLine[i]
        when (simbol) {
            '+', '-', '*', '^', '/', '(', ')' -> {
                if (parseTMP.isNotEmpty()) {
                    parseTMP.also { arrayLex[ptrL++] = it }
                    parseTMP = ""
                }
                arrayLex[ptrL++] = "" + simbol
            }
            ' ' -> {
            }
            else -> parseTMP += simbol
        }
        i++
    }
    if (parseTMP.isNotEmpty()) arrayLex[ptrL] = parseTMP
}
