package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val competitiveProgrammingTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "competitive-programming",
        title = "Competitive Programming",
        category = "Competitive Programming",
        recap = Recap(
            previousTopicTitle = "Kandy",
            recapText = "Kandy is JetBrains's declarative charting DSL that pairs with Kotlin DataFrame, rendering charts inline inside a Kotlin Notebook as the visualization step of an exploratory data analysis pipeline.",
            quickCheckQuestion = "What's the typical three-step EDA pipeline covered by Kotlin Notebooks, DataFrame, and Kandy?",
            quickCheckAnswer = "Explore and document in a notebook, wrangle data with DataFrame, then visualize it with Kandy — all inline.",
        ),
        explain = "Competitive programming rewards languages that let you translate an idea into working code fast, under a strict clock, without fighting the language for boilerplate. Kotlin fits well: concise syntax (data classes, when expressions, single-expression functions, destructuring) means less typing between 'I know the algorithm' and 'it compiles,' and a rich standard library (sortedBy, groupBy, maxOf, sequences, and other collection algorithms) covers a lot of ground that would otherwise be hand-rolled. The catch is default I/O: println and readLine() alone are too slow for problems that read or print tens of thousands of lines, so competitive Kotlin code almost always swaps in java.io.BufferedReader for input and a StringBuilder for output, flushing once at the end instead of printing line by line. readLine() itself returns a nullable String?, so competitive code commonly reads with readLine()!! when EOF isn't expected mid-input. Several major judges accept Kotlin submissions directly, including Codeforces, LeetCode, and AtCoder, so these habits transfer straight into real contests.",
        example = """
            |import java.io.BufferedReader
            |import java.io.InputStreamReader
            |import java.util.StringTokenizer
            |
            |fun main() {
            |    val br = BufferedReader(InputStreamReader(System.`in`))
            |    val n = br.readLine().trim().toInt()
            |    val st = StringTokenizer(br.readLine())
            |    val nums = IntArray(n) { st.nextToken().toInt() }
            |
            |    val sb = StringBuilder()
            |    repeat(n) { i -> sb.append(nums[i] * 2).append('\n') }
            |    print(sb)                                  // one flush instead of n prints
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Kotlin's concise syntax (data classes, when, destructuring, single-expression functions) reduces boilerplate under time pressure.",
            "Default println/readLine() I/O is too slow for large inputs; competitive code typically switches to BufferedReader for input and a single StringBuilder flushed once for output.",
            "readLine() returns String?, so competitive code commonly uses readLine()!! when EOF isn't expected.",
            "Codeforces, LeetCode, and AtCoder all accept Kotlin submissions directly.",
            "repeat(n) { } and sequence-based lazy generation (e.g. generateSequence, asSequence()) are common idioms for tight, allocation-conscious loops.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "competitive-programming-q1",
                topicId = "competitive-programming",
                question = "Why do competitive programmers in Kotlin typically avoid plain println() for large amounts of output?",
                options = listOf(
                    "println() doesn't support numbers",
                    "Each println() call flushes individually, which is far slower than building output once with StringBuilder and printing it in one go",
                    "println() is deprecated",
                    "println() cannot be used inside loops",
                ),
                correctIndex = 1,
                explanation = "Repeated println() calls each incur their own I/O overhead; building the full output in a StringBuilder and printing it once avoids that per-line cost.",
            ),
            QuizQuestion(
                id = "competitive-programming-q2",
                topicId = "competitive-programming",
                question = "Why does readLine()!! appear so often in competitive Kotlin code?",
                options = listOf(
                    "readLine() returns a nullable String?, and !! asserts a non-null value is present since EOF isn't expected mid-input",
                    "readLine() requires two exclamation marks by syntax rules",
                    "!! converts the input to an Int automatically",
                    "It's required to import java.io",
                ),
                correctIndex = 0,
                explanation = "readLine() is declared to return String? because it returns null at end of input; !! asserts non-null when the code expects more input to still be available.",
            ),
            QuizQuestion(
                id = "competitive-programming-q3",
                topicId = "competitive-programming",
                question = "Which of these platforms accepts Kotlin submissions directly for competitive programming?",
                options = listOf(
                    "Only Codeforces",
                    "Codeforces, LeetCode, and AtCoder all do",
                    "None of the major judges support Kotlin",
                    "Only LeetCode",
                ),
                correctIndex = 1,
                explanation = "Codeforces, LeetCode, and AtCoder are all major competitive programming platforms that accept Kotlin as a submission language.",
            ),
        ),
        tutorFocus = "Emphasize the I/O-speed gotcha since it trips up newcomers moving from println-based examples — an algorithm can be correct but still time out on slow I/O. Exercise: have the learner rewrite a naive println-per-line output loop to use a single StringBuilder flush, and explain why it matters at scale.",
    ),
)
