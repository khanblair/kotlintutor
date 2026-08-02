package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val nullSafetyTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "what-is-null-safety",
        title = "What is Null Safety?",
        category = "Null Safety",
        recap = Recap(
            previousTopicTitle = "Visibility Modifiers",
            recapText = "public (default), private, protected, internal (module-scoped); restrict deliberately.",
            quickCheckQuestion = "Default visibility? What does internal scope to?",
            quickCheckAnswer = "public; the module.",
        ),
        explain = "Kotlin's type system distinguishes nullable from non-nullable types. String can never hold null; String? can. Because nullability is tracked at compile time, the compiler forces you to handle the null case before using a nullable value — eliminating most NullPointerExceptions (Tony Hoare's \"billion-dollar mistake\"). You literally cannot assign null to a non-nullable type, and you cannot call a method on a nullable value without first handling the null.",
        example = """
            |fun main() {
            |    var name: String = "Ada"
            |    // name = null                 // won't compile — String is non-nullable
            |
            |    var nickname: String? = "Ace"  // nullable
            |    nickname = null                // OK
            |
            |    // println(nickname.length)    // won't compile — must handle null first
            |    println(nickname?.length)      // safe: prints null
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "The ? on a type is the whole feature: T vs T?.",
            "The compiler won't let you dereference a nullable value unsafely.",
            "Values from Java are \"platform types\" and bypass these checks — guard them.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "what-is-null-safety-q1",
                topicId = "what-is-null-safety",
                question = "What's the difference between String and String??",
                options = listOf(
                    "No difference, just style",
                    "String? can hold null, plain String cannot",
                    "String? is slower",
                    "String? is for Java interop only",
                ),
                correctIndex = 1,
                explanation = "The ? suffix marks a type as nullable; without it, null is a compile error.",
            ),
            QuizQuestion(
                id = "what-is-null-safety-q2",
                topicId = "what-is-null-safety",
                question = "Why does null safety reduce runtime crashes?",
                options = listOf(
                    "It removes null from the language entirely",
                    "The compiler forces you to handle nulls before use, catching them at compile time",
                    "It automatically retries failed null dereferences",
                    "It converts nulls to empty strings automatically",
                ),
                correctIndex = 1,
                explanation = "The type system forces null handling at compile time instead of letting it surface as a runtime crash.",
            ),
            QuizQuestion(
                id = "what-is-null-safety-q3",
                topicId = "what-is-null-safety",
                question = "Can you assign null to a non-nullable type?",
                options = listOf("Yes, always", "No", "Only inside a companion object", "Only for var, not val"),
                correctIndex = 1,
                explanation = "A non-nullable type can never hold null — the compiler rejects it.",
            ),
        ),
        tutorFocus = "Frame it as \"the compiler is your safety net.\" Show the compile error from dereferencing a T?. Exercise: given a nullable input, ask the learner to make the code compile by handling the null.",
    ),
    CurriculumTopic(
        id = "nullability-check-operators",
        title = "Nullability Operators",
        category = "Null Safety",
        recap = Recap(
            previousTopicTitle = "What is Null Safety?",
            recapText = "T can't be null, T? can; the compiler forces you to handle null before use.",
            quickCheckQuestion = "String vs String?? Assign null to a non-nullable type?",
            quickCheckAnswer = "only String? allows null; no.",
        ),
        explain = "Kotlin provides operators to work with nullable values:\n\n" +
            "- Safe call ?. — calls a member only if the receiver is non-null, else returns null. Chains nicely: a?.b?.c.\n" +
            "- Elvis ?: — provides a fallback when the left side is null: value ?: default.\n" +
            "- Not-null assertion !! — asserts non-null and throws an NPE if wrong. Use sparingly; it opts out of null safety.\n" +
            "- Safe cast as? — casts or returns null on failure.",
        example = """
            |fun lengthOrZero(s: String?): Int = s?.length ?: 0      // safe call + Elvis
            |
            |fun main() {
            |    println(lengthOrZero("hello"))   // 5
            |    println(lengthOrZero(null))      // 0
            |
            |    val maybe: Any? = "text"
            |    val str: String? = maybe as? String    // safe cast → "text"
            |    println(str?.uppercase() ?: "N/A")
            |
            |    val forced: String? = "x"
            |    println(forced!!.length)         // 1 — but throws if forced were null
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Combine ?. with ?: for the common \"use it or fall back\" pattern.",
            "!! should be rare — each one is a place your program can crash; prefer ?., ?:, or a proper null check.",
            "?.let { … } runs a block only when the value is non-null.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "nullability-check-operators-q1",
                topicId = "nullability-check-operators",
                question = "What does a?.b do when a is null?",
                options = listOf(
                    "Throws a NullPointerException",
                    "Returns null without calling b",
                    "Returns b's default value",
                    "It's a compile error",
                ),
                correctIndex = 1,
                explanation = "The safe call operator short-circuits to null instead of calling the member.",
            ),
            QuizQuestion(
                id = "nullability-check-operators-q2",
                topicId = "nullability-check-operators",
                question = "What does the Elvis operator ?: provide?",
                options = listOf(
                    "A way to force-unwrap a nullable value",
                    "A fallback value when the left side is null",
                    "A way to compare two nullable values",
                    "A shorthand for try/catch",
                ),
                correctIndex = 1,
                explanation = "?: supplies a default/fallback expression evaluated only when the left side is null.",
            ),
            QuizQuestion(
                id = "nullability-check-operators-q3",
                topicId = "nullability-check-operators",
                question = "Why should !! be used sparingly?",
                options = listOf(
                    "It's deprecated and will be removed",
                    "It throws an NPE if the value is actually null — opting out of null safety",
                    "It's significantly slower than ?.",
                    "It only works on val, not var",
                ),
                correctIndex = 1,
                explanation = "!! bypasses the compiler's null checks and crashes with an NPE if the value turns out to be null.",
            ),
        ),
        tutorFocus = "Drill the idiomatic ?. + ?: combo and treat !! as a red flag. Review mode: flag every !! in pasted code and suggest a safer rewrite. Exercise: rewrite a defensive if (x != null) chain using ?. and ?:.",
    ),
)
