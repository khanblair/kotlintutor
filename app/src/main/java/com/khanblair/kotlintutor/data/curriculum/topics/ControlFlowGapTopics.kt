package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val controlFlowGapTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "label-loops",
        title = "Label Loops",
        category = "Control Flow",
        recap = Recap(
            previousTopicTitle = "Arrays",
            recapText = "Array<T> is fixed-size but mutable in place, built with arrayOf(...) or Array(size){init}; IntArray/DoubleArray etc. store unboxed primitives; prefer List/MutableList for everyday code.",
            quickCheckQuestion = "Can you resize an Array after creating it?",
            quickCheckAnswer = "No — arrays are fixed-size; only the element values can change.",
        ),
        explain = "By default, break and continue only affect the nearest enclosing loop — in nested loops, there's no way for an inner loop to directly exit or skip an iteration of an outer loop. Kotlin solves this with labels: prefixing any loop with an identifier followed by @ (e.g. loop@) gives it a name that break and continue can target explicitly, as break@loop or continue@loop. This lets you jump out of — or skip an iteration of — an outer loop directly from code nested several levels deep, without extra boolean flags or restructuring into a function you can return from.\n\n" +
            "Labels aren't limited to loops. The identifier@ syntax can prefix any expression in Kotlin — including lambda literals and blocks, most commonly seen later with labelled returns like return@label from inside a lambda. On loops specifically, though, a label's sole purpose is to give break/continue a target beyond the innermost loop.",
        example = """
            |fun main() {
            |    var found = false
            |
            |    search@ for (row in 0..2) {
            |        for (col in 0..2) {
            |            if (row == 1 && col == 1) {
            |                found = true
            |                break@search                      // exits BOTH loops at once
            |            }
            |            println("checking (${'$'}row, ${'$'}col)")
            |        }
            |    }
            |    println("found = ${'$'}found")
            |
            |    outer@ for (i in 1..3) {
            |        for (j in 1..3) {
            |            if (j > i) continue@outer             // skip straight to the next i
            |            print("${'$'}i${'$'}j ")
            |        }
            |    }
            |    println()                                     // 11 21 22 31 32 33
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "An unlabeled break/continue only ever affects the nearest enclosing loop — labels are how you reach an outer one.",
            "A label is identifier@ placed immediately before the loop keyword; break@label and continue@label reference it.",
            "identifier@ can prefix any loop (for, while, do-while), and the same syntax labels other expressions too, like lambdas used with return@label.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "label-loops-q1",
                topicId = "label-loops",
                question = "What problem do labeled loops solve?",
                options = listOf(
                    "Making loops run faster",
                    "Letting break/continue target an outer loop from inside a nested loop",
                    "Allowing loops without a condition",
                    "Replacing while loops entirely",
                ),
                correctIndex = 1,
                explanation = "Without a label, break/continue only affect the nearest enclosing loop; labels let you target an outer one explicitly.",
            ),
            QuizQuestion(
                id = "label-loops-q2",
                topicId = "label-loops",
                question = "How do you label a for loop?",
                options = listOf(
                    "for@ (i in 1..3)",
                    "label: for (i in 1..3)",
                    "loop@ for (i in 1..3)",
                    "@loop for (i in 1..3)",
                ),
                correctIndex = 2,
                explanation = "A label is an identifier followed by @, placed immediately before the loop it names, e.g. loop@ for (...).",
            ),
            QuizQuestion(
                id = "label-loops-q3",
                topicId = "label-loops",
                question = "What does break@outer do inside a loop nested within a loop labeled outer?",
                options = listOf(
                    "Exits only the innermost loop",
                    "Exits the loop labeled outer, skipping its remaining iterations and any loops nested inside it",
                    "Throws an exception",
                    "Restarts the outer loop from the beginning",
                ),
                correctIndex = 1,
                explanation = "A labelled break exits the loop carrying that label directly, regardless of how deeply nested the break statement is.",
            ),
        ),
        tutorFocus = "Show the failure mode first — an unlabeled break in a nested loop only escapes the inner loop — then introduce the label as the fix. Exercise: search a 2D grid (a list of lists) for a target value and use a labeled break to stop scanning immediately once found, printing how many cells were checked.",
    ),
    CurriculumTopic(
        id = "break-continue",
        title = "break & continue",
        category = "Control Flow",
        recap = Recap(
            previousTopicTitle = "Label Loops",
            recapText = "loop@ prefixes a loop with a name; break@loop/continue@loop target that outer loop directly from nested code.",
            quickCheckQuestion = "What does break@outer do from inside a loop nested inside a loop labeled outer?",
            quickCheckAnswer = "It exits the loop labeled outer, not just the innermost loop.",
        ),
        explain = "break immediately terminates the nearest enclosing loop — execution jumps to the first statement after it. continue skips the rest of the current iteration and moves on to the loop's next iteration (re-checking the condition for while/do-while, or advancing to the next element for for). Both exist purely for their effect on control flow inside imperative loops — you use them for what they do, not for a value — and they only make sense inside a loop body (or, with a label, inside whichever labelled loop they name).\n\n" +
            "This is also why plain break/continue can't be used inside a lambda passed to a function like forEach: forEach isn't a loop construct, it's a regular function call, and the lambda you pass it isn't syntactically a loop body — so there's no enclosing loop for an unlabelled break/continue to target, and the compiler rejects it. return inside a forEach lambda returns from the whole enclosing function (or, with a label, return@forEach returns just from that one lambda call, skipping to the next element) — it doesn't give you break's 'stop iterating' or continue's 'skip one, keep going' semantics. When you actually need break/continue control flow, reach for a plain for or while loop instead of forEach.",
        example = """
            |fun main() {
            |    for (n in 1..10) {
            |        if (n % 2 == 0) continue          // skip even numbers
            |        if (n > 7) break                  // stop once n exceeds 7
            |        print("${'$'}n ")                     // 1 3 5 7
            |    }
            |    println()
            |
            |    // listOf(1, 2, 3).forEach { if (it > 1) break }  // won't compile — forEach isn't a loop
            |
            |    val list = listOf(1, 2, 3, 4, 5)
            |    for (n in list) {
            |        if (n == 3) continue
            |        if (n == 5) break
            |        print("${'$'}n ")                     // 1 2 4
            |    }
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "break exits the nearest enclosing loop entirely; continue skips to that loop's next iteration.",
            "Both only make sense inside a loop — they can't be used unlabelled inside a lambda like forEach's, because a lambda call isn't a loop.",
            "For real break/continue behavior while processing a lambda's body, use a plain for/while loop instead of forEach.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "break-continue-q1",
                topicId = "break-continue",
                question = "What does continue do?",
                options = listOf(
                    "Exits the loop entirely",
                    "Skips the rest of the current iteration and moves to the next one",
                    "Pauses the loop indefinitely",
                    "Restarts the loop from the beginning",
                ),
                correctIndex = 1,
                explanation = "continue abandons the remainder of the current iteration's body and proceeds to the loop's next iteration.",
            ),
            QuizQuestion(
                id = "break-continue-q2",
                topicId = "break-continue",
                question = "Why can't you use break inside a lambda passed to list.forEach { ... }?",
                options = listOf(
                    "break is deprecated in Kotlin",
                    "forEach is a function call, not a loop, so there's no enclosing loop for break to target",
                    "forEach lambdas run on a separate thread",
                    "You actually can, with no restrictions",
                ),
                correctIndex = 1,
                explanation = "forEach's lambda body isn't a loop construct the compiler recognizes as a break/continue target, so unlabelled break/continue don't compile there.",
            ),
            QuizQuestion(
                id = "break-continue-q3",
                topicId = "break-continue",
                question = "If you need genuine break/continue semantics while iterating, what should you reach for instead of forEach?",
                options = listOf(
                    "A plain for or while loop",
                    "A when expression",
                    "A try/catch block",
                    "The lazy sequence builder",
                ),
                correctIndex = 0,
                explanation = "for and while are actual loop constructs, so break and continue (optionally labelled) work directly inside them.",
            ),
        ),
        tutorFocus = "Make the forEach limitation concrete — have the learner try writing break inside a forEach lambda, see the compile error, then rewrite the same logic with a for loop. Exercise: process a list of transaction amounts with a for loop, using continue to skip zero-amount entries and break once a running total exceeds a threshold.",
    ),
)
