package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val aiDevelopmentTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "koog",
        title = "Koog",
        category = "AI Development",
        recap = Recap(
            previousTopicTitle = "Competitive Programming",
            recapText = "Kotlin's concise syntax and rich stdlib suit competitive programming, but real contest code demands fast I/O too — BufferedReader for input, a single StringBuilder flush for output — since default println/readLine() are too slow at scale.",
            quickCheckQuestion = "Why do competitive Kotlin submissions typically avoid one println() call per output line?",
            quickCheckAnswer = "Each call flushes individually, which is much slower than building output in a StringBuilder and printing once.",
        ),
        explain = "Koog is JetBrains's Kotlin framework for building AI agents — software that plans, calls tools, and reasons over multiple steps using a large language model, rather than making a single one-shot prompt/response call. Where a framework like LangChain is Python-first and gets used from Kotlin only through interop or a subprocess, Koog is built for the Kotlin/JVM ecosystem from the ground up: it exposes a type-safe, coroutine-based DSL for describing an agent's behavior, so defining what tools an agent can call, how it chains reasoning steps, and how it handles a model's response reads like ordinary structured Kotlin code — checked by the compiler — instead of untyped dictionaries and free-form prompt strings. Because it's coroutine-based, calling an LLM API, waiting on its response, and orchestrating multiple tool calls all compose the same way any other suspending Kotlin code does, fitting naturally into JVM and Android codebases that already use coroutines elsewhere. For a team already invested in Kotlin, Koog offers a way to add structured, multi-step LLM features without adopting a second, Python-first stack alongside it. There's a fitting parallel close to home: this very app's AI Tutor feature is a hand-rolled example of exactly the kind of thing Koog exists to make more structured and reusable. TutorRepository and KtorDeepSeekApi call the DeepSeek chat completions endpoint directly over Ktor's HttpClient, manually build the list of chat messages (system prompt plus history plus the new user message), and manually parse the JSON response into a typed result. That's a perfectly reasonable way to wire up one LLM call for one feature — but it's also exactly the kind of boilerplate (message-list bookkeeping, response parsing, no built-in tool-calling or multi-step reasoning support) that a framework like Koog is designed to take off a Kotlin developer's hands, especially once an app needs an LLM to call tools or reason across multiple steps rather than answer a single prompt.",
        example = "",
        keyPoints = listOf(
            "Koog is JetBrains's Kotlin framework for building LLM-powered agents — multi-step, tool-calling AI workflows — natively in Kotlin rather than through a Python-first framework like LangChain.",
            "It offers a type-safe, coroutine-based DSL for defining agent behavior, tool calling, and reasoning chains, checked by the compiler instead of built from untyped prompt strings.",
            "Being coroutine-based lets LLM calls and multi-step orchestration compose naturally with the suspend functions already used elsewhere in JVM/Android code.",
            "This app's own AI Tutor feature (TutorRepository/KtorDeepSeekApi calling DeepSeek over Ktor) is a hand-rolled example of the kind of LLM integration Koog aims to make structured and reusable.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "koog-q1",
                topicId = "koog",
                question = "What is Koog?",
                options = listOf(
                    "JetBrains's Kotlin framework for building LLM-powered, tool-calling AI agents natively in Kotlin",
                    "A Python library for building neural networks",
                    "A build tool replacing Gradle",
                    "A Kotlin charting library like Kandy",
                ),
                correctIndex = 0,
                explanation = "Koog is JetBrains's framework for defining LLM-powered agent behavior, tool calling, and multi-step reasoning idiomatically in Kotlin.",
            ),
            QuizQuestion(
                id = "koog-q2",
                topicId = "koog",
                question = "How does Koog's coroutine-based DSL benefit a Kotlin/JVM or Android codebase?",
                options = listOf(
                    "It requires abandoning coroutines entirely in favor of callbacks",
                    "It lets LLM calls and multi-step tool orchestration compose the same way as other suspending Kotlin code already in the app",
                    "It only works with Java, not Kotlin",
                    "It removes the need for any network calls",
                ),
                correctIndex = 1,
                explanation = "Because Koog is built on coroutines, agent behavior involving LLM calls and tool orchestration composes naturally alongside any other suspend-function code in a JVM or Android app.",
            ),
            QuizQuestion(
                id = "koog-q3",
                topicId = "koog",
                question = "How does this app's existing AI Tutor feature relate to what Koog is designed for?",
                options = listOf(
                    "They are unrelated — the AI Tutor uses a completely different technology stack",
                    "The AI Tutor's hand-rolled DeepSeek integration over Ktor is exactly the kind of LLM feature Koog aims to make more structured and reusable",
                    "Koog replaces Jetpack Compose in this app",
                    "The AI Tutor already uses Koog internally",
                ),
                correctIndex = 1,
                explanation = "TutorRepository and KtorDeepSeekApi manually build message lists and parse responses over Ktor to call DeepSeek — exactly the kind of hand-rolled LLM plumbing a framework like Koog is designed to make structured and reusable.",
            ),
        ),
        tutorFocus = "This is the capstone topic of the whole curriculum — treat it as a forward-looking wrap-up. Ground it concretely in this app's own TutorRepository/KtorDeepSeekApi (manual message-list building, manual JSON parsing, no tool-calling) as the 'before' picture, and Koog's type-safe agent DSL as the 'after.' Exercise: ask the learner to name one piece of boilerplate in KtorDeepSeekApi.sendMessage that a framework like Koog is designed to abstract away.",
    ),
)
