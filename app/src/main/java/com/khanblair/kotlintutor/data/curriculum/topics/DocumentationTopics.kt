package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val documentationTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "dokka",
        title = "Dokka",
        category = "Documentation",
        recap = Recap(
            previousTopicTitle = "Opt-in Requirements",
            recapText = "@RequiresOptIn marks an experimental API; consumers acknowledge it with @OptIn(MarkerClass::class) or @file:OptIn(...), making experimental usage explicit in the source.",
            quickCheckQuestion = "Why does Kotlin's opt-in mechanism exist at all?",
            quickCheckAnswer = "To surface the risk of relying on an unstable API explicitly, instead of letting it in silently.",
        ),
        explain = "Dokka is Kotlin's official documentation generation tool — the Kotlin equivalent of Java's Javadoc. It reads your source code along with the KDoc comments written above declarations and produces browsable API documentation, most commonly as a set of HTML pages, though it can also generate other formats like Markdown. Dokka is applied to a project as a Gradle plugin; once configured, running its Gradle task walks your public API surface, pulls in the KDoc on each class and function, and assembles a documentation site you can host or publish alongside your library. It understands Kotlin-specific constructs — data classes, extension functions, companion objects — better than Javadoc-style tools would, since it's built specifically for Kotlin.",
        example = """
            |// build.gradle.kts
            |plugins {
            |    id("org.jetbrains.dokka") version "1.9.20"
            |}
            |
            |// Then generate docs with:
            |// ./gradlew dokkaHtml
            |// Output lands in build/dokka/html by default
        """.trimMargin(),
        keyPoints = listOf(
            "Dokka is applied via a Gradle plugin, not a standalone command-line tool you install separately.",
            "It generates docs from your KDoc comments — no comments means sparse, signature-only documentation.",
            "HTML is the most common output format, but Dokka supports others (e.g. Markdown) too.",
            "It's Kotlin-aware, correctly documenting things like extension functions and companion objects.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "dokka-q1",
                topicId = "dokka",
                question = "What is Dokka?",
                options = listOf(
                    "A Kotlin testing framework",
                    "Kotlin's official documentation generation tool, similar to Javadoc",
                    "A dependency injection library",
                    "A linter for Kotlin style violations",
                ),
                correctIndex = 1,
                explanation = "Dokka generates API documentation from source and KDoc comments, playing the same role Javadoc plays for Java.",
            ),
            QuizQuestion(
                id = "dokka-q2",
                topicId = "dokka",
                question = "How is Dokka typically added to a project?",
                options = listOf(
                    "As a Gradle plugin",
                    "By copying a jar file into src/main",
                    "It's built into kotlinc and requires no setup",
                    "As an IntelliJ IDEA plugin only, with no build integration",
                ),
                correctIndex = 0,
                explanation = "Dokka is configured through a Gradle plugin (e.g. id(\"org.jetbrains.dokka\")) applied to the build script.",
            ),
            QuizQuestion(
                id = "dokka-q3",
                topicId = "dokka",
                question = "What does Dokka use as the source for the documentation content it generates?",
                options = listOf(
                    "Comments written in any format anywhere in the file",
                    "KDoc comments on declarations",
                    "A separate manually-written documentation file",
                    "Git commit messages",
                ),
                correctIndex = 1,
                explanation = "Dokka reads KDoc comments (/** ... */) attached to classes, functions, and other declarations to build its documentation output.",
            ),
        ),
        tutorFocus = "Keep this at a conceptual, tooling-overview level — Dokka setup details vary by project. Exercise: have the learner imagine adding the Dokka plugin to this app's build.gradle.kts and predict what dokkaHtml would generate for a documented class.",
    ),
    CurriculumTopic(
        id = "kdoc",
        title = "KDoc",
        category = "Documentation",
        recap = Recap(
            previousTopicTitle = "Dokka",
            recapText = "Dokka is Kotlin's official doc generator — a Gradle plugin that turns KDoc comments into HTML (or other format) API docs.",
            quickCheckQuestion = "How is Dokka typically added to a project?",
            quickCheckAnswer = "As a Gradle plugin.",
        ),
        explain = "KDoc is Kotlin's comment format for documenting declarations — the source that tools like Dokka read. A KDoc comment starts with /** and ends with */, placed directly above the declaration it documents. The first block of text is the general description. After that, block tags document specific parts: @param name describes a parameter, @return describes the return value, @throws ExceptionType explains when an exception is thrown, @see Identifier points to a related declaration, and @sample Identifier embeds example code from another function's body. The body text supports Markdown formatting (bold, code spans, lists, links), which is one difference from Javadoc's HTML-based markup. Another difference: to link to another declaration, KDoc uses square brackets — [Identifier] — instead of Javadoc's {@link Identifier}, and the brackets can reference classes, functions, properties, or parameters in scope.",
        example = """
            |/**
            | * Computes the discounted price for an [amount], applying [rate] as a percentage.
            | *
            | * See also [applyTax] for the companion tax calculation.
            | *
            | * @param amount the original price before discount.
            | * @param rate the discount rate, e.g. 0.1 for 10%.
            | * @return the price after the discount is applied.
            | * @throws IllegalArgumentException if [rate] is not between 0 and 1.
            | */
            |fun applyDiscount(amount: Double, rate: Double): Double {
            |    require(rate in 0.0..1.0) { "rate must be between 0 and 1" }
            |    return amount * (1 - rate)
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "KDoc comments use /** ... */ and sit directly above the declaration they document.",
            "Common block tags: @param, @return, @throws, @see, @sample.",
            "Body text is Markdown, not HTML — bold, code spans, and lists work as you'd expect.",
            "Linking to another declaration uses [Identifier] square brackets, unlike Javadoc's {@link ...}.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "kdoc-q1",
                topicId = "kdoc",
                question = "How does a KDoc comment begin and end?",
                options = listOf("// and end of line", "/* and */", "/** and */", "<!-- and -->"),
                correctIndex = 2,
                explanation = "KDoc comments use /** to open and */ to close, placed directly above the documented declaration.",
            ),
            QuizQuestion(
                id = "kdoc-q2",
                topicId = "kdoc",
                question = "Which block tag documents why a function might throw an exception?",
                options = listOf("@throws", "@exception", "@error", "@fails"),
                correctIndex = 0,
                explanation = "@throws ExceptionType documents when and why a function throws a particular exception.",
            ),
            QuizQuestion(
                id = "kdoc-q3",
                topicId = "kdoc",
                question = "How does KDoc link to another declaration, and how does that differ from Javadoc?",
                options = listOf(
                    "{@link Identifier} — identical to Javadoc",
                    "[Identifier] square brackets — Javadoc uses {@link Identifier} instead",
                    "<<Identifier>> double angle brackets",
                    "KDoc has no way to link between declarations",
                ),
                correctIndex = 1,
                explanation = "KDoc uses square-bracket [Identifier] syntax for links, whereas Javadoc requires the {@link Identifier} tag.",
            ),
        ),
        tutorFocus = "Show a before/after: an undocumented function vs. one with a full KDoc comment. Exercise: have the learner write a KDoc comment (with @param, @return, and a [link] to a related function) for one of this app's existing functions.",
    ),
)
