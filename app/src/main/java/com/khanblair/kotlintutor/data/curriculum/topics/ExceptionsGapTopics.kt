package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val exceptionsGapTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "catching-exceptions",
        title = "Catching Exceptions",
        category = "Exceptions",
        recap = Recap(
            previousTopicTitle = "break & continue",
            recapText = "break exits the nearest enclosing loop; continue skips to its next iteration; labels (break@loop, continue@loop) target an outer loop; neither works unlabelled inside a forEach lambda.",
            quickCheckQuestion = "Why doesn't break compile inside a list.forEach { ... } lambda?",
            quickCheckAnswer = "forEach is a function call, not a loop, so there's no enclosing loop for break to target.",
        ),
        explain = "A try block wraps code that might throw; one or more catch blocks handle specific exception types if they're thrown; an optional finally block runs afterward no matter what — whether the try succeeded, an exception was caught, or an exception propagated uncaught. Like if/when, try is an expression: it evaluates to whichever branch actually ran (the last expression of the try block, or of whichever catch block handled the exception), so you can assign or return its result directly.\n\n" +
            "You can chain multiple catch clauses to handle different exception types differently. They're checked in order, top to bottom, and the first one whose type matches (or is a supertype of) the thrown exception wins — so list more specific exception types before more general ones. A catch (e: Exception) placed first would swallow everything below it, since every exception is an Exception, making any more specific catch clauses after it dead code. Catching a broad supertype like Exception (or Throwable) is sometimes appropriate as a last-resort fallback at a boundary, but usually catching the specific types you can meaningfully recover from — and handling each differently — is clearer and safer.\n\n" +
            "Unlike Java, Kotlin has no checked exceptions: there's no throws clause, and the compiler never forces you to catch or declare anything. Any function can throw any exception at any time as far as the type system is concerned, which puts the burden of knowing what might be thrown on documentation and judgment rather than the compiler.",
        example = """
            |fun readConfigValue(raw: String): Int {
            |    return try {
            |        raw.trim().toInt()
            |    } catch (e: NumberFormatException) {            // most specific first
            |        println("not a number: '${'$'}raw' — defaulting to 0")
            |        0
            |    } catch (e: Exception) {                         // broad fallback, listed last
            |        println("unexpected error: ${'$'}{e.message}")
            |        -1
            |    } finally {
            |        println("finished parsing '${'$'}raw'")       // always runs, success or failure
            |    }
            |}
            |
            |fun main() {
            |    println(readConfigValue("42"))      // prints "finished parsing '42'", then 42
            |    println(readConfigValue("oops"))    // prints "not a number..." then "finished...", then 0
            |    // No throws declaration needed anywhere above — Kotlin exceptions are unchecked.
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "try is an expression — it evaluates to the result of whichever block (try or a matching catch) actually ran.",
            "Order catch clauses from most specific to most general; a broader supertype catch placed first shadows the specific ones below it.",
            "finally always runs — on success, after a caught exception, or while an exception is propagating uncaught — which makes it the place for cleanup.",
            "Kotlin exceptions are unchecked: no throws declarations, and the compiler never forces a try/catch.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "catching-exceptions-q1",
                topicId = "catching-exceptions",
                question = "Why must more specific catch clauses come before more general ones?",
                options = listOf(
                    "Kotlin requires catch clauses in alphabetical order",
                    "catch clauses are checked in order, and the first type match wins — a general one placed first would shadow specific ones below it",
                    "Order doesn't matter — Kotlin checks all catch clauses and picks the best match",
                    "Only the last catch clause in a chain is ever reachable",
                ),
                correctIndex = 1,
                explanation = "catch clauses are checked top to bottom, and the first type match wins, so a broad supertype listed first would catch everything, leaving more specific clauses below it unreachable.",
            ),
            QuizQuestion(
                id = "catching-exceptions-q2",
                topicId = "catching-exceptions",
                question = "When does a finally block run?",
                options = listOf(
                    "Only if no exception was thrown",
                    "Only if an exception was caught",
                    "Always — whether the try succeeded, an exception was caught, or one propagated uncaught",
                    "Only if the try block explicitly returns a value",
                ),
                correctIndex = 2,
                explanation = "finally runs unconditionally after the try/catch logic completes, making it reliable for cleanup regardless of the outcome.",
            ),
            QuizQuestion(
                id = "catching-exceptions-q3",
                topicId = "catching-exceptions",
                question = "How does Kotlin's exception handling differ from Java's regarding checked exceptions?",
                options = listOf(
                    "Kotlin requires a throws clause on every function that can throw",
                    "Kotlin has no checked exceptions — nothing is declared or enforced by the compiler",
                    "Kotlin forbids catching more than one exception type per try block",
                    "Kotlin only allows exceptions to be caught inside main()",
                ),
                correctIndex = 1,
                explanation = "Kotlin drops Java's checked-exceptions concept entirely; there's no throws keyword and the compiler never forces you to handle or declare thrown exceptions.",
            ),
        ),
        tutorFocus = "Drill catch-clause ordering with a concrete failure: write catch (e: Exception) first and catch (e: NumberFormatException) after it, and show that the specific clause is now dead, unreachable code. Exercise: write a function that parses user input into an Int, catching NumberFormatException specifically with a helpful message and a generic Exception as a fallback, using try as an expression to return the parsed value or a default.",
    ),
)
