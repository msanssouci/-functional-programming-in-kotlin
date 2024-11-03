package com.sans.souci.fpik.chapter3

import io.kotest.core.spec.style.ExpectSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

class SinglyLinkedListSpec : ExpectSpec({

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

    context("tailNil") {
        expect("should return Nil for Nil") {
            val list = Nil
            val result = List.tailNil(list)
            result shouldBe Nil
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
            result shouldBe Nil
        }
        expect("should return tail for Cons") {
            val list = List.of(1, 2, 3)
            val result = List.tailEmpty(list)
            result shouldBe List.of(2, 3)
        }
    }

    context("fill") {
        expect("fill should return nil for n == 0") {
            List.fill(0, 1) shouldBe Nil
        }

        expect("n == 1 returns list with head and Nil tail") {
            val list = List.fill(1, 2)
            List.head(list) shouldBe 2
        }
    }

    context("print") {
        expect("Can ") {
            val list = List.fill(10, 2)
            List.print(list, System.out)
        }
    }
})
