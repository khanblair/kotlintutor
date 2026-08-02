package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val buildToolsGapTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "gradle-plugins",
        title = "Gradle Plugins",
        category = "Build Tools",
        recap = Recap(
            previousTopicTitle = "KDoc",
            recapText = "KDoc comments (/** ... */) document declarations with tags like @param, @return, @throws, @see, and @sample, using Markdown and [Identifier] links.",
            quickCheckQuestion = "How does KDoc link to another declaration, and how does that differ from Javadoc?",
            quickCheckAnswer = "[Identifier] square brackets — Javadoc uses {@link Identifier} instead.",
        ),
        explain = "A Gradle plugin extends what a build script can do — it adds new tasks, new configuration blocks (like plugins{}, android{}, or dependencies{} conventions), and wiring between them. Without plugins, a build.gradle.kts file would have to define everything from scratch; plugins package up reusable build logic so you just apply and configure it. You apply a plugin in the plugins { } block at the top of a build script, identifying it by an id and usually a version. For Kotlin projects, the most common plugins are kotlin(\"jvm\") for plain JVM projects, kotlin(\"android\") for Android projects, and kotlin(\"plugin.serialization\") to enable kotlinx.serialization's compiler support — these kotlin(...) helpers are shorthand for the full org.jetbrains.kotlin.* plugin ids. Plugins are published to and downloaded from the Gradle Plugin Portal (or a private plugin repository your organization configures), the same way library dependencies come from Maven Central.",
        example = """
            |// build.gradle.kts
            |plugins {
            |    kotlin("jvm") version "2.0.0"                    // Kotlin/JVM compiler support
            |    kotlin("plugin.serialization") version "2.0.0"   // enables @Serializable
            |    application                                       // built-in Gradle plugin, no version needed
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "A plugin extends a build with new tasks and configuration — it's how build logic gets reused.",
            "Plugins are applied in the plugins { } block, identified by an id and (usually) a version.",
            "kotlin(\"jvm\"), kotlin(\"android\"), and kotlin(\"plugin.serialization\") are the most common Kotlin plugins.",
            "Plugins are published to the Gradle Plugin Portal, similar to how libraries come from Maven Central.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "gradle-plugins-q1",
                topicId = "gradle-plugins",
                question = "What does applying a Gradle plugin do?",
                options = listOf(
                    "It only changes the IDE's syntax highlighting",
                    "It extends the build with new tasks and configuration options",
                    "It replaces the entire build.gradle.kts file",
                    "It only affects test execution, never compilation",
                ),
                correctIndex = 1,
                explanation = "Plugins add new tasks, configuration blocks, and wiring to a build — that's their core purpose.",
            ),
            QuizQuestion(
                id = "gradle-plugins-q2",
                topicId = "gradle-plugins",
                question = "Which plugin enables kotlinx.serialization's @Serializable support?",
                options = listOf(
                    "kotlin(\"jvm\")",
                    "kotlin(\"plugin.serialization\")",
                    "kotlin(\"android\")",
                    "application",
                ),
                correctIndex = 1,
                explanation = "kotlin(\"plugin.serialization\") enables the compiler plugin that generates serializer code for @Serializable classes.",
            ),
            QuizQuestion(
                id = "gradle-plugins-q3",
                topicId = "gradle-plugins",
                question = "Where do Gradle plugins come from?",
                options = listOf(
                    "They must be copied manually into the project's libs folder",
                    "The Gradle Plugin Portal (or a configured private repository)",
                    "They are bundled with the Kotlin compiler and need no source",
                    "Only from Maven Central, never anywhere else",
                ),
                correctIndex = 1,
                explanation = "Gradle plugins are typically resolved from the Gradle Plugin Portal, the plugin-focused counterpart to a library repository like Maven Central.",
            ),
        ),
        tutorFocus = "Tie this to the plugins { } block the learner already saw in the Build Tools topic — now explain what applying one of those lines actually does. Exercise: have the learner identify which plugins this app's own build.gradle.kts applies and what each one is for.",
    ),
    CurriculumTopic(
        id = "maven",
        title = "Maven",
        category = "Build Tools",
        recap = Recap(
            previousTopicTitle = "Gradle Plugins",
            recapText = "A Gradle plugin extends a build with new tasks/config, applied via plugins { id ... version ... }; kotlin(\"jvm\"), kotlin(\"android\"), and kotlin(\"plugin.serialization\") are common ones, sourced from the Gradle Plugin Portal.",
            quickCheckQuestion = "Which plugin enables kotlinx.serialization's @Serializable support?",
            quickCheckAnswer = "kotlin(\"plugin.serialization\").",
        ),
        explain = "Maven is the other major JVM build tool, older than Gradle and still widely used, especially in enterprise and legacy JVM shops. Where Gradle build scripts are code (Kotlin or Groovy DSL), Maven configuration is declarative XML in a pom.xml file — you describe what your project needs (dependencies, plugins, build steps) rather than scripting how to produce it. Kotlin support in Maven comes from the kotlin-maven-plugin, configured in the pom.xml's <build><plugins> section, which compiles .kt sources alongside or instead of Java. Dependencies in Maven are identified the same way as in Gradle — by coordinates in the form groupId:artifactId:version — since both tools pull from the same Maven Central repository ecosystem; only the syntax for declaring them differs. Teams sometimes choose Maven over Gradle for its convention-over-configuration style (less to decide, more standardized project layout) and because many existing enterprise JVM codebases and internal tooling are already built around it, making migration costly relative to the benefit.",
        example = """
            |<!-- pom.xml (excerpt) -->
            |<build>
            |    <plugins>
            |        <plugin>
            |            <groupId>org.jetbrains.kotlin</groupId>
            |            <artifactId>kotlin-maven-plugin</artifactId>
            |            <version>2.0.0</version>
            |        </plugin>
            |    </plugins>
            |</build>
            |
            |<dependencies>
            |    <dependency>
            |        <groupId>org.jetbrains.kotlinx</groupId>
            |        <artifactId>kotlinx-coroutines-core</artifactId>
            |        <version>1.8.1</version>
            |    </dependency>
            |</dependencies>
        """.trimMargin(),
        keyPoints = listOf(
            "Maven configuration is declarative XML (pom.xml), unlike Gradle's Kotlin/Groovy DSL.",
            "The kotlin-maven-plugin adds Kotlin compilation support to a Maven build.",
            "Dependency coordinates (groupId:artifactId:version) are the same concept in both tools — only the syntax differs.",
            "Maven's convention-over-flexibility style and existing enterprise adoption are common reasons teams stick with it.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "maven-q1",
                topicId = "maven",
                question = "How is a Maven build configured, in contrast to Gradle?",
                options = listOf(
                    "Declarative XML in pom.xml, vs. Gradle's Kotlin/Groovy DSL scripts",
                    "Maven uses YAML while Gradle uses XML",
                    "They use the exact same build.gradle.kts format",
                    "Maven has no configuration file — everything is CLI flags",
                ),
                correctIndex = 0,
                explanation = "Maven describes builds declaratively in pom.xml, while Gradle build scripts are executable Kotlin or Groovy code.",
            ),
            QuizQuestion(
                id = "maven-q2",
                topicId = "maven",
                question = "What adds Kotlin compilation support to a Maven build?",
                options = listOf("kotlin-maven-plugin", "kotlin(\"jvm\")", "kotlinc-maven", "org.jetbrains.kotlin.jvm"),
                correctIndex = 0,
                explanation = "The kotlin-maven-plugin, declared in pom.xml, is what enables compiling .kt sources under Maven.",
            ),
            QuizQuestion(
                id = "maven-q3",
                topicId = "maven",
                question = "What format do Maven dependency coordinates use?",
                options = listOf(
                    "groupId:artifactId:version",
                    "package.path.ClassName",
                    "name@version (like npm)",
                    "vendor/library-version.jar",
                ),
                correctIndex = 0,
                explanation = "Maven identifies a dependency by groupId:artifactId:version — the same coordinate concept Gradle uses, just declared in XML.",
            ),
        ),
        tutorFocus = "Contrast rather than duplicate the Gradle topic — same underlying concepts (dependencies, plugins, build lifecycle), different syntax and philosophy. Exercise: have the learner translate one of this app's build.gradle.kts dependency lines into the equivalent pom.xml <dependency> block.",
    ),
    CurriculumTopic(
        id = "build-tool-api",
        title = "Build Tool API",
        category = "Build Tools",
        recap = Recap(
            previousTopicTitle = "Maven",
            recapText = "Maven configures builds declaratively via pom.xml, uses the kotlin-maven-plugin for Kotlin support, and shares Gradle's groupId:artifactId:version dependency coordinates.",
            quickCheckQuestion = "What adds Kotlin compilation support to a Maven build?",
            quickCheckAnswer = "The kotlin-maven-plugin.",
        ),
        explain = "The Kotlin Build Tools API is a stable, tool-agnostic API for invoking the Kotlin compiler programmatically. Rather than every build tool or IDE talking directly to the compiler's internal, version-specific classes, the Build Tools API gives them a supported, versioned interface to compile Kotlin code, request incremental compilation, and read diagnostics. Gradle's Kotlin plugin and Maven's kotlin-maven-plugin use it internally, and IDE tooling can use it too. This matters because compiler internals change between Kotlin releases, but a stable API means a build tool integration doesn't have to be rewritten every time the compiler evolves — the API absorbs those changes, decoupling build tool and IDE integrations from compiler implementation details. As a learner you're unlikely to call this API directly (it's used by the plugins you already rely on, not by application code), but knowing it exists explains why upgrading your Kotlin version rarely breaks your Gradle or Maven build.",
        example = "",
        keyPoints = listOf(
            "The Build Tools API is a stable, versioned way to invoke the Kotlin compiler programmatically.",
            "Gradle's and Maven's Kotlin plugins use it under the hood — you don't call it directly in application code.",
            "It exists to decouple build tools and IDEs from the compiler's internal implementation details.",
            "This is why bumping your Kotlin version usually doesn't require reworking your build configuration.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "build-tool-api-q1",
                topicId = "build-tool-api",
                question = "What is the Kotlin Build Tools API for?",
                options = listOf(
                    "Writing Android UI layouts",
                    "Invoking the Kotlin compiler programmatically in a stable, tool-agnostic way",
                    "Managing app runtime permissions",
                    "Serializing objects to JSON",
                ),
                correctIndex = 1,
                explanation = "It's a stable API that build tools and IDEs use to drive the Kotlin compiler without depending on its internals.",
            ),
            QuizQuestion(
                id = "build-tool-api-q2",
                topicId = "build-tool-api",
                question = "Who typically uses the Build Tools API directly?",
                options = listOf(
                    "Application code, in every Kotlin project",
                    "Build tool plugins (like Gradle's and Maven's Kotlin plugins) and IDE tooling",
                    "Only end users running kotlinc from the terminal",
                    "It's unused — a purely theoretical API",
                ),
                correctIndex = 1,
                explanation = "The API is consumed internally by build tool integrations and IDEs, not by typical application code.",
            ),
            QuizQuestion(
                id = "build-tool-api-q3",
                topicId = "build-tool-api",
                question = "Why does the Build Tools API exist?",
                options = listOf(
                    "To make Kotlin compile faster than Java",
                    "To decouple build tools/IDEs from compiler internals, so compiler updates don't break every integration",
                    "To let you skip the Kotlin compiler entirely",
                    "To replace Gradle and Maven with a single unified tool",
                ),
                correctIndex = 1,
                explanation = "By giving build tools a stable interface instead of direct access to compiler internals, compiler changes are far less likely to break existing integrations.",
            ),
        ),
        tutorFocus = "This is a niche, advanced topic — keep it conceptual and brief, don't dive into API specifics the learner won't use directly. Exercise: none needed beyond a quick recap question; this is background knowledge, not a hands-on skill.",
    ),
    CurriculumTopic(
        id = "teamcity",
        title = "CI/CD: TeamCity",
        category = "Build Tools",
        recap = Recap(
            previousTopicTitle = "Build Tool API",
            recapText = "The Kotlin Build Tools API is a stable, tool-agnostic API for invoking the compiler programmatically, used internally by Gradle/Maven plugins and IDEs so compiler updates don't break every integration.",
            quickCheckQuestion = "Why does the Build Tools API exist?",
            quickCheckAnswer = "To decouple build tools/IDEs from compiler internals, so compiler updates don't break every integration.",
        ),
        explain = "TeamCity is JetBrains' CI/CD server — a natural fit for Kotlin and JVM projects given the shared vendor, though it works with any language or stack. Like any CI/CD system, a TeamCity pipeline for a Kotlin project generally follows the same shape: check out the source, build it (invoking Gradle or Maven), run the test suite, and then package or deploy the result if everything passes. What sets TeamCity apart for Kotlin developers specifically is its Kotlin DSL support for configuration-as-code — instead of only configuring build steps through TeamCity's UI, you can define pipelines as Kotlin files under a .teamcity/ directory in your repository, versioned right alongside your source. That said, TeamCity is just the JetBrains-native option, not a requirement — plenty of Kotlin projects run their pipelines on GitHub Actions, GitLab CI, or other CI systems just as well; the underlying checkout-build-test-deploy pipeline shape is the same regardless of which CI system you pick.",
        example = """
            |// .teamcity/settings.kts (excerpt) — TeamCity's Kotlin DSL for config-as-code
            |project {
            |    buildType {
            |        id("Build_And_Test")
            |        name = "Build and Test"
            |
            |        steps {
            |            gradle {
            |                tasks = "clean build test"
            |            }
            |        }
            |    }
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "TeamCity is JetBrains' CI/CD server; it pairs naturally with Kotlin/JVM projects.",
            "A typical CI pipeline: checkout, build (via Gradle/Maven), test, then package/deploy.",
            "TeamCity supports a Kotlin DSL for config-as-code, defined in a .teamcity/ directory in the repo.",
            "TeamCity isn't mandatory — GitHub Actions, GitLab CI, and other CI systems work fine for Kotlin projects too.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "teamcity-q1",
                topicId = "teamcity",
                question = "What is TeamCity?",
                options = listOf(
                    "A Kotlin testing framework",
                    "JetBrains' CI/CD server",
                    "A Gradle plugin for dependency management",
                    "An Android emulator",
                ),
                correctIndex = 1,
                explanation = "TeamCity is a CI/CD server built by JetBrains, the same company behind Kotlin and IntelliJ IDEA.",
            ),
            QuizQuestion(
                id = "teamcity-q2",
                topicId = "teamcity",
                question = "What does TeamCity's Kotlin DSL support let you do?",
                options = listOf(
                    "Write Android UI in Kotlin instead of XML",
                    "Define CI pipeline configuration as versioned Kotlin files under .teamcity/",
                    "Compile Kotlin faster than kotlinc",
                    "Replace pom.xml with Kotlin syntax",
                ),
                correctIndex = 1,
                explanation = "TeamCity lets you write build configuration as Kotlin code in a .teamcity/ directory, keeping pipeline config in version control alongside the source.",
            ),
            QuizQuestion(
                id = "teamcity-q3",
                topicId = "teamcity",
                question = "Is TeamCity required to build and test a Kotlin project in CI?",
                options = listOf(
                    "Yes, it's the only CI system that supports Kotlin",
                    "No — GitHub Actions, GitLab CI, and other systems work fine; TeamCity is just the JetBrains-native option",
                    "Yes, Gradle will not run outside of TeamCity",
                    "No, but only if the project avoids Gradle entirely",
                ),
                correctIndex = 1,
                explanation = "TeamCity is one option among several — any CI system that can run Gradle or Maven commands can build and test a Kotlin project.",
            ),
        ),
        tutorFocus = "Keep the pipeline shape (checkout → build → test → deploy) as the main takeaway; TeamCity specifics are secondary since many teams use other CI systems. Exercise: have the learner sketch, in plain steps, what a CI pipeline for this app would look like (checkout, ./gradlew build, run tests, assemble the APK).",
    ),
)
