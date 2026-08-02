package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val collectionsGapTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "progressions",
        title = "Progressions",
        category = "Collections",
        recap = Recap(
            previousTopicTitle = "Function Types",
            recapText = "A function type like (Int) -> Boolean names a function's parameter and return types; lambdas, function references, and anonymous functions can all be assigned to a variable declared with that type.",
            quickCheckQuestion = "What function type would you write for a function taking two Ints and returning an Int?",
            quickCheckAnswer = "(Int, Int) -> Int",
        ),
        explain = "A range with a step, like 1..10 step 2, or a downTo range, like 10 downTo 1, produces an IntProgression (or LongProgression/CharProgression for Long and Char) rather than a plain IntRange. A progression exposes first, last, and step: first is the starting value, step is the increment (negative for a descending progression), and last is the actual final value the progression reaches — which can differ from the bound you wrote if the step doesn't land on it exactly. In fact IntRange is itself declared as an IntProgression with step fixed to 1, so every plain range you've already been using was a progression all along.",
        example = """
            |fun main() {
            |    val evens: IntProgression = 1..10 step 2      // step turns a range into a progression
            |    println(evens.first)                           // 1
            |    println(evens.last)                             // 9 — the actual last element reached, not 10
            |    println(evens.step)                              // 2
            |
            |    val countdown = 10 downTo 1                       // IntProgression, counts down
            |    println(countdown.step)                             // -1
            |
            |    val plainRange: IntProgression = 1..5                // IntRange IS an IntProgression, step = 1
            |    println(plainRange.step)                              // 1
            |
            |    println(('a'..'e' step 2).toList())                    // [a, c, e] — CharProgression works the same way
            |
            |    for (i in 1..8 step 3) print("${'$'}i ")                  // 1 4 7
            |    println()
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "A range like 1..10 step 2 or 10 downTo 1 produces an IntProgression (or LongProgression/CharProgression) — not a plain Range once a step or direction is involved.",
            "IntRange is defined as an IntProgression with step fixed to 1, so every plain range is already a progression.",
            "first, last, and step describe the progression: last is the actual final element produced, which may not equal the endpoint you wrote if the step overshoots it.",
            "The argument to the step(...) infix function must always be positive, even on a downTo progression — direction comes from .. vs downTo, not from the sign you pass to step.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "progressions-q1",
                topicId = "progressions",
                question = "What type does the expression 1..10 step 3 produce?",
                options = listOf("IntRange", "IntProgression", "List<Int>", "IntArray"),
                correctIndex = 1,
                explanation = "Applying step to a range yields an IntProgression; only a step-1 range stays an IntRange.",
            ),
            QuizQuestion(
                id = "progressions-q2",
                topicId = "progressions",
                question = "For val p = 1..8 step 3, what is p.last?",
                options = listOf("8", "7", "9", "10"),
                correctIndex = 1,
                explanation = "The progression produces 1, 4, 7 — the next value (10) would exceed 8, so last is 7, not the written endpoint 8.",
            ),
            QuizQuestion(
                id = "progressions-q3",
                topicId = "progressions",
                question = "What must the argument passed to step(...) be, even for a downTo progression?",
                options = listOf(
                    "Any integer, positive or negative",
                    "Positive — direction comes from downTo/.., not the sign of step",
                    "Always negative",
                    "Zero is allowed to mean 'no step'",
                ),
                correctIndex = 1,
                explanation = "step(...) throws IllegalArgumentException for non-positive arguments; a descending progression's step property ends up negative internally, but you always pass a positive number in.",
            ),
        ),
        tutorFocus = "Make sure the learner sees IntRange as a special case of IntProgression (step = 1), not a separate concept. Exercise: write 1..20 step 5 and predict first/last/step before running, then predict last for 1..8 step 3 where the step overshoots the endpoint.",
    ),
    CurriculumTopic(
        id = "iterators",
        title = "Iterators",
        category = "Collections",
        recap = Recap(
            previousTopicTitle = "Progressions",
            recapText = "IntProgression/LongProgression/CharProgression describe a range with a step; first/last/step describe its bounds and increment, and a plain range is just a progression with step 1.",
            quickCheckQuestion = "What's the step of the progression produced by 10 downTo 1?",
            quickCheckAnswer = "-1",
        ),
        explain = "Every Iterable<T>, including List, Set, and Map's entries, can hand out an Iterator<T> via .iterator(). Iterator<T> defines just two members: hasNext(): Boolean and next(): T. A for loop over a collection is syntactic sugar — under the hood the compiler calls iterator() once, then loops while hasNext() returns true, calling next() each time to get the value. MutableIterator<T> extends Iterator<T> with a remove() function, which is the safe way to delete elements from a mutable collection while iterating: it removes the element that was last returned by next() and keeps the iterator's internal position consistent. Mutating the collection directly — for example calling list.remove(x) — while a for loop is iterating over that same list throws a ConcurrentModificationException, because the collection's structure changed out from under the iterator.",
        example = """
            |fun main() {
            |    val numbers = mutableListOf(1, 2, 3, 4, 5)
            |
            |    val iterator = numbers.iterator()          // MutableIterator<Int>
            |    while (iterator.hasNext()) {
            |        val n = iterator.next()
            |        if (n % 2 == 0) {
            |            iterator.remove()                    // safe removal mid-iteration
            |        }
            |    }
            |    println(numbers)                             // [1, 3, 5]
            |
            |    // A for loop desugars to exactly this hasNext()/next() pattern under the hood:
            |    for (n in numbers) print("${'$'}n ")          // 1 3 5
            |    println()
            |
            |    // Mutating the collection directly while iterating with for throws:
            |    // for (n in numbers) { numbers.remove(n) }   // ConcurrentModificationException
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Iterator<T> exposes just hasNext() and next(); a for loop over an Iterable<T> compiles down to exactly these two calls.",
            "Call collection.iterator() to obtain one manually when you need finer control than a for loop gives you.",
            "MutableIterator<T> adds remove() — the only safe way to delete elements from a mutable collection while iterating over it.",
            "Removing directly from a collection (e.g. list.remove(x)) inside a for loop over that same collection throws ConcurrentModificationException.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "iterators-q1",
                topicId = "iterators",
                question = "What two members does the Iterator<T> interface define?",
                options = listOf("add() and remove()", "hasNext() and next()", "first() and last()", "get() and set()"),
                correctIndex = 1,
                explanation = "Iterator<T> defines hasNext(): Boolean and next(): T; everything else (like remove()) belongs to MutableIterator.",
            ),
            QuizQuestion(
                id = "iterators-q2",
                topicId = "iterators",
                question = "What happens if you call list.remove(element) directly inside a for loop iterating over that same list?",
                options = listOf(
                    "It works fine, just like iterator.remove()",
                    "It throws ConcurrentModificationException",
                    "It silently skips the next element",
                    "It only removes it after the loop ends",
                ),
                correctIndex = 1,
                explanation = "Structurally mutating a list outside of its iterator while a for loop iterates over it invalidates the iterator's state, triggering a fail-fast ConcurrentModificationException.",
            ),
            QuizQuestion(
                id = "iterators-q3",
                topicId = "iterators",
                question = "How do you safely remove elements from a MutableList while iterating over it?",
                options = listOf(
                    "Call list.removeAt(index) inside the for loop",
                    "Obtain a MutableIterator via .iterator() and call its remove() method",
                    "Use a for-each with removeIf inside the loop body",
                    "You can't; you must always build a brand-new list",
                ),
                correctIndex = 1,
                explanation = "MutableIterator.remove() removes the element last returned by next() and keeps the iterator's internal state consistent, avoiding ConcurrentModificationException.",
            ),
        ),
        tutorFocus = "Walk through how for (x in collection) is just sugar for iterator()/hasNext()/next() — this demystifies the for loop and sets up MutableIterator.remove() as the natural fix for ConcurrentModificationException. Exercise: iterate a MutableList<Int> and remove all multiples of 3 using the iterator directly, then show the ConcurrentModificationException produced by removing via the list reference inside the loop instead.",
    ),
)
