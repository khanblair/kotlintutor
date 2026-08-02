package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val packagesEcosystemGapTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "importing-packages",
        title = "Importing Packages",
        category = "Packages & Ecosystem",
        recap = Recap(
            previousTopicTitle = "Coroutines Behavior",
            recapText = "You've covered how coroutines behave at runtime — suspension, structured concurrency, and cancellation.",
            quickCheckQuestion = "What makes coroutine cancellation cooperative rather than forced?",
            quickCheckAnswer = "Suspending functions must check for cancellation themselves (e.g. at suspension points); nothing forcibly kills them.",
        ),
        explain = "The import statement brings a declaration from another package into scope so you can refer to it by its short name instead of its fully-qualified one. import pkg.Name imports a single class, function, or property. import pkg.* is a wildcard import that brings everything in that package into scope — convenient, but it can make it unclear where a name came from, so many style guides prefer explicit imports. Top-level functions and properties (not just classes) can be imported directly, e.g. import kotlin.math.max. When two imports would clash under the same short name, import foo.Bar as FooBar renames the import at the use site without touching the original declaration. All of this is resolved at compile time — the compiler must be able to find every imported declaration before it will produce any code, so there's no dynamic or runtime import resolution like some scripting languages have.",
        example = """
            |import kotlin.math.max                    // single declaration
            |import kotlin.math.*                       // wildcard: everything in kotlin.math
            |import kotlin.math.PI as Pi                 // aliased import
            |
            |fun main() {
            |    println(max(3, 7))    // 7 — imported directly, called unqualified
            |    println(Pi)           // 3.14159... — using the alias
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "import pkg.Name imports one declaration; import pkg.* imports everything in that package.",
            "Top-level functions and properties can be imported just like classes.",
            "import foo.Bar as FooBar resolves a naming clash without renaming the original declaration.",
            "Imports are resolved at compile time — an unresolved import is a compile error, not a runtime one.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "importing-packages-q1",
                topicId = "importing-packages",
                question = "What does import kotlin.math.* do?",
                options = listOf(
                    "Imports only the math package's classes, not its functions",
                    "Imports every declaration in the kotlin.math package (wildcard import)",
                    "Imports kotlin.math lazily at runtime",
                    "Fails to compile — wildcard imports aren't supported in Kotlin",
                ),
                correctIndex = 1,
                explanation = "The * wildcard brings every public declaration in the kotlin.math package into scope at once.",
            ),
            QuizQuestion(
                id = "importing-packages-q2",
                topicId = "importing-packages",
                question = "How do you resolve a name clash between two imports without renaming either declaration?",
                options = listOf(
                    "Always fully qualify one of the names everywhere it's used",
                    "Use an import alias, e.g. import foo.Bar as FooBar",
                    "Delete one of the imports and hope it isn't needed",
                    "Kotlin picks the more recently declared one automatically",
                ),
                correctIndex = 1,
                explanation = "An aliased import (as Name) renames the import locally, leaving the original declaration untouched.",
            ),
            QuizQuestion(
                id = "importing-packages-q3",
                topicId = "importing-packages",
                question = "When are Kotlin imports resolved?",
                options = listOf(
                    "At runtime, the first time the imported name is used",
                    "At compile time — an unresolved import fails compilation",
                    "Lazily, only if the JVM classloader needs the class",
                    "At app startup, before main() runs",
                ),
                correctIndex = 1,
                explanation = "Import resolution happens during compilation; the compiler must find every imported declaration or the build fails.",
            ),
        ),
        tutorFocus = "Emphasize the difference between explicit and wildcard imports, and when each is appropriate (wildcard is fine for small scopes, explicit is clearer in larger files). Exercise: have the learner write two functions with the same name from different packages and resolve the clash with an import alias.",
    ),
    CurriculumTopic(
        id = "default-imports",
        title = "Default Imports",
        category = "Packages & Ecosystem",
        recap = Recap(
            previousTopicTitle = "Importing Packages",
            recapText = "import pkg.Name imports one declaration, import pkg.* imports everything, and import ... as renames on clash — all resolved at compile time.",
            quickCheckQuestion = "How do you resolve a name clash between two imports without renaming either declaration?",
            quickCheckAnswer = "Alias one of them with import foo.Bar as FooBar.",
        ),
        explain = "Every Kotlin file gets a set of packages imported automatically, with no import statement required. On every platform this includes kotlin.*, kotlin.annotation.*, kotlin.collections.*, kotlin.comparisons.*, kotlin.io.*, kotlin.ranges.*, kotlin.sequences.*, and kotlin.text.*. This is why you can call println (from kotlin.io), use List or listOf (from kotlin.collections), or write a range like 1..10 (from kotlin.ranges) without ever writing an import for them. On top of that common set, each platform adds its own defaults — on Kotlin/JVM that includes kotlin.jvm.* and java.lang.* (so String, Int wrapping, and other java.lang types are available unqualified too). You still need an explicit import for anything outside these default packages, such as kotlin.math.* or any third-party library.",
        example = """
            |// No imports at all — these are all covered by default imports:
            |fun main() {
            |    val numbers: List<Int> = listOf(1, 2, 3)   // kotlin.collections
            |    val range = 1..5                            // kotlin.ranges
            |    val text = "hi".uppercase()                 // kotlin.text
            |    println(numbers)                            // kotlin.io
            |    println(range.toList())
            |    println(text)
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "kotlin.*, kotlin.collections.*, kotlin.io.*, kotlin.ranges.*, kotlin.text.*, and a few others are imported into every file automatically.",
            "This is why println, List, listOf, and range syntax (1..10) work without any import.",
            "Platform-specific defaults add more — kotlin.jvm.* and java.lang.* on the JVM.",
            "Anything outside the default set — like kotlin.math or a third-party library — still needs an explicit import.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "default-imports-q1",
                topicId = "default-imports",
                question = "Why can you call println() without importing anything?",
                options = listOf(
                    "println is a language keyword, not a function",
                    "kotlin.io is one of the packages imported by default into every file",
                    "The IDE inserts the import invisibly at compile time",
                    "println only works inside fun main, which has special import rules",
                ),
                correctIndex = 1,
                explanation = "kotlin.io.* is part of Kotlin's default imports, so println is already in scope everywhere.",
            ),
            QuizQuestion(
                id = "default-imports-q2",
                topicId = "default-imports",
                question = "Which of these is NOT part of Kotlin's common default imports?",
                options = listOf("kotlin.collections.*", "kotlin.text.*", "kotlin.math.*", "kotlin.ranges.*"),
                correctIndex = 2,
                explanation = "kotlin.math is not a default import — functions like sqrt or max still need an explicit import.",
            ),
            QuizQuestion(
                id = "default-imports-q3",
                topicId = "default-imports",
                question = "On Kotlin/JVM specifically, which extra package is imported by default alongside the common set?",
                options = listOf("java.util.*", "java.lang.*", "java.io.*", "java.net.*"),
                correctIndex = 1,
                explanation = "On the JVM target, java.lang.* (plus kotlin.jvm.*) is added to the default imports alongside the common kotlin.* packages.",
            ),
        ),
        tutorFocus = "Point out that default imports are why beginner code 'just works' without imports cluttering the top of the file. Exercise: have the learner list three stdlib calls they've already used (println, listOf, a range) and identify which default package each comes from.",
    ),
    CurriculumTopic(
        id = "opt-in-requirements",
        title = "Opt-in Requirements",
        category = "Packages & Ecosystem",
        recap = Recap(
            previousTopicTitle = "Default Imports",
            recapText = "kotlin.*, kotlin.collections.*, kotlin.io.*, kotlin.ranges.*, kotlin.text.* (and more) are imported into every file automatically; platforms add their own on top.",
            quickCheckQuestion = "Why can you call println() without importing anything?",
            quickCheckAnswer = "kotlin.io is part of Kotlin's default imports.",
        ),
        explain = "Some APIs are experimental — they might still change or be removed in a future release. Rather than hide that risk, Kotlin makes it explicit through opt-in requirements. A library author marks an experimental API with a custom annotation that is itself annotated @RequiresOptIn; using that API from outside the library then triggers a compiler error unless the caller acknowledges the risk. A consumer acknowledges it with @OptIn(MarkerClass::class) on the specific function or class using the API, or with @file:OptIn(MarkerClass::class) at the top of a file to cover everything in it. This surfaces experimental-API usage explicitly in the source rather than letting it slip in silently — anyone reading the code (or diffing it) can see exactly where an unstable API is being relied on. This app uses exactly this pattern: ui/theme/Font.kt has @file:OptIn(ExperimentalTextApi::class) at the top of the file because it uses Compose's experimental variable-font APIs, which are marked with the ExperimentalTextApi opt-in annotation.",
        example = """
            |// Library author's side:
            |@RequiresOptIn(message = "This API is experimental and may change.")
            |@Retention(AnnotationRetention.BINARY)
            |annotation class ExperimentalGreeting
            |
            |@ExperimentalGreeting
            |fun experimentalGreet(name: String) = "Hey there, ${'$'}name!"
            |
            |// Consumer's side — must opt in to call it:
            |@OptIn(ExperimentalGreeting::class)
            |fun main() {
            |    println(experimentalGreet("Ada"))
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "@RequiresOptIn marks a custom annotation as an opt-in marker for an experimental API.",
            "@OptIn(MarkerClass::class) on a declaration, or @file:OptIn(MarkerClass::class) for a whole file, is how a consumer acknowledges using that experimental API.",
            "Without opting in, calling the experimental API is a compile error — the risk can't be missed accidentally.",
            "This codebase's ui/theme/Font.kt uses @file:OptIn(ExperimentalTextApi::class) to use Compose's experimental variable-font support.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "opt-in-requirements-q1",
                topicId = "opt-in-requirements",
                question = "What annotation does a library author use to mark an API as experimental and requiring opt-in?",
                options = listOf("@Deprecated", "@RequiresOptIn", "@JvmStatic", "@Suppress"),
                correctIndex = 1,
                explanation = "@RequiresOptIn is applied to a custom marker annotation, turning it into an opt-in requirement for anything annotated with it.",
            ),
            QuizQuestion(
                id = "opt-in-requirements-q2",
                topicId = "opt-in-requirements",
                question = "How does a consumer acknowledge use of an experimental, opt-in-required API for an entire file?",
                options = listOf(
                    "@OptIn(MarkerClass::class) on every single call site only",
                    "@file:OptIn(MarkerClass::class) at the top of the file",
                    "By adding a comment explaining the risk",
                    "Opt-in cannot be scoped to a file, only to individual declarations",
                ),
                correctIndex = 1,
                explanation = "@file:OptIn(MarkerClass::class) applies the opt-in to every use of that experimental API within the file.",
            ),
            QuizQuestion(
                id = "opt-in-requirements-q3",
                topicId = "opt-in-requirements",
                question = "Why does Kotlin's opt-in mechanism exist at all?",
                options = listOf(
                    "To make experimental APIs run faster",
                    "To surface the risk of relying on an unstable API explicitly in the source, instead of letting it in silently",
                    "It's required by the JVM specification for all annotations",
                    "To automatically generate documentation for experimental features",
                ),
                correctIndex = 1,
                explanation = "Opt-in requirements force experimental-API usage to be visible and deliberate in the code, rather than an easy-to-miss implicit dependency.",
            ),
        ),
        tutorFocus = "Ground this in the app's own code — point to ui/theme/Font.kt's @file:OptIn(ExperimentalTextApi::class) as a real, concrete example. Exercise: have the learner define a tiny @RequiresOptIn marker annotation, apply it to a function, and then call that function from another function using @OptIn.",
    ),
)
