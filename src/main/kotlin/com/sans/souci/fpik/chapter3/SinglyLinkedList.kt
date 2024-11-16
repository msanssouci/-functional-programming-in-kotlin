package com.sans.souci.fpik.chapter3

sealed class List<out A> {
    companion object {
        fun <A> of(vararg aa: A): List<A> {
            val tail = aa.sliceArray(1 until aa.size)
            return if (aa.isEmpty()) Nil else Cons(aa[0], of(*tail))
        }

        fun <A> fillProducer(
            n: Int,
            a: A,
            p: (A) -> A = { prevA -> prevA },
        ): List<A> {
            tailrec fun fillIt(
                i: Int,
                nextA: A,
                acc: List<A> = Nil,
            ): List<A> =
                when {
                    i <= 0 -> acc
                    else -> fillIt(i - 1, p(nextA), Cons(nextA, acc))
                }

            return fillIt(n, a)
        }

        fun <A> fill(
            n: Int,
            a: A,
        ): List<A> {
            /**
             * This only works because order does not matter
             */
            tailrec fun fillIt(
                i: Int,
                acc: List<A> = Nil,
            ): List<A> =
                when {
                    i <= 0 -> acc
                    else -> fillIt(i - 1, Cons(a, acc))
                }

            return fillIt(n)
        }

        fun <A> drop(
            l: List<A>,
            n: Int,
        ): List<A> {
            tailrec fun dropIt(
                l: List<A>,
                n: Int,
            ): List<A> =
                if (l is Nil || n <= 0) {
                    l
                } else {
                    dropIt(tail(l), n - 1)
                }

            return dropIt(l, n)
        }

        fun <A> dropWhile(
            l: List<A>,
            f: (A) -> Boolean,
        ): List<A> {
            return when (l) {
                is Nil -> l
                is Cons -> if (f(l.head)) dropWhile(l.tail, f) else l
            }
        }

        fun <A> tail(xs: List<A>): List<A> =
            when (xs) {
                is Nil -> xs
                is Cons -> xs.tail
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

        fun <A> setHead(
            xs: List<A>,
            x: A,
        ): List<A> =
            when (xs) {
                is Nil -> of(x)
                is Cons -> Cons(x, xs.tail)
            }

        fun <A> init(l: List<A>): List<A> =
            when (l) {
                is Nil -> l
                is Cons ->
                    if (l.tail is Nil) {
                        Nil
                    } else {
                        Cons(l.head, init(l.tail))
                    }
            }
    }
}

data object Nil : List<Nothing>()

data class Cons<out A>(val head: A, val tail: List<A>) : List<A>()
