package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val introductionTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "why-use-kotlin",
        title = "Why use Kotlin",
        category = "Introduction to Kotlin",
        recap = null,
        explain = "Kotlin is a modern, statically-typed programming language that runs on the Java Virtual Machine (JVM), and also compiles to JavaScript, native binaries, and WebAssembly. It was designed to fix long-standing pain points in Java while staying 100% interoperable with it. The headline reasons developers choose Kotlin:\n\n" +
            "- Conciseness. A data class that would take dozens of lines in Java is one line in Kotlin. Boilerplate like getters, setters, equals, and hashCode is generated for you.\n" +
            "- Null safety. Nullability is part of the type system, so the compiler stops most NullPointerExceptions before your program ever runs.\n" +
            "- Interoperability. You can call Java from Kotlin and Kotlin from Java in the same project, which makes gradual adoption painless.\n" +
            "- Official Android support. Google endorses Kotlin as the preferred language for Android development, and most modern Android APIs are Kotlin-first.\n" +
            "- Coroutines. First-class, lightweight concurrency built into the language and standard library.",
        example = """
            |// A complete, immutable value type with equals/hashCode/toString/copy — one line.
            |data class User(val name: String, val age: Int)
            |
            |fun main() {
            |    val user = User("Ada", 36)
            |    println(user)            // User(name=Ada, age=36)
            |    val older = user.copy(age = 37)
            |    println(older)
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Kotlin is not \"a different runtime\" — on the JVM it produces ordinary bytecode and uses Java libraries.",
            "Conciseness is a means, not a goal: readability still matters.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "why-use-kotlin-q1",
                topicId = "why-use-kotlin",
                question = "Which two problems was Kotlin designed to reduce compared to Java?",
                options = listOf(
                    "Slow compile times and large binary size",
                    "Null-pointer errors and boilerplate",
                    "Lack of a package manager and no IDE support",
                    "Weak typing and no generics",
                ),
                correctIndex = 1,
                explanation = "Kotlin's headline goals are cutting boilerplate (e.g. data classes) and eliminating most NullPointerExceptions via the type system.",
            ),
            QuizQuestion(
                id = "why-use-kotlin-q2",
                topicId = "why-use-kotlin",
                question = "True or false: Kotlin can only run on the JVM.",
                options = listOf(
                    "True — it's JVM-only",
                    "False — it also targets JS, native, and WASM",
                    "True, unless you use Android",
                    "False — but only on iOS",
                ),
                correctIndex = 1,
                explanation = "Kotlin compiles to JVM bytecode, JavaScript, native binaries, and WebAssembly.",
            ),
            QuizQuestion(
                id = "why-use-kotlin-q3",
                topicId = "why-use-kotlin",
                question = "Which company promotes Kotlin as the preferred Android language?",
                options = listOf("Oracle", "JetBrains", "Google", "Microsoft"),
                correctIndex = 2,
                explanation = "Google made Kotlin its preferred language for Android development.",
            ),
        ),
        tutorFocus = "Keep this motivational and concrete. Contrast a Java snippet with its Kotlin equivalent to make conciseness felt, not just claimed. Probe the misconception that Kotlin replaces the JVM. Good exercise: ask the learner to list one feature they hope Kotlin improves, then confirm whether Kotlin addresses it.",
    ),
    CurriculumTopic(
        id = "history-of-kotlin",
        title = "History of Kotlin",
        category = "Introduction to Kotlin",
        recap = Recap(
            previousTopicTitle = "Why use Kotlin",
            recapText = "Kotlin is a concise, null-safe, fully Java-interoperable JVM language, and Google's preferred language for Android.",
            quickCheckQuestion = "Name one thing Kotlin reduces vs Java. Can it run outside the JVM?",
            quickCheckAnswer = "null errors / boilerplate; yes (JS, native, WASM).",
        ),
        explain = "Kotlin was created by JetBrains (makers of IntelliJ IDEA) and first announced in 2011, with version 1.0 released in 2016. It's named after Kotlin Island near St. Petersburg. In 2017, Google announced first-class support for Kotlin on Android, and in 2019 Google made Kotlin its preferred language for Android development. The language is open source and governed with input from the Kotlin Foundation (a partnership between JetBrains and Google).",
        example = "",
        keyPoints = listOf(
            "JetBrains builds the tooling and the language, which is why IntelliJ / Android Studio support is excellent.",
            "Kotlin's evolution is deliberate and backward-compatibility-conscious.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "history-of-kotlin-q1",
                topicId = "history-of-kotlin",
                question = "Which company created Kotlin?",
                options = listOf("Google", "JetBrains", "Oracle", "The Apache Foundation"),
                correctIndex = 1,
                explanation = "JetBrains, makers of IntelliJ IDEA, created Kotlin.",
            ),
            QuizQuestion(
                id = "history-of-kotlin-q2",
                topicId = "history-of-kotlin",
                question = "In roughly what year did Kotlin reach version 1.0?",
                options = listOf("2011", "2014", "2016", "2019"),
                correctIndex = 2,
                explanation = "Kotlin was announced in 2011 but reached 1.0 in 2016.",
            ),
            QuizQuestion(
                id = "history-of-kotlin-q3",
                topicId = "history-of-kotlin",
                question = "What is the significance of 2017 and 2019 for Kotlin and Android?",
                options = listOf(
                    "Kotlin was open-sourced in 2017 and forked in 2019",
                    "Google announced official Android support in 2017, then made Kotlin preferred in 2019",
                    "Kotlin 1.0 shipped in 2017 and 2.0 in 2019",
                    "Android stopped supporting Java in 2017 and 2019",
                ),
                correctIndex = 1,
                explanation = "Google announced first-class Android support in 2017 and made Kotlin the preferred Android language in 2019.",
            ),
        ),
        tutorFocus = "Brief and factual — this topic is context, not a skill. If the learner is impatient, the tutor should offer to move straight to hands-on basics.",
    ),
    CurriculumTopic(
        id = "java-interoperability-intro",
        title = "Java Interoperability",
        category = "Introduction to Kotlin",
        recap = Recap(
            previousTopicTitle = "History of Kotlin",
            recapText = "Created by JetBrains, 1.0 in 2016; Google backed it for Android (2017) and made it preferred (2019).",
            quickCheckQuestion = "Who made Kotlin? When was 1.0?",
            quickCheckAnswer = "JetBrains; 2016.",
        ),
        explain = "Because Kotlin compiles to JVM bytecode, Kotlin and Java code can coexist and call each other directly. You can add Kotlin files to an existing Java project, use any Java library from Kotlin, and expose Kotlin code to Java.\n\n" +
            "- Calling Java from Kotlin: Java classes appear as normal Kotlin types. Getters/setters become properties (user.getName() becomes user.name).\n" +
            "- Calling Kotlin from Java: Kotlin funs become methods; top-level functions live in a generated FileNameKt class. Annotations like @JvmStatic, @JvmField, and @JvmName fine-tune the Java-facing API.\n" +
            "- Platform types: values coming from Java have unknown nullability (shown as String!). Kotlin trusts you here, so guard Java values that might be null.",
        example = """
            |import java.util.ArrayList   // a Java class, used as if it were Kotlin
            |
            |fun main() {
            |    val list = ArrayList<String>()   // Java type
            |    list.add("Kotlin")
            |    list.add("Java")
            |    for (item in list) println(item) // Kotlin for-loop over a Java collection
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Platform types are the classic trap: the compiler won't force a null check on a Java-returned value, so a NullPointerException can still sneak in.",
            "Use @Jvm* annotations when designing Kotlin APIs meant to be called from Java.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "java-interoperability-intro-q1",
                topicId = "java-interoperability-intro",
                question = "Where do Kotlin top-level functions end up when viewed from Java?",
                options = listOf(
                    "In a class named after the package",
                    "In a generated class named <FileName>Kt",
                    "As static methods on the first class in the file",
                    "They aren't visible from Java",
                ),
                correctIndex = 1,
                explanation = "The Kotlin compiler generates a class named <FileName>Kt to hold top-level functions for Java callers.",
            ),
            QuizQuestion(
                id = "java-interoperability-intro-q2",
                topicId = "java-interoperability-intro",
                question = "What is a \"platform type\" and why is it risky?",
                options = listOf(
                    "A type only available on Android; it may not exist on other JVMs",
                    "A type from Java with unknown nullability (String!); the compiler skips null checks on it",
                    "A deprecated type kept only for binary compatibility",
                    "A type that can only be used inside expect/actual declarations",
                ),
                correctIndex = 1,
                explanation = "Platform types come from Java and have unknown nullability, so Kotlin doesn't force a null check — a NullPointerException can slip through.",
            ),
            QuizQuestion(
                id = "java-interoperability-intro-q3",
                topicId = "java-interoperability-intro",
                question = "Which annotation exposes a Kotlin function as a static Java method?",
                options = listOf("@JvmField", "@JvmName", "@JvmStatic", "@JvmOverloads"),
                correctIndex = 2,
                explanation = "@JvmStatic exposes a companion/object function as a static method to Java callers.",
            ),
        ),
        tutorFocus = "Emphasize the practical value (adopt Kotlin gradually) and the one real danger (platform-type nulls). Good exercise: give the learner a Java method signature that may return null and ask them to write safe Kotlin that consumes it.",
    ),
)
