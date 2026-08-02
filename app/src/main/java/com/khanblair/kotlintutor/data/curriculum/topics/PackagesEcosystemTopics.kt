package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val packagesEcosystemTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "understanding-packages",
        title = "Packages & Imports",
        category = "Packages & Ecosystem",
        recap = Recap(
            previousTopicTitle = "Coroutine Best Practices",
            recapText = "Structured concurrency ties coroutines to scopes; pick dispatchers (IO/Default/Main); cancellation is cooperative.",
            quickCheckQuestion = "Dispatcher for blocking I/O? Why avoid GlobalScope?",
            quickCheckAnswer = "Dispatchers.IO; it has no lifecycle, so it can leak.",
        ),
        explain = "A package groups related declarations and forms a namespace, declared with package at the top of a file (conventionally matching the folder path, e.g. com.example.app). You bring declarations from other packages into scope with import. Some declarations from kotlin.* and kotlin.io.* are imported by default. You can alias an import to avoid name clashes with import foo.Bar as FooBar.",
        example = """
            |package com.example.demo
            |
            |import kotlin.math.max              // explicit import
            |import kotlin.math.PI as Pi         // aliased import
            |
            |fun main() {
            |    println(max(3, 7))              // 7
            |    println(Pi)                     // 3.14159...
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Package name should mirror the directory structure by convention.",
            "Default imports mean you don't import basics like println or listOf.",
            "Use import aliases to resolve naming conflicts cleanly.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "understanding-packages-q1",
                topicId = "understanding-packages",
                question = "What does a package provide?",
                options = listOf(
                    "A build target",
                    "A namespace grouping related code",
                    "A runtime permission boundary",
                    "A compiled binary format",
                ),
                correctIndex = 1,
                explanation = "Packages group related declarations under a shared namespace.",
            ),
            QuizQuestion(
                id = "understanding-packages-q2",
                topicId = "understanding-packages",
                question = "Why don't you need to import println?",
                options = listOf(
                    "It's a keyword, not a function",
                    "It's part of the default imports",
                    "It's generated per file automatically",
                    "It's actually always required, just easy to miss",
                ),
                correctIndex = 1,
                explanation = "Kotlin automatically imports core declarations from kotlin.* and kotlin.io.*, including println.",
            ),
            QuizQuestion(
                id = "understanding-packages-q3",
                topicId = "understanding-packages",
                question = "How do you resolve two imported names that clash?",
                options = listOf(
                    "It's not possible — rename one of the declarations",
                    "Alias one with import … as Name",
                    "Always use the fully-qualified name inline every time",
                    "Kotlin resolves clashes automatically by package order",
                ),
                correctIndex = 1,
                explanation = "An import alias (as Name) lets you disambiguate two same-named imports.",
            ),
        ),
        tutorFocus = "Keep it practical — packages as folders/namespaces, imports as bringing names into scope. Exercise: split a small program into two packages and wire them together with imports.",
    ),
    CurriculumTopic(
        id = "standard-library",
        title = "Standard Library",
        category = "Packages & Ecosystem",
        recap = Recap(
            previousTopicTitle = "Packages & Imports",
            recapText = "Packages namespace code (mirroring folders); import brings names in; some are default; alias with as.",
            quickCheckQuestion = "Why no import for println? Resolve a name clash?",
            quickCheckAnswer = "it's a default import; alias with as.",
        ),
        explain = "The Kotlin standard library (kotlin-stdlib) ships with the language and provides the core types and thousands of helper functions: collection operations (map, filter, fold), scope functions (let, apply), string utilities, math (kotlin.math), sequences, ranges, and more. Much of what feels like \"language features\" is actually stdlib functions. Knowing it well means writing less code and fewer bugs.",
        example = """
            |import kotlin.math.sqrt
            |
            |fun main() {
            |    val nums = listOf(4, 9, 16)
            |    println(nums.map { sqrt(it.toDouble()) })   // stdlib: map + kotlin.math.sqrt
            |    println("  trim me  ".trim())               // stdlib string helper
            |    println(listOf(3, 1, 2).sorted())           // stdlib sorting
            |    println((1..5).sumOf { it * it })           // stdlib aggregate
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Before writing a helper, check whether stdlib already has it — it usually does.",
            "The stdlib is organized into packages like kotlin.collections, kotlin.text, kotlin.math.",
            "Many stdlib functions are extension functions, which is why they chain fluently.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "standard-library-q1",
                topicId = "standard-library",
                question = "What kinds of things does the standard library provide?",
                options = listOf(
                    "Only networking utilities",
                    "Core types plus helpers for collections, strings, math, sequences, etc.",
                    "Only Android-specific APIs",
                    "Nothing — it's an empty shell for third-party libraries",
                ),
                correctIndex = 1,
                explanation = "kotlin-stdlib bundles core types with a huge set of helpers across collections, text, math, and more.",
            ),
            QuizQuestion(
                id = "standard-library-q2",
                topicId = "standard-library",
                question = "Why do so many stdlib functions chain together nicely?",
                options = listOf(
                    "They're all suspend functions",
                    "Many are extension functions",
                    "They all return Unit",
                    "Kotlin has special chaining syntax just for stdlib",
                ),
                correctIndex = 1,
                explanation = "Extension functions are what let stdlib calls read fluently, one after another.",
            ),
            QuizQuestion(
                id = "standard-library-q3",
                topicId = "standard-library",
                question = "Where would you look for math functions like sqrt?",
                options = listOf("kotlin.text", "kotlin.collections", "kotlin.math", "kotlin.io"),
                correctIndex = 2,
                explanation = "Math helpers live in the kotlin.math package.",
            ),
        ),
        tutorFocus = "Encourage \"search stdlib first.\" When reviewing code, point out reinvented helpers that stdlib already provides. Exercise: rewrite a hand-written loop (e.g. summing squares) using a single stdlib call like sumOf.",
    ),
    CurriculumTopic(
        id = "serialization",
        title = "Serialization",
        category = "Packages & Ecosystem",
        recap = Recap(
            previousTopicTitle = "Standard Library",
            recapText = "kotlin-stdlib provides core types plus thousands of helpers; many are extension functions, so they chain.",
            quickCheckQuestion = "Where are math functions? Why do stdlib funcs chain?",
            quickCheckAnswer = "kotlin.math; they're extension functions.",
        ),
        explain = "kotlinx.serialization is Kotlin's official multiplatform library for converting objects to and from formats like JSON. You mark a class @Serializable, then use Json.encodeToString(...) and Json.decodeFromString<T>(...). It's compile-time (no reflection required), fast, and integrates with data classes — making it ideal for APIs, config, and persistence. (This app's tutor client uses it for the API payloads.)",
        example = """
            |import kotlinx.serialization.*
            |import kotlinx.serialization.json.*
            |
            |@Serializable
            |data class User(val name: String, val age: Int)
            |
            |fun main() {
            |    val json = Json.encodeToString(User("Ada", 36))
            |    println(json)                               // {"name":"Ada","age":36}
            |
            |    val user = Json.decodeFromString<User>(json)
            |    println(user)                               // User(name=Ada, age=36)
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Requires the kotlin.plugin.serialization Gradle plugin plus the runtime dependency.",
            "Mark every serialized class @Serializable.",
            "Use Json { ignoreUnknownKeys = true } when parsing APIs that may add fields.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "serialization-q1",
                topicId = "serialization",
                question = "What annotation marks a class as serializable?",
                options = listOf("@JsonClass", "@Serializable", "@Parcelize", "@Encodable"),
                correctIndex = 1,
                explanation = "@Serializable is kotlinx.serialization's annotation for marking a class as serializable.",
            ),
            QuizQuestion(
                id = "serialization-q2",
                topicId = "serialization",
                question = "Which functions encode and decode JSON?",
                options = listOf(
                    "Json.toJson and Json.fromJson",
                    "Json.encodeToString and Json.decodeFromString",
                    "Json.write and Json.read",
                    "Json.serialize and Json.deserialize",
                ),
                correctIndex = 1,
                explanation = "kotlinx.serialization's Json object exposes encodeToString and decodeFromString.",
            ),
            QuizQuestion(
                id = "serialization-q3",
                topicId = "serialization",
                question = "What setting helps when an API response adds unexpected fields?",
                options = listOf(
                    "strictMode = false",
                    "ignoreUnknownKeys = true",
                    "allowExtraFields = true",
                    "lenient = strict",
                ),
                correctIndex = 1,
                explanation = "ignoreUnknownKeys = true prevents decoding failures when the JSON has fields not present in the data class.",
            ),
        ),
        tutorFocus = "Connect it to the app: this is exactly how the tutor's API requests are built. Exercise: model an API response as a @Serializable data class and round-trip it through JSON with ignoreUnknownKeys enabled.",
    ),
    CurriculumTopic(
        id = "gradle",
        title = "Build Tools",
        category = "Packages & Ecosystem",
        recap = Recap(
            previousTopicTitle = "Serialization",
            recapText = "kotlinx.serialization: mark @Serializable, use Json.encodeToString/decodeFromString; ignoreUnknownKeys for APIs.",
            quickCheckQuestion = "Annotation to serialize? Handle unexpected JSON fields?",
            quickCheckAnswer = "@Serializable; ignoreUnknownKeys = true.",
        ),
        explain = "Kotlin projects are built with Gradle (most common, especially on Android) or Maven. Gradle build scripts are usually written in Kotlin DSL (build.gradle.kts), where you declare plugins, dependencies, and configuration. Dependencies are fetched from repositories like Maven Central. Understanding the basics — plugins block, dependencies block, and versions — is enough to get moving; depth comes later.",
        example = """
            |// build.gradle.kts (excerpt)
            |plugins {
            |    kotlin("jvm") version "2.0.0"
            |    kotlin("plugin.serialization") version "2.0.0"
            |}
            |
            |dependencies {
            |    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")
            |    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "build.gradle.kts is itself Kotlin — the DSL is Kotlin code.",
            "implementation(...) adds a dependency; the coordinate is group:artifact:version.",
            "Keep plugin and library versions compatible with your Kotlin version.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "gradle-q1",
                topicId = "gradle",
                question = "What are the two main build tools for Kotlin?",
                options = listOf("Gradle and Maven", "Gradle and Bazel", "Maven and Make", "Ant and Gradle"),
                correctIndex = 0,
                explanation = "Gradle and Maven are the two dominant build tools in the Kotlin/JVM ecosystem.",
            ),
            QuizQuestion(
                id = "gradle-q2",
                topicId = "gradle",
                question = "What language is build.gradle.kts written in?",
                options = listOf("Groovy", "XML", "Kotlin (the Gradle Kotlin DSL)", "JSON"),
                correctIndex = 2,
                explanation = "The .kts extension signals Gradle's Kotlin DSL — the build script is itself Kotlin code.",
            ),
            QuizQuestion(
                id = "gradle-q3",
                topicId = "gradle",
                question = "What does an implementation(...) line do?",
                options = listOf(
                    "Declares a dependency in the form group:artifact:version",
                    "Implements an interface for the whole module",
                    "Registers a new Gradle task",
                    "Declares the app's entry point",
                ),
                correctIndex = 0,
                explanation = "implementation(...) adds a library dependency using its Maven coordinate.",
            ),
        ),
        tutorFocus = "Demystify Gradle — plugins + dependencies + versions is the 80%. Tie it to the app's own build.gradle.kts. Exercise: read the app's dependencies block and identify which library provides coroutines vs serialization.",
    ),
)
