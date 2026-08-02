package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val controlFlowTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "conditional-expressions",
        title = "Conditional Expressions (if / when)",
        category = "Control Flow",
        recap = Recap(
            previousTopicTitle = "Ranges & Progressions",
            recapText = ".. is inclusive, until exclusive, downTo/step change direction/increment; in checks membership.",
            quickCheckQuestion = "Values of 1 until 3? Count down 5 to 1?",
            quickCheckAnswer = "1, 2; 5 downTo 1.",
        ),
        explain = "In Kotlin, if and when are expressions — they return a value, not just execute branches. That means you can assign their result directly, which removes the need for a ternary operator (Kotlin has none).\n\n" +
            "- if/else returns the value of the chosen branch.\n" +
            "- when is a powerful multi-branch selector. It can match values, ranges, types (is), or arbitrary boolean conditions (when used without a subject).",
        example = """
            |fun grade(score: Int): String {
            |    val pass = if (score >= 50) "pass" else "fail"     // if as expression
            |
            |    return when {                                       // when as expression
            |        score >= 90 -> "A (${'$'}pass)"
            |        score in 70..89 -> "B (${'$'}pass)"
            |        score >= 50 -> "C (${'$'}pass)"
            |        else -> "F (${'$'}pass)"
            |    }
            |}
            |
            |fun main() {
            |    println(grade(95))   // A (pass)
            |    println(grade(40))   // F (fail)
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "When used as an expression, when/if must be exhaustive — include else (unless the compiler can prove all cases are covered, e.g. sealed types/enums).",
            "when with a subject (when (x) { … }) matches against that value; without a subject it evaluates boolean conditions top to bottom.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "conditional-expressions-q1",
                topicId = "conditional-expressions",
                question = "Why doesn't Kotlin have a ternary operator like a ? b : c?",
                options = listOf(
                    "It was removed for performance reasons",
                    "Because if is already an expression that returns a value",
                    "Kotlin has one, it's just spelled differently",
                    "Ternary expressions are unsafe",
                ),
                correctIndex = 1,
                explanation = "Since if already returns a value as an expression, a separate ternary operator would be redundant.",
            ),
            QuizQuestion(
                id = "conditional-expressions-q2",
                topicId = "conditional-expressions",
                question = "When is else required in a when?",
                options = listOf(
                    "Always",
                    "Never",
                    "When when is used as an expression and the cases aren't provably exhaustive",
                    "Only when there are more than 3 branches",
                ),
                correctIndex = 2,
                explanation = "As a statement, when doesn't need else; as an expression, the compiler must be able to prove exhaustiveness.",
            ),
            QuizQuestion(
                id = "conditional-expressions-q3",
                topicId = "conditional-expressions",
                question = "Can a when branch match a range?",
                options = listOf("No", "Yes, e.g. in 1..10 ->", "Only with an if inside", "Only for Char, not Int"),
                correctIndex = 1,
                explanation = "when branches can match ranges directly with the in operator.",
            ),
        ),
        tutorFocus = "The mental shift is \"control flow returns values.\" Show assigning an if result. Probe why there's no ternary. Exercise: rewrite a nested if/else chain as a clean when expression.",
    ),
    CurriculumTopic(
        id = "loops",
        title = "Loops (for, while, break & continue)",
        category = "Control Flow",
        recap = Recap(
            previousTopicTitle = "Conditional Expressions (if / when)",
            recapText = "if and when are expressions that return values (no ternary needed); when matches values, ranges, types.",
            quickCheckQuestion = "Why no ternary? When is else required?",
            quickCheckAnswer = "if already returns a value; when when is an expression and not exhaustive.",
        ),
        explain = "Kotlin has for, while, and do-while. The for loop iterates over anything iterable: ranges, collections, arrays, strings. There's no C-style for(i=0;…) — you iterate ranges instead. break exits a loop and continue skips to the next iteration. Labels (outer@) let break/continue target an outer loop in nested loops.",
        example = """
            |fun main() {
            |    for (item in listOf("a", "b", "c")) print("${'$'}item ")   // a b c
            |    println()
            |
            |    for ((index, value) in listOf("x", "y").withIndex())  // index + value
            |        println("${'$'}index -> ${'$'}value")
            |
            |    var n = 3
            |    while (n > 0) { print("${'$'}n "); n-- }                   // 3 2 1
            |    println()
            |
            |    outer@ for (i in 1..3) {
            |        for (j in 1..3) {
            |            if (i * j > 4) break@outer                    // break the outer loop
            |            print("${'$'}{i * j} ")
            |        }
            |    }
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "No traditional C-style index loop — use ranges or withIndex().",
            "Labels are the clean way to break/continue an outer loop.",
            "Iterating a Map gives you entries: for ((k, v) in map).",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "loops-q1",
                topicId = "loops",
                question = "How do you loop over indices and values together?",
                options = listOf(
                    "for (i in 0..list.size) { ... }",
                    "for ((i, v) in list.withIndex())",
                    "for (i, v in list)",
                    "list.forEachIndex { i, v -> ... }",
                ),
                correctIndex = 1,
                explanation = "withIndex() pairs each element with its index for destructuring in a for loop.",
            ),
            QuizQuestion(
                id = "loops-q2",
                topicId = "loops",
                question = "What does break do vs continue?",
                options = listOf(
                    "Both exit the loop",
                    "break exits the loop; continue skips to the next iteration",
                    "break skips one iteration; continue exits the loop",
                    "They're identical in Kotlin",
                ),
                correctIndex = 1,
                explanation = "break exits the loop entirely; continue moves on to the next iteration.",
            ),
            QuizQuestion(
                id = "loops-q3",
                topicId = "loops",
                question = "How do you break out of an outer loop from an inner loop?",
                options = listOf(
                    "It's not possible in Kotlin",
                    "With a label, e.g. break@outer",
                    "Return from the function",
                    "Throw an exception and catch it outside",
                ),
                correctIndex = 1,
                explanation = "A labelled break (break@outer) exits the labelled outer loop directly.",
            ),
        ),
        tutorFocus = "Java/C learners look for for(i=0;…) — redirect them to ranges and withIndex(). Exercise: sum only even numbers in a list using continue, or find the first pair whose product exceeds a threshold using a labelled break.",
    ),
    CurriculumTopic(
        id = "throwing-exceptions",
        title = "Exceptions",
        category = "Control Flow",
        recap = Recap(
            previousTopicTitle = "Loops (for, while, break & continue)",
            recapText = "for iterates ranges/iterables; use withIndex() for index+value, labels to break an outer loop.",
            quickCheckQuestion = "Loop index and value together? Break an outer loop?",
            quickCheckAnswer = "withIndex(); labelled break@outer.",
        ),
        explain = "Kotlin handles errors with try/catch/finally, and throw to raise an exception. A key difference from Java: Kotlin has no checked exceptions — you're never forced to declare or catch them. Also, try is an expression, so it can return a value. Common exceptions include IllegalArgumentException and IllegalStateException, often raised via the require(...) and check(...) helper functions.",
        example = """
            |fun parseAge(text: String): Int {
            |    return try {
            |        text.toInt()
            |    } catch (e: NumberFormatException) {
            |        -1                                   // try as an expression
            |    } finally {
            |        println("parse attempted for '${'$'}text'")
            |    }
            |}
            |
            |fun setAge(age: Int) {
            |    require(age >= 0) { "age must be non-negative, was ${'$'}age" }  // throws IllegalArgumentException
            |}
            |
            |fun main() {
            |    println(parseAge("42"))   // 42
            |    println(parseAge("no"))   // -1
            |    // setAge(-1)             // would throw with the given message
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "No checked exceptions: the compiler won't force try/catch, so document what can throw.",
            "Prefer require/check for precondition/state validation — they read clearly and throw the right exception type.",
            "finally always runs, even after a return in try.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "throwing-exceptions-q1",
                topicId = "throwing-exceptions",
                question = "How do Kotlin exceptions differ from Java's checked exceptions?",
                options = listOf(
                    "Kotlin has no checked exceptions — nothing is forced",
                    "Kotlin requires every exception to be declared with throws",
                    "Kotlin exceptions can only be caught once",
                    "There is no difference",
                ),
                correctIndex = 0,
                explanation = "Kotlin has no checked-exceptions concept; the compiler never forces a try/catch or a throws declaration.",
            ),
            QuizQuestion(
                id = "throwing-exceptions-q2",
                topicId = "throwing-exceptions",
                question = "What does require(condition) do when the condition is false?",
                options = listOf(
                    "Returns null",
                    "Throws IllegalArgumentException",
                    "Logs a warning and continues",
                    "Throws NullPointerException",
                ),
                correctIndex = 1,
                explanation = "require throws IllegalArgumentException with the given message when the condition is false.",
            ),
            QuizQuestion(
                id = "throwing-exceptions-q3",
                topicId = "throwing-exceptions",
                question = "Can try return a value?",
                options = listOf(
                    "No, it's always a statement",
                    "Yes — try is an expression",
                    "Only inside a lambda",
                    "Only if there's no catch block",
                ),
                correctIndex = 1,
                explanation = "try is an expression in Kotlin, so its result (from the try or catch block) can be assigned or returned.",
            ),
        ),
        tutorFocus = "Highlight \"no checked exceptions\" for Java learners and the try-as-expression idiom. Push require/check for validation. Exercise: write a function that validates its input with require and safely parses a number with a try expression.",
    ),
)
