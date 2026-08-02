package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val kotlinLibrariesTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "test-library",
        title = "Test Library",
        category = "Kotlin Libraries",
        recap = Recap(
            previousTopicTitle = "CI/CD: TeamCity",
            recapText = "TeamCity (and CI servers generally) build and run your test suite automatically on every push, catching regressions before they reach production.",
            quickCheckQuestion = "What does a CI server run automatically on every push?",
            quickCheckAnswer = "Your build and test suite, to catch regressions early.",
        ),
        explain = "kotlin.test is Kotlin's official multiplatform assertion library — a thin, common API (assertEquals, assertTrue, assertFalse, assertNull, assertFailsWith, etc.) that works the same whether your tests run on the JVM, JS, or Native. Under the hood, kotlin.test delegates to whatever test framework is actually running the tests: on the JVM that's typically JUnit. You mark test functions with @Test (kotlin.test's own annotation, which maps onto the underlying framework's), and the assertions throw the framework's native failure exceptions so IDE and CI tooling report failures normally. To wire kotlin.test to a specific JVM runner, you add an adapter artifact — kotlin-test-junit for JUnit 4, or kotlin-test-junit5 for JUnit 5 (Jupiter) — alongside the kotlin-test dependency itself.",
        example = """
            |import kotlin.test.Test
            |import kotlin.test.assertEquals
            |import kotlin.test.assertTrue
            |import kotlin.test.assertFailsWith
            |
            |class MathUtilsTest {
            |
            |    @Test
            |    fun `sum of two positives is positive`() {
            |        val result = 2 + 3
            |        assertEquals(5, result)
            |        assertTrue(result > 0)
            |    }
            |
            |    @Test
            |    fun `division by zero throws`() {
            |        assertFailsWith<ArithmeticException> {
            |            val x = 1 / 0
            |        }
            |    }
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "kotlin.test provides one assertion API that works across JVM, JS, and Native targets.",
            "@Test from kotlin.test maps onto the platform's real test framework — JUnit on the JVM.",
            "Add kotlin-test-junit (JUnit 4) or kotlin-test-junit5 (JUnit 5) to actually run JVM tests.",
            "assertFailsWith<T> { ... } asserts that a block throws an exception of type T.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "test-library-q1",
                topicId = "test-library",
                question = "Why does kotlin.test exist alongside JUnit rather than just using JUnit directly?",
                options = listOf(
                    "It's faster than JUnit at runtime",
                    "It gives one common assertion API that works across JVM, JS, and Native targets",
                    "JUnit cannot be used from Kotlin at all",
                    "It replaces the need for a build tool",
                ),
                correctIndex = 1,
                explanation = "kotlin.test is a multiplatform-friendly facade; on each platform it delegates to that platform's native test framework, which is JUnit on the JVM.",
            ),
            QuizQuestion(
                id = "test-library-q2",
                topicId = "test-library",
                question = "What does adding kotlin-test-junit5 to a JVM project's dependencies do?",
                options = listOf(
                    "Wires kotlin.test's @Test and assertions to run on JUnit 5 (Jupiter)",
                    "Adds a new assertion syntax unrelated to kotlin.test",
                    "Disables JUnit and replaces it entirely",
                    "Is only needed for Android instrumented tests",
                ),
                correctIndex = 0,
                explanation = "kotlin-test-junit5 is the adapter artifact that connects kotlin.test's common API to JUnit 5 as the actual runner on the JVM.",
            ),
            QuizQuestion(
                id = "test-library-q3",
                topicId = "test-library",
                question = "What does assertFailsWith<ArithmeticException> { 1 / 0 } do?",
                options = listOf(
                    "Fails the test unless 1 / 0 throws an ArithmeticException",
                    "Silently ignores any exception thrown inside the block",
                    "Only compiles but never runs the block",
                    "Asserts that the block returns null",
                ),
                correctIndex = 0,
                explanation = "assertFailsWith<T> runs the block and asserts it throws an exception assignable to T; if it doesn't throw (or throws the wrong type), the assertion fails.",
            ),
        ),
        tutorFocus = "Emphasize that kotlin.test is a thin common-API layer over a real framework (JUnit on the JVM), not a replacement for it. Exercise: write a small class with a function that can throw, then write a kotlin.test test using assertEquals for the happy path and assertFailsWith for the error path.",
    ),
    CurriculumTopic(
        id = "coroutines-library",
        title = "Coroutines",
        category = "Kotlin Libraries",
        recap = Recap(
            previousTopicTitle = "Test Library",
            recapText = "kotlin.test gives one assertion API across platforms, delegating to a real framework — JUnit on the JVM via kotlin-test-junit/kotlin-test-junit5.",
            quickCheckQuestion = "What does kotlin-test-junit5 actually do?",
            quickCheckAnswer = "It wires kotlin.test's API to run on JUnit 5 as the real test runner.",
        ),
        explain = "Coroutines in Kotlin are not a language builtin the way async/await is baked into some other languages — the compiler only provides one small primitive, the suspend modifier, plus continuation-passing-style compilation for suspend functions. Everything else — Job, Deferred, launch, async, Dispatchers, structured concurrency scopes — is ordinary library code shipped in kotlinx-coroutines-core, an artifact you add to your project like any other dependency: implementation(\"org.jetbrains.kotlinx:kotlinx-coroutines-core:1.11.0\"). Because it's a library, the ecosystem ships focused companion artifacts on top of that core: kotlinx-coroutines-android supplies Dispatchers.Main backed by the Android main looper, and kotlinx-coroutines-test supplies TestDispatcher/runTest for deterministic coroutine unit tests. This app depends on exactly this split — kotlinx-coroutines-android for runtime use and kotlinx-coroutines-test for tests.",
        example = """
            |// app/build.gradle.kts (excerpt) — coroutines as ordinary Gradle dependencies
            |dependencies {
            |    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.11.0")   // Dispatchers.Main on Android
            |    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.11.0")  // runTest, TestDispatcher
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "The compiler only knows about suspend; launch, async, Job, and Dispatchers are library APIs from kotlinx-coroutines-core.",
            "kotlinx-coroutines-core is a normal Gradle dependency with a version you pin, just like any other library.",
            "kotlinx-coroutines-android adds Dispatchers.Main backed by the Android main thread's looper.",
            "kotlinx-coroutines-test adds TestDispatcher and runTest for deterministic, fast-forwarded coroutine tests.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "coroutines-library-q1",
                topicId = "coroutines-library",
                question = "What part of coroutines is actually a compiler/language feature?",
                options = listOf(
                    "launch and async",
                    "The suspend modifier and its continuation-passing-style compilation",
                    "Dispatchers.IO and Dispatchers.Main",
                    "Job and Deferred",
                ),
                correctIndex = 1,
                explanation = "The compiler's job is limited to compiling suspend functions; builders like launch/async and types like Job/Deferred are all implemented as ordinary code in kotlinx-coroutines-core.",
            ),
            QuizQuestion(
                id = "coroutines-library-q2",
                topicId = "coroutines-library",
                question = "Which Gradle dependency provides launch, async, Job, and the core Dispatchers?",
                options = listOf(
                    "kotlinx-coroutines-android",
                    "kotlinx-coroutines-test",
                    "kotlinx-coroutines-core",
                    "kotlin-stdlib alone, with no extra dependency",
                ),
                correctIndex = 2,
                explanation = "kotlinx-coroutines-core is the base artifact that ships the coroutine builders, Job/Deferred, and the standard dispatchers.",
            ),
            QuizQuestion(
                id = "coroutines-library-q3",
                topicId = "coroutines-library",
                question = "What does kotlinx-coroutines-test add on top of kotlinx-coroutines-core?",
                options = listOf(
                    "Dispatchers.Main backed by the Android looper",
                    "TestDispatcher and runTest for deterministic coroutine unit tests",
                    "The suspend keyword itself",
                    "JSON serialization support for coroutine results",
                ),
                correctIndex = 1,
                explanation = "kotlinx-coroutines-test supplies test-focused tooling like runTest and TestDispatcher so coroutine-based code can be unit tested deterministically without real delays.",
            ),
        ),
        tutorFocus = "Make the library-vs-language distinction concrete: suspend is compiler magic, everything else (launch, async, Dispatchers) is a Kotlin library you could in principle swap out. Exercise: have the learner find kotlinx-coroutines-android and kotlinx-coroutines-test in this app's build.gradle.kts and explain why each is needed.",
    ),
    CurriculumTopic(
        id = "io-library",
        title = "I/O Library",
        category = "Kotlin Libraries",
        recap = Recap(
            previousTopicTitle = "Coroutines",
            recapText = "Coroutines live in the kotlinx-coroutines-core library, built on the suspend compiler primitive; kotlinx-coroutines-android and -test add platform/testing support.",
            quickCheckQuestion = "What's the one thing the compiler actually knows about coroutines?",
            quickCheckAnswer = "The suspend modifier — everything else (launch, async, Job) is library code.",
        ),
        explain = "kotlin.io is not a replacement for java.io — it's a set of extension functions that Kotlin adds on top of Java's existing I/O classes (File, InputStream, OutputStream, Reader, Writer) to make common cases concise. java.io.File already exists on the JVM/Android; kotlin.io adds convenience methods directly onto it, like File.readText(), File.writeText(...), and File.readLines(), so you don't have to manually open a stream, wrap it in a reader, loop, and close it for simple whole-file operations. This is the same pattern Kotlin uses throughout its standard library: keep the underlying Java type, layer extension functions on top for ergonomics.",
        example = """
            |import java.io.File
            |
            |fun main() {
            |    val file = File("notes.txt")
            |
            |    file.writeText("first line\n")          // kotlin.io extension on java.io.File
            |    file.appendText("second line\n")
            |
            |    val contents = file.readText()           // whole file as one String
            |    println(contents)
            |
            |    val lines = file.readLines()              // whole file as List<String>
            |    println(lines.size)                       // 2
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "kotlin.io adds extension functions onto java.io.File; it doesn't replace java.io.",
            "readText()/writeText() and readLines() handle streams, charsets, and closing for you.",
            "These whole-file helpers load everything into memory — fine for small files, not for huge ones.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "io-library-q1",
                topicId = "io-library",
                question = "What is kotlin.io, relative to java.io?",
                options = listOf(
                    "A full replacement for java.io's classes",
                    "A set of extension functions layered on top of java.io's existing classes",
                    "A networking library unrelated to java.io",
                    "A code generator that produces java.io boilerplate",
                ),
                correctIndex = 1,
                explanation = "kotlin.io adds extension functions (like File.readText()) directly onto java.io types such as File — the underlying class is still java.io.File.",
            ),
            QuizQuestion(
                id = "io-library-q2",
                topicId = "io-library",
                question = "What does File.readLines() return?",
                options = listOf(
                    "A single String with the whole file's contents",
                    "A List<String>, one entry per line",
                    "An InputStream you must close manually",
                    "Nothing — it writes lines to stdout",
                ),
                correctIndex = 1,
                explanation = "readLines() reads the whole file and returns its lines as a List<String>.",
            ),
            QuizQuestion(
                id = "io-library-q3",
                topicId = "io-library",
                question = "What's a downside of File.readText() on a very large file?",
                options = listOf(
                    "It only works on files under 1 KB",
                    "It requires manually managing a Reader",
                    "It loads the entire file into memory at once",
                    "It cannot read UTF-8 encoded files",
                ),
                correctIndex = 2,
                explanation = "readText() reads the whole file into a single String in memory, which doesn't scale to very large files.",
            ),
        ),
        tutorFocus = "Stress that kotlin.io is extension functions over java.io, not a new type hierarchy — readText/writeText/readLines are conveniences for the common, small-file case. Exercise: write a small script that writes a few lines with writeText, appends one with appendText, then reads it back with both readText and readLines.",
    ),
    CurriculumTopic(
        id = "date-time",
        title = "Date & Time",
        category = "Kotlin Libraries",
        recap = Recap(
            previousTopicTitle = "I/O Library",
            recapText = "kotlin.io extends java.io.File with convenience functions like readText()/writeText()/readLines(); it layers on top of java.io rather than replacing it.",
            quickCheckQuestion = "Does kotlin.io replace java.io's classes?",
            quickCheckAnswer = "No — it adds extension functions on top of them (e.g. onto File).",
        ),
        explain = "Kotlin has two main options for date/time work. On the JVM/Android, java.time (introduced in Java 8) is the mature, full-featured standard: LocalDate, LocalDateTime, Instant, ZonedDateTime, Duration, and a rich set of formatting and arithmetic APIs. kotlinx-datetime is JetBrains's official multiplatform date/time library — it has its own Instant and LocalDateTime types, plus Clock.System.now() to get the current instant, and works identically on JVM, JS, Native, and other Kotlin Multiplatform targets, because java.time isn't available outside the JVM. The rule of thumb: if your code is Kotlin Multiplatform (shared logic across platforms), use kotlinx-datetime; if your code is JVM/Android-only, java.time is more mature and full-featured, and is usually the better default.",
        example = """
            |// kotlinx-datetime — works on JVM, JS, Native alike
            |import kotlinx.datetime.Clock
            |import kotlinx.datetime.Instant
            |import kotlinx.datetime.TimeZone
            |import kotlinx.datetime.toLocalDateTime
            |
            |fun kotlinxDatetimeExample() {
            |    val now: Instant = Clock.System.now()
            |    val local = now.toLocalDateTime(TimeZone.currentSystemDefault())
            |    println(local)                          // e.g. 2026-08-02T14:03:11.123
            |}
            |
            |// java.time — JVM/Android only, more mature and full-featured
            |import java.time.LocalDateTime
            |import java.time.ZoneId
            |
            |fun javaTimeExample() {
            |    val now = LocalDateTime.now(ZoneId.systemDefault())
            |    println(now)
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "kotlinx-datetime is JetBrains's official multiplatform date/time library, with its own Instant/LocalDateTime.",
            "Clock.System.now() is kotlinx-datetime's entry point for the current instant.",
            "java.time (LocalDate, LocalDateTime, Instant, etc.) is JVM/Android-only, but more mature and full-featured.",
            "Prefer kotlinx-datetime for shared Kotlin Multiplatform code; prefer java.time for JVM/Android-only code.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "date-time-q1",
                topicId = "date-time",
                question = "Why does kotlinx-datetime exist when java.time already covers dates and times?",
                options = listOf(
                    "kotlinx-datetime is faster than java.time on the JVM",
                    "java.time isn't available on non-JVM Kotlin Multiplatform targets like JS or Native",
                    "java.time was deprecated by Oracle",
                    "kotlinx-datetime is required for any Android app",
                ),
                correctIndex = 1,
                explanation = "java.time is a JVM API; kotlinx-datetime exists so multiplatform Kotlin code (JVM, JS, Native, etc.) can share date/time logic without depending on the JVM-only java.time.",
            ),
            QuizQuestion(
                id = "date-time-q2",
                topicId = "date-time",
                question = "What does Clock.System.now() return in kotlinx-datetime?",
                options = listOf(
                    "A formatted date String",
                    "The current Instant",
                    "A java.time.LocalDateTime",
                    "The device's time zone only",
                ),
                correctIndex = 1,
                explanation = "Clock.System.now() is kotlinx-datetime's way to get the current Instant, analogous to java.time's Instant.now().",
            ),
            QuizQuestion(
                id = "date-time-q3",
                topicId = "date-time",
                question = "For an Android-only app with no multiplatform target, which is generally the better default?",
                options = listOf(
                    "kotlinx-datetime, because it's newer",
                    "java.time, since it's more mature and full-featured on the JVM/Android",
                    "Neither — always parse dates as raw Strings",
                    "Both are required together for every date operation",
                ),
                correctIndex = 1,
                explanation = "For JVM/Android-only code, java.time is the more mature, complete option; kotlinx-datetime's main advantage is portability across non-JVM targets, which doesn't matter for an Android-only app.",
            ),
        ),
        tutorFocus = "Frame this as a decision, not a fact dump: multiplatform code reaches for kotlinx-datetime, Android/JVM-only code usually reaches for java.time. Exercise: have the learner get 'now' both ways (Clock.System.now() and LocalDateTime.now()) and explain which they'd pick for a shared KMP module vs. an Android-only feature.",
    ),
    CurriculumTopic(
        id = "jvm-metadata",
        title = "JVM Metadata",
        category = "Kotlin Libraries",
        recap = Recap(
            previousTopicTitle = "Date & Time",
            recapText = "Prefer kotlinx-datetime for multiplatform code (Instant, Clock.System.now()); prefer java.time for JVM/Android-only code, since it's more mature.",
            quickCheckQuestion = "When would you reach for kotlinx-datetime instead of java.time?",
            quickCheckAnswer = "When the code needs to run on non-JVM Kotlin Multiplatform targets.",
        ),
        explain = "Plain JVM bytecode can't express everything Kotlin needs to know about a class — things like which parameter types are nullable, which functions are suspend functions, or which classes are data classes or companion objects. So the Kotlin compiler stamps every compiled class with a @kotlin.Metadata annotation containing this Kotlin-specific information in a compact, versioned binary format. Other Kotlin tooling (the compiler itself, reflection, IDEs) reads this metadata back to reconstruct Kotlin-level semantics that plain .class files don't capture. Because the metadata format is versioned and tied to the Kotlin compiler/stdlib version that produced it, mixing dependencies compiled by very different Kotlin versions can trigger errors like \"class was compiled with an incompatible version of Kotlin.\" This is exactly why real projects pin compatible versions across their Kotlin-related dependencies — this app's own gradle/libs.versions.toml pins kotlin = \"2.1.20\" alongside matching kotlinx-coroutines, kotlinx-serialization-json, and Ktor versions, precisely to avoid @kotlin.Metadata version mismatches between libraries.",
        example = """
            |// Decompiled sketch of what the compiler attaches to every Kotlin class —
            |// you never write this by hand, but every class you compile carries it.
            |@kotlin.Metadata(
            |    mv = intArrayOf(2, 1, 0),   // metadata format version, tied to the Kotlin compiler version
            |    k = 1,                      // kind: class, file, synthetic class, multi-file class...
            |    d1 = arrayOf(/* compact encoded Kotlin-specific info, e.g. nullability, suspend markers */),
            |)
            |class Repository(val name: String)
        """.trimMargin(),
        keyPoints = listOf(
            "@kotlin.Metadata carries Kotlin-specific info (nullability, suspend markers, data-class-ness) that plain JVM bytecode can't express.",
            "The metadata format itself is versioned and tied to the Kotlin compiler/stdlib version that produced it.",
            "\"Compiled with an incompatible version of Kotlin\" errors come from mismatched metadata versions across dependencies.",
            "This is why projects pin consistent Kotlin-related library versions — e.g. this app pinning kotlin, kotlinx-coroutines, kotlinx-serialization-json, and Ktor together in libs.versions.toml.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "jvm-metadata-q1",
                topicId = "jvm-metadata",
                question = "Why does the Kotlin compiler attach @kotlin.Metadata to every compiled class?",
                options = listOf(
                    "To make the .class file smaller",
                    "To store Kotlin-specific information (like nullability and suspend markers) that plain JVM bytecode can't express",
                    "It's only used for debugging line numbers",
                    "To replace the need for a manifest file",
                ),
                correctIndex = 1,
                explanation = "Plain JVM bytecode has no concept of Kotlin nullability, suspend functions, data classes, etc.; @kotlin.Metadata records that Kotlin-specific information so other Kotlin tooling can read it back.",
            ),
            QuizQuestion(
                id = "jvm-metadata-q2",
                topicId = "jvm-metadata",
                question = "What typically causes a \"class was compiled with an incompatible version of Kotlin\" error?",
                options = listOf(
                    "Using too many extension functions",
                    "A missing @JvmStatic annotation",
                    "Depending on libraries whose @kotlin.Metadata was produced by mismatched, incompatible Kotlin compiler/stdlib versions",
                    "Forgetting to add the kotlin-reflect dependency",
                ),
                correctIndex = 2,
                explanation = "Because the metadata format is versioned, a class built with a much newer (or otherwise incompatible) Kotlin compiler can produce metadata that an older Kotlin toolchain can't parse, surfacing as this exact error.",
            ),
            QuizQuestion(
                id = "jvm-metadata-q3",
                topicId = "jvm-metadata",
                question = "How does this app's gradle/libs.versions.toml relate to @kotlin.Metadata version mismatches?",
                options = listOf(
                    "It's unrelated — that file only configures the Android Gradle Plugin",
                    "It pins kotlin, kotlinx-coroutines, kotlinx-serialization-json, and Ktor to compatible versions together, avoiding exactly this class of metadata mismatch",
                    "It disables @kotlin.Metadata generation entirely",
                    "It only affects debug builds, never release builds",
                ),
                correctIndex = 1,
                explanation = "Pinning Kotlin and Kotlin-ecosystem library versions together (as libs.versions.toml does) keeps their @kotlin.Metadata versions compatible, which is exactly the kind of mismatch that causes \"incompatible version of Kotlin\" errors.",
            ),
        ),
        tutorFocus = "Ground this in something the learner can see: open gradle/libs.versions.toml and point out kotlin/coroutines/serializationJson/ktor being pinned together. Exercise: ask the learner to explain, in their own words, why bumping only one Kotlin-ecosystem library's version (leaving the rest pinned) risks a metadata-version error.",
    ),
)
