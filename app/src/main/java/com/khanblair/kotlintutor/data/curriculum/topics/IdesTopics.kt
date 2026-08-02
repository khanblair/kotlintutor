package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val idesTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "intellij-idea",
        title = "IntelliJ IDEA",
        category = "IDEs",
        recap = Recap(
            previousTopicTitle = "Buffered Streams",
            recapText = "Buffered streams (BufferedReader/BufferedWriter, or Kotlin's .buffered() extension) wrap raw I/O streams to batch reads and writes in memory, cutting the number of expensive system calls; always close them, ideally with use { }, so buffered data gets flushed.",
            quickCheckQuestion = "Why wrap a stream in a buffered one, and what ensures it actually gets closed?",
            quickCheckAnswer = "Buffering reduces costly system calls by batching I/O in memory; use { } closes the stream automatically, even on error.",
        ),
        explain = "IntelliJ IDEA is JetBrains' flagship IDE, and since JetBrains also created Kotlin, it's the language's primary and reference IDE — the Kotlin plugin is developed by JetBrains and bundled directly into the IDE, so new Kotlin language features and tooling typically land there first. It ships in two editions: Community, which is free and open-source and covers JVM, Android, and general Kotlin/Java development, and Ultimate, a paid edition that adds support for more frameworks and tools (Spring, Java EE, database tools, profilers, and more) aimed at professional and enterprise development. For everyday Kotlin work, IntelliJ IDEA offers smart, context-aware code completion, a built-in Java-to-Kotlin converter (paste Java code into a .kt file, or use Code > Convert Java File to Kotlin File, and IntelliJ rewrites it as idiomatic Kotlin), tightly integrated Gradle and Maven support (dependency resolution, running build tasks, and syncing build files without leaving the editor), and a debugger with coroutine-aware views that let you inspect suspended coroutines and their state rather than just raw threads.",
        example = "",
        keyPoints = listOf(
            "IntelliJ IDEA is built by JetBrains, the same company that created Kotlin, so its Kotlin plugin is bundled and usually the most up to date.",
            "Community edition is free and open-source; Ultimate adds paid support for more frameworks, tools, and enterprise features.",
            "Built-in Gradle/Maven integration and a coroutine-aware debugger make it well suited to real Kotlin projects, not just small scripts.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "intellij-idea-q1",
                topicId = "intellij-idea",
                question = "Why is IntelliJ IDEA considered Kotlin's reference IDE?",
                options = listOf(
                    "It was the first IDE ever released for Java",
                    "JetBrains, which created Kotlin, also builds IntelliJ IDEA and bundles the Kotlin plugin",
                    "It only works with Kotlin, not Java",
                    "Google maintains IntelliJ IDEA specifically for Kotlin",
                ),
                correctIndex = 1,
                explanation = "JetBrains created Kotlin and also builds IntelliJ IDEA, bundling the Kotlin plugin directly into the IDE.",
            ),
            QuizQuestion(
                id = "intellij-idea-q2",
                topicId = "intellij-idea",
                question = "What distinguishes IntelliJ IDEA Ultimate from Community?",
                options = listOf(
                    "Ultimate is free while Community is paid",
                    "Ultimate adds paid support for more frameworks and enterprise tools; Community is free and open-source",
                    "Community only supports Java, not Kotlin",
                    "There is no functional difference, only branding",
                ),
                correctIndex = 1,
                explanation = "Community is the free, open-source edition; Ultimate is paid and adds support for more frameworks and professional/enterprise tooling.",
            ),
            QuizQuestion(
                id = "intellij-idea-q3",
                topicId = "intellij-idea",
                question = "What can IntelliJ IDEA's built-in Java-to-Kotlin converter do?",
                options = listOf(
                    "Automatically rewrite pasted or existing Java code as Kotlin",
                    "Compile Kotlin code down to Java bytecode only",
                    "Convert XML layouts into Compose code",
                    "Translate Kotlin coroutines into Java threads",
                ),
                correctIndex = 0,
                explanation = "IntelliJ IDEA can convert pasted Java code, or an entire .java file, into idiomatic Kotlin.",
            ),
        ),
        tutorFocus = "Keep this practical and tool-oriented rather than testing memorization of edition names. A good check is asking the learner which edition they're using and whether they've tried the Java-to-Kotlin converter or noticed the coroutine debugger views.",
    ),
    CurriculumTopic(
        id = "android-studio-ide",
        title = "Android Studio",
        category = "IDEs",
        recap = Recap(
            previousTopicTitle = "IntelliJ IDEA",
            recapText = "IntelliJ IDEA is JetBrains' flagship IDE and Kotlin's reference IDE, with the Kotlin plugin bundled in; Community is free, Ultimate is paid with more frameworks, and it offers a Java-to-Kotlin converter plus a coroutine-aware debugger.",
            quickCheckQuestion = "What's the key functional difference between the Community and Ultimate editions?",
            quickCheckAnswer = "Community is free and open-source; Ultimate is paid and adds support for more frameworks and enterprise tooling.",
        ),
        explain = "Android Studio is Google's official IDE for Android development, and it's built directly on the IntelliJ Platform — the same open-source foundation JetBrains uses for IntelliJ IDEA. Because of that shared base, Android Studio inherits IntelliJ's core strengths for Kotlin: smart completion, refactoring, and debugging all work the same way. On top of that foundation, Google adds Android-specific tooling: the Layout Inspector (examine a running app's view hierarchy and properties), Logcat (view system and app log output in real time), the APK Analyzer (inspect the contents and size breakdown of a built APK/AAB), built-in emulator management (create and run virtual devices via the AVD Manager), and Compose Preview (render @Composable functions directly in the editor without running the app). Because it ships in step with the Android SDK and is Google's officially supported tool for Play Store development, it's the de facto choice for Android Kotlin development, even though plain IntelliJ IDEA can technically edit the same code.",
        example = "",
        keyPoints = listOf(
            "Android Studio is built on the IntelliJ Platform, so it inherits IntelliJ's Kotlin editing, refactoring, and debugging support.",
            "Android-specific tools — Layout Inspector, Logcat, APK Analyzer, AVD Manager, Compose Preview — aren't part of plain IntelliJ IDEA.",
            "Google maintains and ships it alongside the Android SDK, making it the standard IDE for Android app development.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "android-studio-ide-q1",
                topicId = "android-studio-ide",
                question = "What foundation is Android Studio built on?",
                options = listOf(
                    "A fork of Eclipse",
                    "The IntelliJ Platform, the same base as IntelliJ IDEA",
                    "Visual Studio Code's extension framework",
                    "A custom Google-built IDE core",
                ),
                correctIndex = 1,
                explanation = "Android Studio is built on the IntelliJ Platform, so it shares IntelliJ IDEA's core editing and Kotlin support.",
            ),
            QuizQuestion(
                id = "android-studio-ide-q2",
                topicId = "android-studio-ide",
                question = "Which of these is an Android-specific tool added by Android Studio, not part of plain IntelliJ IDEA?",
                options = listOf(
                    "Smart code completion",
                    "The Layout Inspector",
                    "The debugger",
                    "The built-in terminal",
                ),
                correctIndex = 1,
                explanation = "The Layout Inspector is an Android-specific tool for examining a running app's view hierarchy; it's not part of base IntelliJ IDEA.",
            ),
            QuizQuestion(
                id = "android-studio-ide-q3",
                topicId = "android-studio-ide",
                question = "Why is Android Studio the de facto choice for Android Kotlin development?",
                options = listOf(
                    "It's the only IDE that can compile Kotlin at all",
                    "Google maintains it in step with the Android SDK and it includes Android-specific tooling IntelliJ IDEA lacks",
                    "It's cheaper than IntelliJ IDEA",
                    "It replaced Kotlin support with a proprietary language",
                ),
                correctIndex = 1,
                explanation = "Android Studio is Google's officially supported Android IDE, released alongside SDK updates and packed with Android-specific tools.",
            ),
        ),
        tutorFocus = "Focus on the practical distinction from IntelliJ IDEA — same Kotlin core, plus Android tooling. A good check is asking the learner to name one Android-specific feature they'd reach for that plain IntelliJ IDEA doesn't have.",
    ),
    CurriculumTopic(
        id = "kotlin-notebook",
        title = "Kotlin Notebook",
        category = "IDEs",
        recap = Recap(
            previousTopicTitle = "Android Studio",
            recapText = "Android Studio is Google's official Android IDE, built on the IntelliJ Platform so it inherits Kotlin support, plus Android-specific tools like Layout Inspector, Logcat, APK Analyzer, AVD Manager, and Compose Preview.",
            quickCheckQuestion = "What platform is Android Studio built on, and name one Android-specific tool it adds.",
            quickCheckAnswer = "The IntelliJ Platform; for example the Layout Inspector, Logcat, APK Analyzer, or Compose Preview.",
        ),
        explain = "Kotlin Notebook is a JetBrains plugin for IntelliJ IDEA that brings Jupyter-notebook-style interactive coding to Kotlin. A notebook document is a sequence of cells that can hold executable Kotlin code, Markdown text for narration, or rendered output — including rich output like charts and dataframes — all displayed inline in the same document. This makes it well suited to data exploration and prototyping: it's the natural tool when working with Kotlin DataFrame for tabular data or Kandy for charting (covered later in Data Analysis), where seeing results immediately next to the code that produced them speeds up iteration. The key contrast with a plain .kt script file is state and output: a script re-runs top to bottom every time it's executed and only prints text, while a notebook keeps variables and definitions alive between individual cell runs — so you can tweak one cell and re-run just that cell — and renders rich output such as tables or plots directly beneath the cell instead of only text.",
        example = "",
        keyPoints = listOf(
            "Kotlin Notebook is a JetBrains plugin for IntelliJ IDEA, not a separate standalone application.",
            "Cells mix code, Markdown narration, and rendered output — including charts and dataframes — in one document.",
            "Unlike a .kts script, a notebook keeps state between cell executions, so you can re-run individual cells without starting over.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "kotlin-notebook-q1",
                topicId = "kotlin-notebook",
                question = "What is Kotlin Notebook?",
                options = listOf(
                    "A standalone application unrelated to IntelliJ IDEA",
                    "A JetBrains IntelliJ IDEA plugin for Jupyter-style interactive Kotlin cells",
                    "A command-line REPL with no editor integration",
                    "A Gradle plugin for running unit tests",
                ),
                correctIndex = 1,
                explanation = "Kotlin Notebook is an IntelliJ IDEA plugin providing Jupyter-notebook-style interactive cells for Kotlin.",
            ),
            QuizQuestion(
                id = "kotlin-notebook-q2",
                topicId = "kotlin-notebook",
                question = "What can a Kotlin Notebook cell contain, beyond executable code?",
                options = listOf(
                    "Only compiled bytecode",
                    "Markdown text and rendered rich output like charts or dataframes",
                    "Only shell commands",
                    "Nothing — cells can only hold Kotlin code",
                ),
                correctIndex = 1,
                explanation = "Notebook cells can hold Markdown narration and rendered rich output such as charts and dataframes, alongside code.",
            ),
            QuizQuestion(
                id = "kotlin-notebook-q3",
                topicId = "kotlin-notebook",
                question = "How does a Kotlin Notebook differ from running a plain .kts script?",
                options = listOf(
                    "A notebook only supports Java, not Kotlin",
                    "A notebook keeps state between cell runs and renders rich output inline; a script reruns top to bottom and only prints text",
                    "A script keeps state between runs, but a notebook does not",
                    "There is no meaningful difference between the two",
                ),
                correctIndex = 1,
                explanation = "Notebooks preserve state between individual cell executions and can render rich output inline, unlike a script that reruns entirely each time and only prints text.",
            ),
        ),
        tutorFocus = "Keep this light — it's a tool preview ahead of the Data Analysis category, not a skill to drill yet. A good check is asking the learner to describe, in their own words, how a notebook cell differs from running a .kts script.",
    ),
)
