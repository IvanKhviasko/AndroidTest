import kotlin.math.pow

var ptrL = 0
var ptrOp = 0
var ptrVal = 0
var arrayLex = arrayOfNulls<String>(200)
var opStack = arrayOfNulls<String>(200)
var valStack = arrayOfNulls<String>(200)

fun simPrty(o: String?): Int {
    var r = -1
    when (o) {
        "(" -> r = 0
        "+", "-" -> r = 1
        "*", "/" -> r = 2
        "^" -> r = 3
    }
    return r
}

fun peekOp(): String? {
    return opStack[ptrOp - 1]
}

fun pushOp(op: String?) {
    opStack[ptrOp++] = op
}

fun pushVal(v: String?) {
    valStack[ptrVal++] = v
}

fun popOp(): String? {
    return opStack[--ptrOp]
}

fun popVal(): String? {
    return valStack[--ptrVal]
}

fun execFun() {
    val a1: Double
    val a2: Double
    var r: Double
    val v2: String? = popVal()
    var v1: String? = popVal()
    val op: String? = popOp()
    a1 = v1!!.toDouble()
    a2 = v2!!.toDouble()

    r = 0.0

    when (op) {
        "+" -> r = a1 + a2
        "-" -> r = a1 - a2
        "*" -> r = a1 * a2
        "/" -> r = a1 / a2
        "^" -> r = a1.pow(a2)
    }
    v1 = r.toString()
    pushVal(v1)
}


fun recCalculate(F: String) {
    var curr: String?
    var top: String?
    formulaParse(F)

    for (i in 0..ptrL) {
        arrayLex[i].also { curr = it }
        when (curr) {
            "(" -> pushOp(curr)
            "+", "-", "*", "/", "^" -> {
                if (ptrOp == 0) {
                    pushOp(curr)
                } else {

                    top = peekOp()

                    if (simPrty(curr) > simPrty(top)) {
                        pushOp(curr)
                    } else {
                        execFun()
                        pushOp(curr)
                    }
                }
            }

            ")" -> while (true) {
                top = peekOp()
                if (top == "(") {
                    popOp()
                    break
                }
                execFun()
            }
            else -> pushVal(curr)
        }
    }

    while (ptrOp != 0) {
        execFun()
    }
}


fun formulaParse(Formula: String) {
    var s: Char
    var parseTMP = ""
    arrayLex = arrayOfNulls(200)
    ptrL = 0
    var i = 0
    while (i < Formula.length) {
        s = Formula[i]
        when (s) {
            '+', '-', '*', '^', '/', '(', ')' -> {
                if (parseTMP.isNotEmpty()) {
                    parseTMP.also { arrayLex[ptrL++] = it }
                    parseTMP = ""
                }
                arrayLex[ptrL++] = "" + s
            }
            ' ' -> {
            }
            else -> parseTMP += s
        }
        i++
    }
    if (parseTMP.isNotEmpty()) arrayLex[ptrL] = parseTMP
}

fun main() {
    val line = "(1+2)*3+4"
    recCalculate(line)
    println(line + "=" + popVal())
}