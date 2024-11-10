package com.sans.souci.fpik.chapter3

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ExpectSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldBeSameInstanceAs

class SinglyLinkedListSpec : ExpectSpec({
    expect("Init should sof on a big list") {
        shouldThrow<StackOverflowError> { List.fill(50_000, 1) }
    }

    context("head") {
        expect("should return head for Cons") {
            val list = List.of(1, 2, 3)
            val result = List.head(list)
            result shouldBe 1
        }

        expect("should return null Nil") {
            val list = List.of<Int>()
            val result = List.head(list)
            result.shouldBeNull()
        }
    }

    context("tail") {
        expect("should return Nil for Nil") {
            val list = Nil
            val result = List.tail(list)
            result.shouldBeSameInstanceAs(Nil)
        }
        expect("should return tail for Cons") {
            val list = List.of(1, 2, 3)
            val result = List.tail(list)
            result shouldBe List.of(2, 3)
        }
    }

    context("tailNil") {
        expect("should return Nil for Nil") {
            val list = Nil
            val result = List.tailNil(list)
            result.shouldBeSameInstanceAs(Nil)
        }
        expect("should return tail for Cons") {
            val list = List.of(1, 2, 3)
            val result = List.tailNil(list)
            result shouldBe List.of(2, 3)
        }
    }

    context("tailEmpty") {
        expect("should return Nil for Nil") {
            val list = Nil
            val result = List.tailEmpty(list)
            result.shouldBeSameInstanceAs(Nil)
        }
        expect("should return tail for Cons") {
            val list = List.of(1, 2, 3)
            val result = List.tailEmpty(list)
            result shouldBe List.of(2, 3)
        }
    }

    context("fill") {
        expect("fill should return nil for n == 0") {
            List.fill(0, 1).shouldBeSameInstanceAs(Nil)
        }

        expect("n == 1 returns list with head and Nil tail") {
            val list = List.fill(1, 2)
            List.head(list) shouldBe 2
        }
    }

    context("setHead") {
        expect("Can set the head of a Nil") {
            val nilList = Nil
            List.setHead(nilList, 1).shouldBeInstanceOf<Cons<Int>>().should {
                it.head shouldBe 1
            }
        }

        expect("Can set the head of a Cons") {
            val consList = List.of(1, 2, 3)
            List.setHead(consList, 4).shouldBeInstanceOf<Cons<Int>>().should {
                it.head shouldBe 4
                it.tail shouldBeSameInstanceAs List.tail(consList)
            }
        }
    }

    context("drop") {
        expect("Can drop elements from Nil") {
            List.drop(Nil, 1).shouldBeSameInstanceAs(Nil)
        }

        expect("Can drop 0 elements from Cons returns same Cons") {
            val cons = List.of(1, 2, 3)
            List.drop(cons, 0).shouldBeSameInstanceAs(cons)
        }

        expect("Can drop -1 elements from Cons returns same Cons") {
            val cons = List.of(1, 2, 3)
            List.drop(cons, -1).shouldBeSameInstanceAs(cons)
        }

        expect("Can drop 1 elements from Cons") {
            val cons = List.of(1, 2, 3)
            List.drop(cons, 1).shouldBeInstanceOf<Cons<Int>>().shouldBeSameInstanceAs(List.tail(cons))
        }

        expect("Can drop 2 elements from Cons") {
            val cons = List.of(1, 2, 3)
            List.drop(cons, 2).shouldBeSameInstanceAs(List.tail(List.tail(cons)))
        }

        expect("Can drop 3 elements from Cons") {
            val cons = List.of(1, 2, 3)
            List.drop(cons, 3).shouldBeSameInstanceAs(Nil)
        }
    }

    context("dropWhile") {
        expect("Can drop while on Nil returns Nil") {
            List.dropWhile(Nil, { _ -> false }).shouldBeSameInstanceAs(Nil)
        }

        expect("Can drop while on Cons returns same Cons if all are true") {
            val cons = List.of(1, 2, 3)
            List.dropWhile(cons, { _ -> true }) shouldBe cons
        }

        val cons = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        expect("Can drop odds on Cons returns Cons of only evens") {
            List.dropWhile(cons, { a -> a % 2 == 0 }) shouldBe List.of(2, 4, 6, 8, 10)
        }

        expect("Can drop odds on Cons returns Cons of only odds") {
            List.dropWhile(cons, { a -> a % 2 == 1 }) shouldBe List.of(1, 3, 5, 7, 9)
        }
    }

    context("Fill and drop") {
        val cons = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        expect("Can drop odds on Cons returns Cons of only evens") {
            List.dropWhile(cons, { a -> a % 2 == 0 }) shouldBe List.of(2, 4, 6, 8, 10)
        }
    }

    context("Init") {
        expect("Init should return Nil if provided list is Nil") {
            List.init(Nil).shouldBeSameInstanceAs(Nil)
        }

        expect("Init should return Nil if provided list of size 1 is provided") {
            List.init(List.of(1)).shouldBeSameInstanceAs(Nil)
        }

        expect("Init should return only the first element if provided list of size 2 is provided") {
            List.init(List.of(1, 2)) shouldBe List.of(1)
        }

        expect("Init should drop the last element of a list") {
            val cons = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            List.init(cons) shouldBe List.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
        }
    }
})
