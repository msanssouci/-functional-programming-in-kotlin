package com.sans.souci.fpik.chapter3

import java.io.PrintStream

sealed class List<out A> {
    companion object {
        fun <A> of(vararg aa: A): List<A> {
            val tail = aa.sliceArray(1 until aa.size)
            return if (aa.isEmpty()) Nil else Cons(aa[0], of(*tail))
        }

        inline fun <reified A> fill(
            n: Int,
            a: A,
        ): List<A> =
            when {
                n == 0 -> Nil
                else -> of(*((0..n).map { a }.toTypedArray()))
            }

        fun <A> tailNil(list: List<A>): List<A> =
            when (list) {
                is Nil -> Nil
                is Cons -> list.tail
            }

        fun <A> tailEmpty(list: List<A>): List<A> =
            when (list) {
                is Nil -> of()
                is Cons -> list.tail
            }

        fun <A> head(list: List<A>): A? =
            when (list) {
                is Nil -> null
                is Cons -> list.head
            }

        tailrec fun <A> print(
            list: List<A>,
            printStream: PrintStream,
        ) {
            when (list) {
                is Nil -> {
                    printStream.println()
                    return
                }

                is Cons -> {
                    printStream.print("${list.head} ")
                    print(list.tail, printStream)
                }
            }
        }
    }
}

data object Nil : List<Nothing>()

data class Cons<out A>(val head: A, val tail: List<A>) : List<A>()
