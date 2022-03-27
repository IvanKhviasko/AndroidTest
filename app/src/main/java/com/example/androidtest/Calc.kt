package com.example.androidtest

fun main() {
    val line = "2+2*2"
    Calculate(line)
    println(line + "=" + popVal())
}

var ptrL = 0
var ptrOp = 0
var ptrVal = 0

var Lex = arrayOfNulls<String>(200)
var opStack = arrayOfNulls<String>(200)
var valStack = arrayOfNulls<String>(200)

fun Prty(o: String?): Int {
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

fun exec() {
    val a1: Double
    val a2: Double
    var r: Double
    var v1: String?
    val v2: String?
    val op: String?

    v2 = popVal()
    v1 = popVal()
    op = popOp()

    a1 = v1!!.toDouble()
    a2 = v2!!.toDouble()

    r = 0.0

    when (op) {
        "+" -> r = a1 + a2
        "-" -> r = a1 - a2
        "*" -> r = a1 * a2
        "/" -> r = a1 / a2
        "^" -> r = Math.pow(a1, a2)
    }

    v1 = java.lang.Double.toString(r)//?
    pushVal(v1)
}


fun Calculate(F: String) {
    var curr: String?
    var top: String?
    parse(F)

    for (i in 0..ptrL) {
        curr = Lex[i]
        when (curr) {
            "(" -> pushOp(curr)

            "+", "-", "*", "/", "^" -> {
                if (ptrOp == 0) {
                    pushOp(curr)
                } else {
                    top = peekOp()
                    if (Prty(curr) > Prty(top)) {
                        pushOp(curr)
                    } else {
                        exec()
                        pushOp(curr)
                    }
                }
            }

            ")" -> while (true) {
                top = peekOp()
                if (top == "(") {
                    top = popOp()
                    break
                }
                exec()
            }
            else -> pushVal(curr)
        }
    }

    while (ptrOp != 0) {
        exec()
    }
}


fun parse(Formula: String) {
    var s: Char
    var i: Int
    var Tmp = ""

    Lex = arrayOfNulls(200)

    ptrL = 0
    i = 0
    while (i < Formula.length) {
        s = Formula[i]
        when (s) {
            '+', '-', '*', '^', '/', '(', ')' -> {
                if (Tmp.length > 0) {
                    Lex[ptrL++] = Tmp
                    Tmp = ""
                }
                Lex[ptrL++] = "" + s
            }
            ' ' -> {
            }
            else -> Tmp = Tmp + s
        }
        i++
    }
    if (Tmp.length > 0) Lex[ptrL] = Tmp
}

