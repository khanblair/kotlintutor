package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val kotlinApplicationsTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "ktor",
        title = "Ktor",
        category = "Kotlin Applications",
        recap = Recap(
            previousTopicTitle = "Android Jetpack",
            recapText = "Android Jetpack is Google's suite of androidx libraries — Compose, Room, Navigation-Compose, and ViewModel — that this app itself is built on, aimed at reducing boilerplate and encouraging a consistent architecture.",
            quickCheckQuestion = "What is Jetpack's overall goal, as described by Google?",
            quickCheckAnswer = "Reduce boilerplate for common app tasks and encourage a consistent, modern architecture.",
        ),
        explain = "Ktor is JetBrains' asynchronous framework for building both servers and HTTP clients in Kotlin, built directly on coroutines for its I/O rather than threads or callbacks — a request suspends instead of blocking a thread. Its architecture is plugin-based: instead of scanning annotations at startup, you call install(SomePlugin) on either an HttpClient or an Application to opt into a piece of functionality, such as ContentNegotiation (automatic JSON/XML serialization of request and response bodies) or Logging (request/response logging). The Ktor client is also engine-agnostic — the same client API can run on top of different underlying HTTP engines (CIO, OkHttp, Android, and others), chosen when the HttpClient is constructed. This app uses exactly this: its data/tutor/HttpClientFactory.kt builds an HttpClient(OkHttp) with the ContentNegotiation plugin installed for JSON handling, and data/tutor/DeepSeekApi.kt uses that client to POST chat messages to the DeepSeek API and parse the JSON response — this app is a Ktor client consumer, not a Ktor server.",
        example = """
            |val client = HttpClient(OkHttp) {
            |    install(ContentNegotiation) {
            |        json()
            |    }
            |}
            |
            |suspend fun fetchJoke(): String {
            |    val response: HttpResponse = client.get("https://api.example.com/joke")
            |    return response.bodyAsText()
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Ktor is JetBrains' Kotlin framework for both servers and HTTP clients, built on coroutines for non-blocking I/O.",
            "Its architecture is plugin-based — you install() plugins like ContentNegotiation or Logging rather than relying on annotations.",
            "Ktor's HttpClient is engine-agnostic (CIO, OkHttp, Android, etc.); this app picks the OkHttp engine.",
            "This app's data/tutor/HttpClientFactory.kt and DeepSeekApi.kt use Ktor Client with ContentNegotiation to call the DeepSeek chat completions API.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "ktor-q1",
                topicId = "ktor",
                question = "What is Ktor?",
                options = listOf(
                    "A Kotlin Multiplatform UI toolkit",
                    "An asynchronous Kotlin framework, built on coroutines, for building both servers and HTTP clients",
                    "A dependency injection framework for Android",
                    "A JVM-only web framework that cannot make outbound HTTP calls",
                ),
                correctIndex = 1,
                explanation = "Ktor supports both building HTTP servers and making HTTP client calls, with coroutines powering its non-blocking I/O.",
            ),
            QuizQuestion(
                id = "ktor-q2",
                topicId = "ktor",
                question = "How does Ktor let you add functionality like JSON handling to an HttpClient?",
                options = listOf(
                    "Via annotations like @EnableJson",
                    "By calling install() with a plugin such as ContentNegotiation",
                    "By subclassing HttpClient",
                    "It's built in and cannot be customized",
                ),
                correctIndex = 1,
                explanation = "Ktor's plugin system uses install(SomePlugin) to opt into functionality like content negotiation, logging, or authentication.",
            ),
            QuizQuestion(
                id = "ktor-q3",
                topicId = "ktor",
                question = "Which engine does this app's Ktor HttpClient use, and for what?",
                options = listOf(
                    "The OkHttp engine, to call the DeepSeek chat completions API",
                    "The CIO engine, to query the Room database directly",
                    "The Android engine, to render Compose UI",
                    "The Js engine, to render the roadmap screen",
                ),
                correctIndex = 0,
                explanation = "HttpClientFactory.kt constructs HttpClient(OkHttp) with ContentNegotiation installed, and DeepSeekApi.kt uses it to call the DeepSeek API.",
            ),
        ),
        tutorFocus = "Anchor this in the app's own data/tutor/HttpClientFactory.kt and DeepSeekApi.kt — real code showing HttpClient(OkHttp) with ContentNegotiation installed, making an authenticated POST call. Be clear this app only uses Ktor's client side, not its server side.",
    ),
    CurriculumTopic(
        id = "spring",
        title = "Spring",
        category = "Kotlin Applications",
        recap = Recap(
            previousTopicTitle = "Ktor",
            recapText = "Ktor is JetBrains' coroutine-based framework for both servers and HTTP clients, built around installing plugins rather than annotations; this app uses Ktor Client with the OkHttp engine to call the DeepSeek API.",
            quickCheckQuestion = "How does Ktor let you add functionality like JSON handling to an HttpClient?",
            quickCheckAnswer = "By calling install() with a plugin such as ContentNegotiation.",
        ),
        explain = "Spring, and Spring Boot specifically, has had first-class, officially supported Kotlin support since Spring Framework 5 (released 2017) — not a community add-on, but a target the Spring team designs and tests for directly. This shows up in several Kotlin-specific niceties: Spring's core modules integrate with kotlin-stdlib so that Kotlin's null-safety is respected end to end, meaning request and response DTOs can be plain Kotlin data classes with non-nullable fields instead of Java-style classes relying on Optional<T> or nullable getters. Spring also offers a functional routing DSL — router { } for standard MVC, coRouter { } for the coroutine/WebFlux variant — as an alternative to the traditional @RestController plus @GetMapping annotation style, letting routes be declared as a single block of Kotlin code rather than scattered across annotated methods. Given Spring's long-standing dominance in enterprise Java, many JVM shops that adopt Kotlin do so by keeping Spring and simply writing new Spring Boot services in Kotlin instead of Java, rather than switching frameworks entirely. That is the core tradeoff versus Ktor: Ktor is lighter-weight and was designed coroutine-first from day one, while Spring brings a much larger ecosystem of integrations (Spring Data, Spring Security, Spring Cloud) and existing team expertise that many organizations are reluctant to give up.",
        example = """
            |// Traditional annotation style
            |@RestController
            |class GreetingController {
            |    @GetMapping("/hello")
            |    fun hello(): String = "Hello, world"
            |}
            |
            |// Kotlin-idiomatic routing DSL alternative
            |val routes = router {
            |    GET("/hello") {
            |        ServerResponse.ok().bodyValue("Hello, world")
            |    }
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Spring has had first-class, officially supported Kotlin support since Spring Framework 5 (2017), not just community support.",
            "Kotlin's non-null-by-default types pair naturally with Spring's request/response DTOs, avoiding Java's Optional<T>/nullable-getter patterns.",
            "Spring offers a functional routing DSL (router { } / coRouter { } for coroutines) as an alternative to @RestController-style annotations.",
            "Many enterprise JVM teams choose Spring+Kotlin over Ktor for its larger ecosystem (Spring Data, Spring Security, Spring Cloud) and existing team Spring experience.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "spring-q1",
                topicId = "spring",
                question = "Since which Spring Framework version has Kotlin been officially, first-class supported?",
                options = listOf("Spring 3", "Spring 4", "Spring 5", "Spring 6"),
                correctIndex = 2,
                explanation = "Spring Framework 5, released in 2017, introduced official, first-class Kotlin support.",
            ),
            QuizQuestion(
                id = "spring-q2",
                topicId = "spring",
                question = "Why do Kotlin data classes pair especially well with Spring's DTOs?",
                options = listOf(
                    "Spring requires all DTOs to be written in Java",
                    "Kotlin's non-null-by-default types align with typical DTO fields, avoiding Java's Optional<T>/nullable-getter patterns",
                    "Data classes are the only class type Spring can serialize",
                    "Spring converts all Kotlin classes into Java records automatically",
                ),
                correctIndex = 1,
                explanation = "Kotlin's null-safety lets DTO fields be declared non-nullable directly, matching how Spring's Kotlin integration is designed to work.",
            ),
            QuizQuestion(
                id = "spring-q3",
                topicId = "spring",
                question = "What is the main reason many enterprise JVM teams pick Spring+Kotlin instead of switching to Ktor?",
                options = listOf(
                    "Spring's larger existing ecosystem (Spring Data, Security, Cloud) and teams' existing Spring experience",
                    "Ktor cannot run on the JVM",
                    "Kotlin is incompatible with Ktor",
                    "Spring compiles faster than Ktor",
                ),
                correctIndex = 0,
                explanation = "The tradeoff is ecosystem size and familiarity versus Ktor's lighter weight and coroutine-first design — not a technical incompatibility.",
            ),
        ),
        tutorFocus = "Contrast directly with Ktor from the previous topic — same JVM target, different philosophy (annotation/DI-heavy full framework vs. a lightweight, coroutine-first library). Exercise: have the learner compare an @RestController + @GetMapping snippet to a router { } DSL block and explain which reads as more Kotlin-idiomatic and why.",
    ),
    CurriculumTopic(
        id = "quarkus",
        title = "Quarkus",
        category = "Kotlin Applications",
        recap = Recap(
            previousTopicTitle = "Spring",
            recapText = "Spring has had official, first-class Kotlin support since Spring 5, with non-null DTOs pairing naturally with data classes and a router { } DSL as an alternative to @RestController; many enterprise teams stick with Spring+Kotlin for its larger ecosystem.",
            quickCheckQuestion = "What is the main reason many enterprise JVM teams pick Spring+Kotlin instead of switching to Ktor?",
            quickCheckAnswer = "Spring's larger existing ecosystem (Spring Data, Security, Cloud) and teams' existing Spring experience.",
        ),
        explain = "Quarkus is a Kubernetes-native Java and Kotlin framework, purpose-built for fast startup time and low memory footprint, in contrast to traditional JVM frameworks whose startup can take multiple seconds. It gets there largely by doing as much work as possible at build time instead of at runtime — a process Quarkus calls build-time augmentation, where dependency-injection wiring, configuration processing, and reflection metadata are resolved while the app is being built rather than computed fresh every time it starts. Quarkus treats GraalVM native-image compilation as a first-class target: compiling the app ahead-of-time into a standalone native executable, instead of running it on a JVM with a warming-up JIT compiler, yields startup times often measured in milliseconds and substantially lower memory use. Those properties matter most for cloud-native and serverless workloads — a Kubernetes deployment autoscaling under load, or a function-as-a-service platform starting a fresh instance per request — where slow JVM startup translates directly into worse latency and higher infrastructure cost. Quarkus also supports running on a regular JVM during development for fast iteration, then compiling to a native executable for production, giving teams a develop-as-JVM, deploy-as-native workflow. Compared with Spring, the tradeoff runs the other way from the Spring-vs-Ktor comparison: Spring has the larger, more mature ecosystem, but Quarkus specifically targets the startup-time and memory constraints that Spring's runtime-reflection-heavy startup model was never designed to optimize for.",
        example = """
            |@Path("/hello")
            |class GreetingResource {
            |    @GET
            |    fun hello(): String = "Hello from Quarkus"
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Quarkus is a Kubernetes-native framework (Java and Kotlin) optimized for fast startup and low memory use.",
            "It performs build-time augmentation — resolving DI wiring, configuration, and reflection metadata at build time instead of at startup.",
            "It treats GraalVM native-image compilation as a first-class target, producing native executables with millisecond startup times.",
            "It targets cloud-native and serverless workloads where a traditional JVM framework's startup time is a liability.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "quarkus-q1",
                topicId = "quarkus",
                question = "What problem is Quarkus specifically optimized to solve, compared to a framework like Spring?",
                options = listOf(
                    "A larger dependency ecosystem",
                    "Better annotation-based configuration",
                    "A simpler routing DSL syntax",
                    "Fast startup time and low memory footprint",
                ),
                correctIndex = 3,
                explanation = "Quarkus's design goal is minimizing startup time and memory use, particularly for cloud-native and serverless deployments.",
            ),
            QuizQuestion(
                id = "quarkus-q2",
                topicId = "quarkus",
                question = "How does Quarkus achieve much of its startup-time advantage?",
                options = listOf(
                    "By skipping dependency injection entirely",
                    "By performing build-time augmentation — resolving DI wiring, configuration, and reflection metadata at build time rather than at startup",
                    "By caching HTTP responses in memory",
                    "By running only inside Docker containers",
                ),
                correctIndex = 1,
                explanation = "Build-time augmentation moves work that traditional frameworks do at startup (like scanning for annotations) into the build step instead.",
            ),
            QuizQuestion(
                id = "quarkus-q3",
                topicId = "quarkus",
                question = "What compilation target does Quarkus treat as first-class, enabling millisecond startup times?",
                options = listOf(
                    "GraalVM native-image",
                    "WebAssembly",
                    "Kotlin/Native",
                    "Android's ART runtime",
                ),
                correctIndex = 0,
                explanation = "Quarkus is built around compiling to a native executable via GraalVM native-image, avoiding JVM warm-up time entirely.",
            ),
        ),
        tutorFocus = "Emphasize the 'why' — cloud-native workloads where JVM cold-start time costs real latency and money (Kubernetes autoscaling, serverless functions). Contrast build-time augmentation against Spring's more runtime-reflection-heavy startup model from the previous topic.",
    ),
    CurriculumTopic(
        id = "vertx",
        title = "Vert.x",
        category = "Kotlin Applications",
        recap = Recap(
            previousTopicTitle = "Quarkus",
            recapText = "Quarkus is a Kubernetes-native Java/Kotlin framework that pushes dependency-injection and configuration work to build time and targets GraalVM native-image compilation, aiming for millisecond startup and low memory use in cloud-native and serverless workloads.",
            quickCheckQuestion = "What compilation target does Quarkus treat as first-class, enabling millisecond startup times?",
            quickCheckAnswer = "GraalVM native-image.",
        ),
        explain = "Eclipse Vert.x is a toolkit, not a full framework, for building reactive, event-driven applications on the JVM. The toolkit framing matters: Vert.x doesn't impose a rigid application structure or a mandated project layout the way a full framework does — you pull in only the modules you need (an HTTP server, a Kafka client, a Redis client, and so on) and wire them together yourself. Its core concurrency model is built around verticles: deployable units of code, each running on an event loop, that communicate with each other through an in-memory (or clustered) event bus rather than through shared mutable state. Vert.x's core APIs are non-blocking and, historically, callback- or Future-based — an operation like an HTTP request takes a completion handler or returns a Future instead of blocking the calling thread, which is what lets a small number of event-loop threads serve a very large number of concurrent connections. Because Vert.x predates Kotlin coroutines, Kotlin support is layered on top of that existing callback/Future-based core through the vertx-lang-kotlin-coroutines module, which adds suspending extension functions (such as await()) so Kotlin code can call Vert.x's asynchronous APIs with straight-line coroutine code instead of nested callbacks. Contrasted with Ktor: both are lightweight, async-first JVM libraries well suited to high-concurrency I/O, but Ktor was designed coroutine-first from the start, whereas Vert.x's coroutine support is a Kotlin-specific bridge layered onto an older, callback/Future-oriented core engine.",
        example = "",
        keyPoints = listOf(
            "Vert.x is a toolkit, not a framework — you pull in only the modules you need instead of a mandated project structure.",
            "Its concurrency model is built around verticles: units of code on an event loop, communicating via an event bus rather than shared state.",
            "Vert.x's core APIs are non-blocking and historically callback/Future-based, predating Kotlin coroutines.",
            "The vertx-lang-kotlin-coroutines module layers suspending functions (like await()) on top of that core so Kotlin code can use coroutines instead of callbacks.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "vertx-q1",
                topicId = "vertx",
                question = "What best describes Vert.x, as distinct from a full framework?",
                options = listOf(
                    "A toolkit — you pull in only the modules you need instead of a rigid, imposed structure",
                    "A strict MVC framework requiring a fixed project layout",
                    "A build tool that compiles Kotlin to native binaries",
                    "A dependency injection container only",
                ),
                correctIndex = 0,
                explanation = "Vert.x is deliberately described as a toolkit — it doesn't dictate application structure the way a full framework does.",
            ),
            QuizQuestion(
                id = "vertx-q2",
                topicId = "vertx",
                question = "What is a Vert.x verticle?",
                options = listOf(
                    "A database migration script",
                    "A deployable unit of code that runs on an event loop and communicates via the event bus",
                    "A Gradle build variant",
                    "A GraalVM-specific compilation target",
                ),
                correctIndex = 1,
                explanation = "Verticles are Vert.x's core unit of deployment and concurrency, each running on an event loop and communicating over the event bus.",
            ),
            QuizQuestion(
                id = "vertx-q3",
                topicId = "vertx",
                question = "How does Kotlin coroutine support work in Vert.x, given that Vert.x predates coroutines?",
                options = listOf(
                    "Vert.x's core was fully rewritten around coroutines",
                    "Coroutines aren't supported in Vert.x at all",
                    "The vertx-lang-kotlin-coroutines module layers suspending functions (like await()) on top of Vert.x's existing callback/Future-based core",
                    "Vert.x only supports coroutines when compiled with GraalVM native-image",
                ),
                correctIndex = 2,
                explanation = "Coroutine support in Vert.x is an additional Kotlin-specific module bridging into the original callback/Future-based APIs, not a rewrite of the core.",
            ),
        ),
        tutorFocus = "Make sure the learner doesn't conflate 'toolkit' with 'framework' — Vert.x deliberately avoids imposing structure. Contrast its callback/Future-based core (older than Kotlin coroutines) with Ktor's coroutine-first design, and mention vertx-lang-kotlin-coroutines as the bridge layer that makes coroutine usage possible.",
    ),
)
