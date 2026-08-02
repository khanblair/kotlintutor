package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val collectionsTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "lists-sets-maps",
        title = "Lists, Sets, Maps",
        category = "Collections",
        recap = Recap(
            previousTopicTitle = "Printing data",
            recapText = "print/println call toString(); data classes print nicely, plain classes show a hash.",
            quickCheckQuestion = "Difference between print and println? Read a line of input?",
            quickCheckAnswer = "println adds a newline; readLine().",
        ),
        explain = "Kotlin's three core collection types:\n\n" +
            "- List — an ordered collection that allows duplicates; access by index.\n" +
            "- Set — an unordered collection of unique elements; no duplicates.\n" +
            "- Map — a collection of key→value pairs with unique keys.\n\n" +
            "Each comes in a read-only flavor (List, Set, Map) created with listOf, setOf, mapOf, and a mutable flavor (MutableList, etc.) created with mutableListOf, mutableSetOf, mutableMapOf. Read-only interfaces don't expose add/remove — reach for mutable only when you need to change the collection.",
        example = """
            |fun main() {
            |    val list = listOf("a", "b", "a")        // duplicates kept, order preserved
            |    val set = setOf("a", "b", "a")           // → [a, b] duplicates removed
            |    val map = mapOf("one" to 1, "two" to 2)  // key→value pairs
            |
            |    println(list[0])                         // index access → "a"
            |    println(set.contains("b"))               // true
            |    println(map["two"])                      // 2
            |
            |    val mutable = mutableListOf(1, 2)
            |    mutable.add(3)
            |    println(mutable)                         // [1, 2, 3]
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "listOf(...) is read-only, not deeply immutable, but you can't add/remove.",
            "Use to to build map entries: key to value.",
            "Choosing the right type communicates intent (uniqueness → Set, lookup → Map).",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "lists-sets-maps-q1",
                topicId = "lists-sets-maps",
                question = "Which collection type removes duplicates?",
                options = listOf("List", "Set", "Map", "Array"),
                correctIndex = 1,
                explanation = "Set stores only unique elements.",
            ),
            QuizQuestion(
                id = "lists-sets-maps-q2",
                topicId = "lists-sets-maps",
                question = "How do you create a read-only vs mutable list?",
                options = listOf(
                    "listOf(...) vs mutableListOf(...)",
                    "list(...) vs mutableList(...)",
                    "ReadOnlyList(...) vs List(...)",
                    "There's no difference in Kotlin",
                ),
                correctIndex = 0,
                explanation = "listOf builds a read-only List; mutableListOf builds a MutableList.",
            ),
            QuizQuestion(
                id = "lists-sets-maps-q3",
                topicId = "lists-sets-maps",
                question = "What does map[\"key\"] return if the key is absent?",
                options = listOf("Throws an exception", "An empty string", "null", "0"),
                correctIndex = 2,
                explanation = "Map's indexed access returns null when the key isn't present.",
            ),
        ),
        tutorFocus = "Emphasize picking the type by intent. Probe the read-only vs mutable distinction (learners often default to mutable). Exercise: given a list with duplicates, produce the unique values and a count per value.",
    ),
    CurriculumTopic(
        id = "ranges",
        title = "Ranges & Progressions",
        category = "Collections",
        recap = Recap(
            previousTopicTitle = "Lists, Sets, Maps",
            recapText = "List keeps order + duplicates, Set is unique, Map is key→value; each has read-only and mutable forms.",
            quickCheckQuestion = "Which removes duplicates? Read-only list builder?",
            quickCheckAnswer = "Set; listOf(...).",
        ),
        explain = "A range expresses a sequence of values. 1..5 is an inclusive IntRange (1,2,3,4,5). Use until for an exclusive upper bound (1 until 5 → 1..4), downTo to count down, and step to change the increment. A range with a step becomes a progression. Ranges are handy in for loops and for in membership checks.",
        example = """
            |fun main() {
            |    for (i in 1..5) print("${'$'}i ")          // 1 2 3 4 5
            |    println()
            |    for (i in 1 until 5) print("${'$'}i ")     // 1 2 3 4
            |    println()
            |    for (i in 10 downTo 1 step 2) print("${'$'}i ")  // 10 8 6 4 2
            |    println()
            |    println(3 in 1..5)                    // true — membership check
            |    println('c' in 'a'..'z')              // true — works for Chars too
            |}
        """.trimMargin(),
        keyPoints = listOf(
            ".. is inclusive; until excludes the upper bound — a common off-by-one trap.",
            "in works for ranges of comparable types, including Char.",
            "step must be positive even when using downTo.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "ranges-q1",
                topicId = "ranges",
                question = "What values does 1..3 produce? And 1 until 3?",
                options = listOf(
                    "1,2,3 and 1,2,3",
                    "1,2,3 and 1,2",
                    "1,2 and 1,2,3",
                    "0,1,2 and 1,2,3",
                ),
                correctIndex = 1,
                explanation = ".. is inclusive (1,2,3); until excludes the upper bound (1,2).",
            ),
            QuizQuestion(
                id = "ranges-q2",
                topicId = "ranges",
                question = "How do you iterate from 5 down to 1?",
                options = listOf(
                    "for (i in 5..1)",
                    "for (i in 1..5).reversed()",
                    "for (i in 5 downTo 1)",
                    "for (i in 5 until 1)",
                ),
                correctIndex = 2,
                explanation = "downTo counts down from the left operand to the right operand.",
            ),
            QuizQuestion(
                id = "ranges-q3",
                topicId = "ranges",
                question = "How do you check if a value is inside a range?",
                options = listOf(
                    "value.inRange(a, b)",
                    "value in a..b",
                    "range.has(value)",
                    "a <= value <= b",
                ),
                correctIndex = 1,
                explanation = "The in operator checks membership in a range.",
            ),
        ),
        tutorFocus = "The off-by-one between .. and until is the thing to drill. Quiz it explicitly. Exercise: print a times table using nested ranges, or check whether a character is a lowercase letter using in.",
    ),
)
