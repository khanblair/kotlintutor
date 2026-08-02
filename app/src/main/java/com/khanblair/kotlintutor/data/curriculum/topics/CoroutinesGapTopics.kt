package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val coroutinesGapTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "coroutines-behavior",
        title = "Coroutines Behavior",
        category = "Coroutines & Async Programming",
        recap = Recap(
            previousTopicTitle = "Safe Casts",
            recapText = "as? performs a safe cast: it returns null instead of throwing ClassCastException when the cast fails; the unsafe as throws. as? Foo always has the nullable type Foo?, so it composes with ?. and ?:.",
            quickCheckQuestion = "What does (v as? Number)?.toInt() give you if v is a String?",
            quickCheckAnswer = "null — the safe cast fails silently instead of throwing, so the whole chain short-circuits to null.",
        ),
        explain = "Cancellation in Kotlin coroutines is cooperative, not forcible:\n\n" +
            "- Calling job.cancel() only requests cancellation — the coroutine keeps running until it reaches a suspension point (delay, another suspend fun) or explicitly checks isActive / calls ensureActive().\n" +
            "- Kotlin's built-in suspending functions are already cancellation-aware and throw a CancellationException the next time they're invoked on a cancelled coroutine. Tight, CPU-bound loops with no suspension points must poll isActive or call ensureActive() themselves, or they'll never notice they've been cancelled.\n" +
            "- That CancellationException propagates like any exception, but structured concurrency treats it as a silent, expected shutdown rather than a failure — if you catch it, you must rethrow it, or you'll break cancellation for the rest of the coroutine hierarchy.\n\n" +
            "withContext(dispatcher) { … } switches the dispatcher used for a block of code while staying inside the same coroutine: it suspends, runs the block on the new dispatcher, and resumes back on the original dispatcher with the block's result. That's different from launch(dispatcher) { … }, which starts a brand-new, independent child coroutine rather than temporarily relocating the current one — withContext is the tool for \"do this one step elsewhere, then give me the result back here.\"\n\n" +
            "Exception handling differs by builder:\n\n" +
            "- launch surfaces an uncaught exception immediately: it propagates to the parent scope right away, which by default cancels its sibling coroutines. Handle it with try/catch inside the coroutine, or attach a CoroutineExceptionHandler to the top-level scope — it only takes effect on the outermost job of a hierarchy, not on a nested launch.\n" +
            "- async defers surfacing its exception: it's still captured, but only becomes visible to your code when you call .await() on the resulting Deferred, which rethrows it there. Call await() and you can catch it; never call it, and the exception is effectively swallowed from your code's perspective — a common source of silently lost errors.",
        example = """
            |import kotlinx.coroutines.*
            |
            |suspend fun cooperativeWork() {
            |    var i = 0
            |    while (i < 1_000_000_000) {
            |        i++
            |        if (i % 100_000_000 == 0) {
            |            coroutineContext.ensureActive()   // throws if the job was cancelled
            |        }
            |    }
            |}
            |
            |fun main() = runBlocking {
            |    val job = launch {
            |        try {
            |            cooperativeWork()
            |        } catch (e: CancellationException) {
            |            println("cancelled cleanly")
            |            throw e                            // always rethrow CancellationException
            |        }
            |    }
            |    delay(50)
            |    job.cancelAndJoin()                        // request cancellation, wait for it to stop
            |
            |    val handler = CoroutineExceptionHandler { _, e ->
            |        println("handled: ${'$'}{e.message}")
            |    }
            |    launch(handler) { error("boom from launch") }   // handler fires immediately
            |
            |    val deferred = async { error("boom from async") }
            |    delay(50)
            |    runCatching { deferred.await() }               // exception only surfaces here
            |        .onFailure { println("from await: ${'$'}{it.message}") }
            |
            |    val result = withContext(Dispatchers.Default) {
            |        6 * 7                                       // same coroutine, different dispatcher
            |    }
            |    println("result = ${'$'}result")
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Cancellation is cooperative: a coroutine only stops at a suspension point or when it checks isActive / calls ensureActive() — nothing forcibly kills it.",
            "CancellationException is the expected signal for a cancelled coroutine; catching it is fine, but you must rethrow it to keep structured concurrency working.",
            "withContext switches the dispatcher for a block and stays in the same coroutine, returning the block's result; launch(dispatcher) starts a separate child coroutine instead.",
            "launch propagates an uncaught exception to its parent immediately; async holds it until .await() is called, so an un-awaited async can silently lose an error.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "coroutines-behavior-q1",
                topicId = "coroutines-behavior",
                question = "What actually happens when you call job.cancel() on a coroutine running a tight CPU-bound loop with no suspension points?",
                options = listOf(
                    "The coroutine is killed immediately, mid-instruction",
                    "Nothing stops it until it reaches a suspension point or checks isActive / calls ensureActive() itself",
                    "It throws a CancellationException on a background thread automatically",
                    "The JVM pauses the thread until the loop finishes",
                ),
                correctIndex = 1,
                explanation = "Cancellation is cooperative — the coroutine has to check for it (via a suspension point or isActive/ensureActive()) before it will actually stop.",
            ),
            QuizQuestion(
                id = "coroutines-behavior-q2",
                topicId = "coroutines-behavior",
                question = "How does withContext(Dispatchers.Default) { ... } differ from launch(Dispatchers.Default) { ... }?",
                options = listOf(
                    "They're equivalent — both just pick a dispatcher",
                    "withContext switches dispatchers for a block and stays in the same coroutine, returning its result; launch starts a separate child coroutine",
                    "withContext can only be called from Dispatchers.Main",
                    "launch blocks the caller until the child completes, withContext does not",
                ),
                correctIndex = 1,
                explanation = "withContext suspends the current coroutine, runs the block on the given dispatcher, and resumes with its result — it doesn't create a new independent coroutine the way launch does.",
            ),
            QuizQuestion(
                id = "coroutines-behavior-q3",
                topicId = "coroutines-behavior",
                question = "If an async { ... } block throws an exception and you never call .await() on the resulting Deferred, what happens to that exception from your code's perspective?",
                options = listOf(
                    "It's thrown immediately, just like launch",
                    "It's captured on the Deferred and only rethrown when .await() is called — never calling await() means your code never sees it",
                    "It crashes the JVM instantly regardless of await()",
                    "It's automatically logged to CoroutineExceptionHandler",
                ),
                correctIndex = 1,
                explanation = "async defers exception visibility until .await(); skipping await() means the exception is effectively lost to the calling code even though the coroutine failed.",
            ),
        ),
        tutorFocus = "Demonstrate cooperative cancellation concretely: run a tight loop without a cancellation check and show it ignores cancel(), then add ensureActive() and show it stops. Contrast launch vs async exception visibility side by side. Exercise: have the learner add a CoroutineExceptionHandler to a scope and predict/verify which failures it catches (launch) and which it doesn't (async without await).",
    ),
)
