package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val nullSafetyGapTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "nullable-vs-non-nullable",
        title = "Nullable vs Non-nullable",
        category = "Null Safety",
        recap = Recap(
            previousTopicTitle = "Type Aliases",
            recapText = "typealias gives an existing type a new, more readable name (e.g. typealias UserId = String) — it doesn't create a genuinely new type; the alias and the underlying type are fully interchangeable to the compiler.",
            quickCheckQuestion = "Does typealias create a new type, distinct from the one it aliases?",
            quickCheckAnswer = "No — it's purely an alternate name; the compiler treats the alias and the underlying type identically.",
        ),
        explain = "Every type in Kotlin comes in two forms: a non-nullable type like String, which can never hold null, and its nullable counterpart String?, which can. This isn't a runtime convention — it's enforced by the compiler at compile time. Assigning null to a String is a compile error, not something that surfaces later as a crash, and calling a member on a String? without first proving it's non-null (via ?., an explicit null check, !!, or a safe cast) also fails to compile rather than risking a NullPointerException. Because non-nullable is the default, most of your code — parameters, properties, return types — is null-safe automatically; you opt into nullability explicitly by adding ?.\n\nContrast this with Java, where every reference type (String, List<T>, any custom class) is implicitly nullable. The compiler can't tell which references are safe to dereference without extra tooling like @Nullable/@NonNull annotations, and even those are just documentation — nothing stops you from ignoring them, so NullPointerException can strike almost anywhere. When Kotlin calls into Java code, it can't know the true nullability of a Java reference either, so it's exposed as a \"platform type\" (shown as String!) that suppresses Kotlin's compile-time checks for that value — it's on you to guard those the same way you would in Java.",
        example = """
            |fun greet(name: String): String = "Hello, ${'$'}name!"
            |
            |fun greetOptional(name: String?): String {
            |    // return "Hello, ${'$'}{name.length}!"   // won't compile — name might be null
            |    return "Hello, ${'$'}{name ?: "stranger"}!"
            |}
            |
            |fun main() {
            |    val required: String = "Ada"
            |    // val broken: String = null          // compile error: null can't be a String
            |
            |    val optional: String? = null           // fine — String? allows null
            |    println(greet(required))
            |    println(greetOptional(optional))
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "T is non-nullable by default; T? is a distinct, nullable type — not just a runtime flag on the same type.",
            "Nullability is checked at compile time: assigning null to a non-nullable type, or dereferencing a nullable type without a check, is a compile error.",
            "In Java every reference type is implicitly nullable; Kotlin makes nullability an explicit, opt-in part of the type.",
            "Java references seen from Kotlin become \"platform types\" (T!) that bypass compile-time null checks — guard them manually.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "nullable-vs-non-nullable-q1",
                topicId = "nullable-vs-non-nullable",
                question = "When does Kotlin catch a nullability violation like assigning null to a String?",
                options = listOf(
                    "At compile time",
                    "Only at runtime, via a NullPointerException",
                    "Only when the code is unit tested",
                    "Kotlin doesn't check nullability at all",
                ),
                correctIndex = 0,
                explanation = "Nullability is part of the type system, so the compiler rejects assigning null to a non-nullable type before the code ever runs.",
            ),
            QuizQuestion(
                id = "nullable-vs-non-nullable-q2",
                topicId = "nullable-vs-non-nullable",
                question = "How do Java's reference types compare to Kotlin's non-nullable types like String?",
                options = listOf(
                    "Java reference types are implicitly nullable with no compiler enforcement of null-safety",
                    "Java reference types are non-nullable by default, just like Kotlin's",
                    "Java has its own ? suffix for nullable types",
                    "Java references are identical to Kotlin's String? in every way",
                ),
                correctIndex = 0,
                explanation = "Every Java reference type can hold null, and the compiler doesn't enforce null checks — that's exactly what Kotlin's non-nullable types are designed to prevent.",
            ),
            QuizQuestion(
                id = "nullable-vs-non-nullable-q3",
                topicId = "nullable-vs-non-nullable",
                question = "Which of these fails to compile in Kotlin?",
                options = listOf(
                    "val s: String? = null",
                    "val s: String = null",
                    "val s: String = \"hi\"",
                    "val s: String? = \"hi\"",
                ),
                correctIndex = 1,
                explanation = "String is non-nullable, so assigning null to it is rejected by the compiler — String? is required to hold null.",
            ),
        ),
        tutorFocus = "Emphasize that this is a compile-time guarantee, not a runtime check — show the actual compiler error for val s: String = null, then contrast with an equivalent Java snippet that compiles fine and only fails later as a runtime NullPointerException. Exercise: given a Java-style method signature with no nullability info, have the learner decide whether to model the Kotlin parameter as String or String?, and justify the choice via platform types.",
    ),
    CurriculumTopic(
        id = "safe-casts",
        title = "Safe Casts",
        category = "Null Safety",
        recap = Recap(
            previousTopicTitle = "Nullable vs Non-nullable",
            recapText = "T is non-nullable by default and can never hold null; T? explicitly allows null. This is enforced by the compiler at compile time, unlike Java where every reference type is implicitly nullable.",
            quickCheckQuestion = "Does val s: String = null compile in Kotlin?",
            quickCheckAnswer = "No — String is non-nullable, so this is a compile-time error, not a runtime one.",
        ),
        explain = "The as operator performs an unsafe cast: if the value isn't actually an instance of the target type, it throws a ClassCastException at runtime. as? is the safe version — if the cast fails, it evaluates to null instead of throwing, so its result type is always the nullable form of the target (obj as? Number produces Number?, even though Number itself looks non-nullable). This makes as? compose naturally with the rest of the null-safety toolkit: chain it with ?. to keep going only when the cast succeeds, or with ?: to supply a fallback when it fails. A common idiom is val n = (obj as? Number)?.toInt() — pull a value out only if obj really is a Number, otherwise fall through to null (or a default via ?:).",
        example = """
            |fun main() {
            |    val values: List<Any> = listOf(42, "hello", 3.14, true)
            |
            |    for (v in values) {
            |        val n = (v as? Number)?.toInt() ?: -1
            |        println("${'$'}v -> ${'$'}n")
            |    }
            |
            |    // The unsafe cast throws when it's wrong:
            |    val obj: Any = "not a number"
            |    // val forced = obj as Number     // throws ClassCastException at runtime
            |    val safe = obj as? Number         // null — no exception
            |    println(safe)
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "as? never throws: on a failed cast it evaluates to null instead of a ClassCastException.",
            "as? Foo always has the type Foo? — even when the expression being cast is of a non-nullable type.",
            "Pair as? with ?: for a fallback value, or with ?. to keep chaining only when the cast succeeds.",
            "as (without ?) is the unsafe cast — reach for it only when you're certain of the runtime type, or want the crash to be loud.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "safe-casts-q1",
                topicId = "safe-casts",
                question = "What does obj as? Number evaluate to when obj is not actually a Number?",
                options = listOf(
                    "null",
                    "It throws a ClassCastException",
                    "0",
                    "The string \"null\"",
                ),
                correctIndex = 0,
                explanation = "as? returns null on a failed cast instead of throwing, which is exactly what makes it \"safe\".",
            ),
            QuizQuestion(
                id = "safe-casts-q2",
                topicId = "safe-casts",
                question = "How does the as? operator differ from the unsafe as operator?",
                options = listOf(
                    "as? returns null on a failed cast; as throws ClassCastException on a failed cast",
                    "as? is only usable inside coroutines",
                    "as? only works with numeric types",
                    "as always returns null on failure, while as? throws",
                ),
                correctIndex = 0,
                explanation = "as? swaps a thrown ClassCastException for a null result, letting you handle a failed cast with ordinary null-safety tools.",
            ),
            QuizQuestion(
                id = "safe-casts-q3",
                topicId = "safe-casts",
                question = "What is the static type of the expression obj as? String?",
                options = listOf(
                    "String",
                    "String?",
                    "Any",
                    "Nothing",
                ),
                correctIndex = 1,
                explanation = "as? always produces the nullable form of the target type, since the cast might fail and yield null.",
            ),
        ),
        tutorFocus = "Show the ClassCastException thrown by as first, then rewrite the same code with as? to make the difference visceral. Exercise: given a List<Any>, have the learner extract just the Strings using a safe cast inside a map/mapNotNull, without ever throwing on a non-string element.",
    ),
)
