package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val coroutinesTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "suspending-functions",
        title = "Suspending Functions",
        category = "Coroutines & Async",
        recap = Recap(
            previousTopicTitle = "Nullability Operators",
            recapText = "?. safe call, ?: Elvis fallback, !! assert (avoid), as? safe cast.",
            quickCheckQuestion = "What does a?.b do when a is null? What does ?: give?",
            quickCheckAnswer = "returns null; a fallback value.",
        ),
        explain = "A suspending function (marked suspend) can pause its execution and resume later without blocking the underlying thread. While it's suspended (e.g. waiting on network I/O), the thread is free to do other work. Suspending functions can only be called from another suspending function or from a coroutine. This is what makes coroutines lightweight — you can run thousands concurrently on a few threads.",
        example = """
            |import kotlinx.coroutines.*
            |
            |suspend fun fetchUser(): String {
            |    delay(100)               // suspends without blocking the thread
            |    return "Ada"
            |}
            |
            |fun main() = runBlocking {    // bridges regular code into a coroutine
            |    val user = fetchUser()   // call a suspend function
            |    println("Loaded ${'$'}user")
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "suspend marks a function that may pause; it doesn't itself start concurrency.",
            "delay suspends; Thread.sleep blocks — don't confuse them.",
            "A suspend function needs a coroutine context to run (e.g. runBlocking, launch, async).",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "suspending-functions-q1",
                topicId = "suspending-functions",
                question = "What does suspend allow a function to do?",
                options = listOf(
                    "Run on a background thread automatically",
                    "Pause and resume without blocking its thread",
                    "Retry automatically on failure",
                    "Skip null checks",
                ),
                correctIndex = 1,
                explanation = "suspend functions can pause execution at suspension points without blocking the underlying thread.",
            ),
            QuizQuestion(
                id = "suspending-functions-q2",
                topicId = "suspending-functions",
                question = "What's the difference between delay and Thread.sleep?",
                options = listOf(
                    "No difference",
                    "delay suspends the coroutine (frees the thread); Thread.sleep blocks the thread",
                    "Thread.sleep is faster",
                    "delay only works inside Activities",
                ),
                correctIndex = 1,
                explanation = "delay is a suspending function that frees the thread; Thread.sleep blocks it entirely.",
            ),
            QuizQuestion(
                id = "suspending-functions-q3",
                topicId = "suspending-functions",
                question = "Where can you call a suspending function?",
                options = listOf(
                    "From any regular function",
                    "From another suspend function or a coroutine",
                    "Only from main()",
                    "Only from a Thread subclass",
                ),
                correctIndex = 1,
                explanation = "A suspend function requires a coroutine context — another suspend function or a coroutine builder.",
            ),
        ),
        tutorFocus = "Nail \"suspend ≠ blocking.\" Contrast delay vs Thread.sleep explicitly. Exercise: write a suspend function that \"loads\" data with delay and call it from runBlocking.",
    ),
    CurriculumTopic(
        id = "coroutine-builders",
        title = "Coroutine Builders",
        category = "Coroutines & Async",
        recap = Recap(
            previousTopicTitle = "Suspending Functions",
            recapText = "suspend pauses/resumes without blocking the thread; delay suspends, Thread.sleep blocks.",
            quickCheckQuestion = "delay vs Thread.sleep? Where can you call suspend funs?",
            quickCheckAnswer = "delay suspends / sleep blocks; from a coroutine or suspend fun.",
        ),
        explain = "Builders start coroutines:\n\n" +
            "- launch — fire-and-forget; returns a Job you can cancel/join. Use for work whose result you don't need directly.\n" +
            "- async — returns a Deferred<T>; call .await() to get the result. Use for concurrent work you'll combine.\n" +
            "- runBlocking — bridges the non-suspending world into coroutines by blocking the current thread until done (mainly for main and tests).\n\n" +
            "Builders run inside a CoroutineScope.",
        example = """
            |import kotlinx.coroutines.*
            |
            |suspend fun load(id: Int): Int { delay(100); return id * 10 }
            |
            |fun main() = runBlocking {
            |    launch { println("fire-and-forget") }          // Job
            |
            |    val a = async { load(1) }                       // starts concurrently
            |    val b = async { load(2) }
            |    println("sum = ${'$'}{a.await() + b.await()}")       // waits for both → 30
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Use async only when you need the result; otherwise launch.",
            "Two async blocks run concurrently; awaiting them combines results.",
            "Avoid runBlocking in production app code (it blocks) — it's for entry points and tests.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "coroutine-builders-q1",
                topicId = "coroutine-builders",
                question = "What's the difference between launch and async?",
                options = listOf(
                    "No real difference",
                    "launch is fire-and-forget (returns Job); async returns a Deferred result",
                    "async is fire-and-forget; launch returns a result",
                    "launch can only be used in tests",
                ),
                correctIndex = 1,
                explanation = "launch returns a Job for fire-and-forget work; async returns a Deferred you can await for a result.",
            ),
            QuizQuestion(
                id = "coroutine-builders-q2",
                topicId = "coroutine-builders",
                question = "How do you get the result from an async call?",
                options = listOf("Call .join()", "Call .await()", "Call .get()", "Access .result directly"),
                correctIndex = 1,
                explanation = ".await() suspends until the Deferred's result is ready and returns it.",
            ),
            QuizQuestion(
                id = "coroutine-builders-q3",
                topicId = "coroutine-builders",
                question = "When is runBlocking appropriate?",
                options = listOf(
                    "Anywhere you want to start a coroutine",
                    "At entry points like main or in tests",
                    "Inside every ViewModel function",
                    "Only inside launch blocks",
                ),
                correctIndex = 1,
                explanation = "runBlocking blocks its thread, so it's reserved for bridging points like main() or test bodies, not app code.",
            ),
        ),
        tutorFocus = "The launch vs async choice (need a result?) is the key decision. Show two async blocks running concurrently. Exercise: fetch two values concurrently with async and combine them.",
    ),
    CurriculumTopic(
        id = "asynchronous-flow",
        title = "Asynchronous Flow",
        category = "Coroutines & Async",
        recap = Recap(
            previousTopicTitle = "Coroutine Builders",
            recapText = "launch = fire-and-forget Job; async = Deferred result via await; runBlocking bridges at entry points.",
            quickCheckQuestion = "launch vs async? How do you get an async result?",
            quickCheckAnswer = "fire-and-forget vs a result; .await().",
        ),
        explain = "A Flow<T> is a cold asynchronous stream that emits multiple values over time (whereas a suspend function returns a single value). \"Cold\" means the code inside a flow doesn't run until it's collected. Flows use suspending operators (map, filter, collect) and integrate with structured concurrency. They're the idiomatic way to model streams like search results, sensor readings, or database updates.",
        example = """
            |import kotlinx.coroutines.*
            |import kotlinx.coroutines.flow.*
            |
            |fun counts(): Flow<Int> = flow {
            |    for (i in 1..3) {
            |        delay(100)
            |        emit(i)              // emit a value
            |    }
            |}
            |
            |fun main() = runBlocking {
            |    counts()
            |        .map { it * 10 }
            |        .collect { println(it) }   // triggers the flow → 10, 20, 30
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Flows are cold: nothing runs until collect (a terminal operator).",
            "Use a Flow for many values over time; a suspend fun for one.",
            "Collection happens in a coroutine and respects cancellation.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "asynchronous-flow-q1",
                topicId = "asynchronous-flow",
                question = "How does a Flow differ from a suspend function?",
                options = listOf(
                    "A Flow emits multiple values over time; a suspend function returns one",
                    "A Flow always runs on a background thread, a suspend function never does",
                    "Flows can't be cancelled, suspend functions can",
                    "There's no meaningful difference",
                ),
                correctIndex = 0,
                explanation = "Flow models a stream of many values over time; a suspend function returns exactly one result.",
            ),
            QuizQuestion(
                id = "asynchronous-flow-q2",
                topicId = "asynchronous-flow",
                question = "What does \"cold\" mean for a flow?",
                options = listOf(
                    "It can only emit null values",
                    "It doesn't run until collected",
                    "It runs once at app startup",
                    "It caches its emissions forever",
                ),
                correctIndex = 1,
                explanation = "A cold flow's builder code doesn't execute until a collector starts collecting.",
            ),
            QuizQuestion(
                id = "asynchronous-flow-q3",
                topicId = "asynchronous-flow",
                question = "What starts a flow producing values?",
                options = listOf(
                    "Declaring it with the flow { } builder",
                    "A terminal operator like collect",
                    "Calling .map on it",
                    "Nothing — it starts immediately on creation",
                ),
                correctIndex = 1,
                explanation = "A terminal operator such as collect is what actually starts pulling values through the flow.",
            ),
        ),
        tutorFocus = "Contrast \"one value (suspend) vs a stream (Flow)\" and the cold nature. Exercise: write a flow that emits three values with delay, transform with map, and collect them.",
    ),
    CurriculumTopic(
        id = "coroutines-best-practices",
        title = "Coroutine Best Practices",
        category = "Coroutines & Async",
        recap = Recap(
            previousTopicTitle = "Asynchronous Flow",
            recapText = "Flow is a cold stream of many values; the code runs only when collected.",
            quickCheckQuestion = "Flow vs suspend fun? What does 'cold' mean?",
            quickCheckAnswer = "many values vs one; nothing runs until collected.",
        ),
        explain = "Coroutines follow structured concurrency: coroutines are launched in a scope, and the scope won't complete until its children do — so nothing leaks. Key practices:\n\n" +
            "- Launch in an appropriate CoroutineScope (on Android, viewModelScope / lifecycleScope).\n" +
            "- Pick the right dispatcher: Dispatchers.Default (CPU work), Dispatchers.IO (blocking I/O), Dispatchers.Main (UI).\n" +
            "- Cancellation is cooperative — long-running work should check isActive or call suspending functions that are cancellation-aware.\n" +
            "- Use withContext(dispatcher) { … } to switch threads for a block.",
        example = """
            |import kotlinx.coroutines.*
            |
            |suspend fun loadData(): String = withContext(Dispatchers.IO) {
            |    // heavy or blocking work runs on an IO thread
            |    delay(100)
            |    "data"
            |}
            |
            |fun main() = runBlocking {
            |    val job = launch {
            |        val result = loadData()
            |        println("got ${'$'}result")
            |    }
            |    job.join()   // structured: wait for the child to finish
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Don't launch coroutines in GlobalScope in app code — it escapes structured concurrency and can leak.",
            "Move blocking work off the main thread with withContext(Dispatchers.IO).",
            "Respect cancellation; don't swallow CancellationException.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "coroutines-best-practices-q1",
                topicId = "coroutines-best-practices",
                question = "What does structured concurrency guarantee?",
                options = listOf(
                    "Coroutines never throw exceptions",
                    "Child coroutines are bound to a scope that won't finish until they do, preventing leaks",
                    "All coroutines run on the same thread",
                    "Coroutines automatically retry on failure",
                ),
                correctIndex = 1,
                explanation = "Structured concurrency ties every child coroutine's lifetime to its parent scope, so nothing outlives its scope unexpectedly.",
            ),
            QuizQuestion(
                id = "coroutines-best-practices-q2",
                topicId = "coroutines-best-practices",
                question = "Which dispatcher is intended for blocking I/O work?",
                options = listOf("Dispatchers.Main", "Dispatchers.Default", "Dispatchers.IO", "Dispatchers.Unconfined"),
                correctIndex = 2,
                explanation = "Dispatchers.IO is tuned for blocking I/O operations like network or disk access.",
            ),
            QuizQuestion(
                id = "coroutines-best-practices-q3",
                topicId = "coroutines-best-practices",
                question = "Why avoid GlobalScope in application code?",
                options = listOf(
                    "It's deprecated and won't compile",
                    "It isn't tied to any lifecycle, so its coroutines can leak",
                    "It only works inside tests",
                    "It's slower than other scopes",
                ),
                correctIndex = 1,
                explanation = "GlobalScope coroutines live for the whole application process, outside structured concurrency's leak protection.",
            ),
        ),
        tutorFocus = "Emphasize scopes + dispatchers + cooperative cancellation. On Android, tie this to viewModelScope. Exercise: take a snippet using GlobalScope and refactor it to a proper scope with the right dispatcher.",
    ),
)
