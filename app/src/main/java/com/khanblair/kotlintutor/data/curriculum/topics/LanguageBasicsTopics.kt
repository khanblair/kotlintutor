package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val languageBasicsTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "val-vs-var",
        title = "val vs var",
        category = "Language Basics",
        recap = Recap(
            previousTopicTitle = "Java Interoperability",
            recapText = "Kotlin and Java call each other freely on the JVM; watch platform types (Java values with unknown nullability).",
            quickCheckQuestion = "What's the risk with a Java-returned value?",
            quickCheckAnswer = "it may be null yet skips Kotlin's null checks.",
        ),
        explain = "Kotlin has two keywords for declaring variables:\n\n" +
            "- val (from \"value\") declares a read-only reference. Once assigned, you can't reassign it. This is the default you should reach for.\n" +
            "- var (from \"variable\") declares a mutable reference that can be reassigned.\n\n" +
            "Preferring val makes code easier to reason about. Note the subtlety: val makes the reference immutable, not necessarily the object it points to. A val holding a MutableList can't be reassigned, but its contents can change.",
        example = """
            |fun main() {
            |    val name = "Ada"      // read-only
            |    var count = 0         // mutable
            |    count += 1            // OK
            |    // name = "Grace"     // Compile error: val cannot be reassigned
            |
            |    val numbers = mutableListOf(1, 2, 3)
            |    numbers.add(4)        // OK — reference is fixed, list is mutable
            |    println("${'$'}name ${'$'}count ${'$'}numbers")
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "val fixes the reference, not the referenced object's mutability.",
            "Default to val; use var only when reassignment is genuinely needed.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "val-vs-var-q1",
                topicId = "val-vs-var",
                question = "Which keyword should you prefer by default, and why?",
                options = listOf(
                    "var, because it's more flexible",
                    "val, because immutable references are easier to reason about",
                    "Neither — always declare an explicit type instead",
                    "var, because val cannot hold collections",
                ),
                correctIndex = 1,
                explanation = "val is the default: fewer moving parts, easier to reason about.",
            ),
            QuizQuestion(
                id = "val-vs-var-q2",
                topicId = "val-vs-var",
                question = "Can you modify the contents of a MutableList stored in a val?",
                options = listOf(
                    "No — val makes the whole object immutable",
                    "Yes — val fixes the reference, not the object",
                    "Only if you cast it to var first",
                    "Only inside the same function it was declared in",
                ),
                correctIndex = 1,
                explanation = "val only prevents reassigning the reference; the mutable object it points to can still change.",
            ),
            QuizQuestion(
                id = "val-vs-var-q3",
                topicId = "val-vs-var",
                question = "What error do you get if you reassign a val?",
                options = listOf(
                    "A runtime NullPointerException",
                    "A silent no-op — the old value is kept",
                    "A compile-time error",
                    "A deprecation warning only",
                ),
                correctIndex = 2,
                explanation = "Reassigning a val is a compile-time error, not a runtime failure.",
            ),
        ),
        tutorFocus = "Probe the \"val means immutable object\" misconception directly: ask what happens when they .add() to a val mutable list. Exercise: give a snippet that overuses var and ask them to tighten it to val.",
    ),
    CurriculumTopic(
        id = "type-inference",
        title = "Type Inference",
        category = "Language Basics",
        recap = Recap(
            previousTopicTitle = "val vs var",
            recapText = "val is a read-only reference, var is reassignable; val fixes the reference, not the object.",
            quickCheckQuestion = "Which is the default? Can a val MutableList change contents?",
            quickCheckAnswer = "val; yes.",
        ),
        explain = "Kotlin is statically typed, but you rarely write types explicitly because the compiler infers them from the initializer. val x = 5 is Int; val s = \"hi\" is String. Inference is compile-time — the type is fixed, just not spelled out. Single-expression function return types are inferred too.",
        example = """
            |fun main() {
            |    val count = 42            // inferred Int
            |    val price = 9.99          // inferred Double
            |    val label: String         // explicit type, assigned later
            |    label = "ready"
            |    println("${'$'}count ${'$'}price ${'$'}label")
            |}
            |
            |fun square(n: Int) = n * n   // return type Int inferred
        """.trimMargin(),
        keyPoints = listOf(
            "Inference is static, not dynamic typing.",
            "A bare integer literal is Int; a decimal literal is Double (not Float).",
            "Prefer explicit types on public APIs for readability and stability.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "type-inference-q1",
                topicId = "type-inference",
                question = "What type is inferred for val x = 10? For val y = 10.0?",
                options = listOf("Int and Float", "Int and Double", "Long and Double", "Number and Number"),
                correctIndex = 1,
                explanation = "Bare integer literals default to Int; decimal literals default to Double.",
            ),
            QuizQuestion(
                id = "type-inference-q2",
                topicId = "type-inference",
                question = "Does type inference make Kotlin dynamically typed?",
                options = listOf(
                    "Yes — types can change at runtime",
                    "No — the type is still fixed at compile time, just not spelled out",
                    "Only for var, not val",
                    "Yes, but only inside lambdas",
                ),
                correctIndex = 1,
                explanation = "Inference is a compile-time convenience; Kotlin remains statically typed.",
            ),
            QuizQuestion(
                id = "type-inference-q3",
                topicId = "type-inference",
                question = "When must you write a type explicitly?",
                options = listOf(
                    "Always, for every variable",
                    "Never — the compiler can always infer it",
                    "When there's no initializer, or to widen/clarify the type",
                    "Only for function parameters",
                ),
                correctIndex = 2,
                explanation = "Without an initializer the compiler has nothing to infer from, so the type must be written explicitly.",
            ),
        ),
        tutorFocus = "Reinforce \"inferred ≠ dynamic.\" Ask what type val y = 10.0 is — learners often guess Float. Exercise: declare a variable without an initializer and assign it later with the right type.",
    ),
    CurriculumTopic(
        id = "data-types",
        title = "Basic Types",
        category = "Language Basics",
        recap = Recap(
            previousTopicTitle = "Type Inference",
            recapText = "The compiler infers types from initializers — still static typing, just not spelled out.",
            quickCheckQuestion = "Type of val y = 10.0? Is inference dynamic typing?",
            quickCheckAnswer = "Double; no.",
        ),
        explain = "Kotlin's built-in types:\n\n" +
            "- Integers: Byte (8-bit), Short (16), Int (32), Long (64). Use an L suffix for Long literals (10L).\n" +
            "- Unsigned integers: UByte, UShort, UInt, ULong (suffix u, e.g. 10u).\n" +
            "- Floating point: Float (32-bit, suffix f) and Double (64-bit, default).\n" +
            "- Boolean: true / false.\n" +
            "- Char: a single character in single quotes, 'A'.\n" +
            "- String: double-quoted text; supports templates and multiline \"\"\"…\"\"\".\n" +
            "- Arrays: fixed-size via arrayOf(...) or typed variants like IntArray.\n\n" +
            "Unlike Java, Kotlin has no primitive/wrapper split in the language — you always write Int, and the compiler uses primitives under the hood where possible.",
        example = """
            |fun main() {
            |    val i: Int = 100
            |    val big: Long = 10_000_000_000L      // underscores aid readability
            |    val ratio: Double = 3.14
            |    val flag: Boolean = true
            |    val letter: Char = 'K'
            |    val nums: IntArray = intArrayOf(1, 2, 3)
            |    val positive: UInt = 42u
            |    println("${'$'}i ${'$'}big ${'$'}ratio ${'$'}flag ${'$'}letter ${'$'}{nums.sum()} ${'$'}positive")
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "No implicit widening: assign an Int to a Long only via .toLong().",
            "Literals default to Int / Double; use suffixes for Long / Float.",
            "Arrays are fixed-size; use lists when you need to grow or shrink.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "data-types-q1",
                topicId = "data-types",
                question = "What's the default type of 5? Of 5.0?",
                options = listOf("Int and Double", "Long and Float", "Int and Float", "Byte and Double"),
                correctIndex = 0,
                explanation = "Integer literals default to Int; decimal literals default to Double.",
            ),
            QuizQuestion(
                id = "data-types-q2",
                topicId = "data-types",
                question = "How do you write a Long literal? A Float literal?",
                options = listOf("10l and 10F", "10L and 10f", "L10 and f10", "10 and 10.0"),
                correctIndex = 1,
                explanation = "Append L for Long (10L) and f for Float (10f).",
            ),
            QuizQuestion(
                id = "data-types-q3",
                topicId = "data-types",
                question = "Does Kotlin auto-convert an Int to a Long?",
                options = listOf(
                    "Yes, always",
                    "No — you must call .toLong()",
                    "Only in arithmetic expressions",
                    "Only when assigning to a val",
                ),
                correctIndex = 1,
                explanation = "Kotlin has no implicit numeric widening; convert explicitly with .toLong().",
            ),
        ),
        tutorFocus = "The key gotcha is no implicit numeric conversion. Set an exercise adding an Int and a Long so the learner discovers .toLong(). Also quiz the Int vs Double literal defaults.",
    ),
    CurriculumTopic(
        id = "string-templates",
        title = "String Templates",
        category = "Language Basics",
        recap = Recap(
            previousTopicTitle = "Basic Types",
            recapText = "Int/Long/Double etc., no implicit widening; literals default to Int/Double.",
            quickCheckQuestion = "How do you write a Long literal? Convert Int to Long?",
            quickCheckAnswer = "10L; .toLong().",
        ),
        explain = "String templates embed values and expressions in a string using \$. Use \$name for a simple variable and \${expression} for anything more complex. Raw (multiline) strings with triple quotes don't process escapes and are great for JSON, SQL, or multi-line text.",
        example = """
            |fun main() {
            |    val name = "Ada"
            |    val items = listOf("a", "b", "c")
            |    println("Hello, ${'$'}name!")                     // simple
            |    println("You have ${'$'}{items.size} items.")     // expression
            |    println("Uppercase: ${'$'}{name.uppercase()}")
            |
            |    val json = ${'"'}${'"'}${'"'}
            |        {
            |          "name": "${'$'}name",
            |          "count": ${'$'}{items.size}
            |        }
            |    ${'"'}${'"'}${'"'}.trimIndent()
            |    println(json)
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Use \${} for property access, method calls, or arithmetic; \$var only for a bare variable.",
            "Print a literal \$ with \${'\$'} or a raw string.",
            ".trimIndent() cleans leading whitespace in multiline strings.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "string-templates-q1",
                topicId = "string-templates",
                question = "What's the difference between \$name and \${name.length}?",
                options = listOf(
                    "No difference",
                    "\$name inserts a variable; \${...} evaluates an expression",
                    "\$name only works with numbers",
                    "\${...} is only for raw strings",
                ),
                correctIndex = 1,
                explanation = "\$name is the shorthand for a bare variable; \${...} is required for expressions like property access or calls.",
            ),
            QuizQuestion(
                id = "string-templates-q2",
                topicId = "string-templates",
                question = "How do you write a multi-line string?",
                options = listOf(
                    "Escape every newline with \\n",
                    "Triple-quoted \"\"\"…\"\"\", usually with .trimIndent()",
                    "Use a StringBuilder only",
                    "Concatenate with + across lines",
                ),
                correctIndex = 1,
                explanation = "Triple-quoted raw strings support multi-line content directly, cleaned up with .trimIndent().",
            ),
            QuizQuestion(
                id = "string-templates-q3",
                topicId = "string-templates",
                question = "How do you include a literal dollar sign in a template string?",
                options = listOf("\\$", "$$", "\${'\$'} or a raw string", "It's not possible"),
                correctIndex = 2,
                explanation = "\${'\$'} escapes a literal dollar sign inside a template string.",
            ),
        ),
        tutorFocus = "Have the learner convert +-concatenation into a template. Probe the \${} vs \$ rule with items.size. Exercise: build a receipt line using a template with a calculation.",
    ),
    CurriculumTopic(
        id = "type-checks-casts",
        title = "Type Checks & Casts",
        category = "Language Basics",
        recap = Recap(
            previousTopicTitle = "String Templates",
            recapText = "\$var and \${expr} embed values; triple quotes make raw multiline strings.",
            quickCheckQuestion = "When do you need \${}? Multiline string syntax?",
            quickCheckAnswer = "for expressions; \"\"\"…\"\"\".",
        ),
        explain = "Kotlin checks types with is (and !is). A key feature is the smart cast: after x is String, the compiler treats x as String in that scope — no manual cast needed. For explicit casting, as is an unsafe cast (throws on failure) and as? is a safe cast that returns null on failure.",
        example = """
            |fun describe(x: Any): String {
            |    if (x is String) {
            |        return "String of length ${'$'}{x.length}"   // smart cast to String
            |    }
            |    return when (x) {
            |        is Int -> "Int: ${'$'}{x + 1}"                // smart cast to Int
            |        is Boolean -> "Boolean: ${'$'}{!x}"
            |        else -> "Unknown"
            |    }
            |}
            |
            |fun main() {
            |    val maybeText: Any = "hello"
            |    val safe: String? = maybeText as? String     // safe cast → "hello"
            |    println(describe(42))
            |    println(safe?.uppercase())
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Smart casts apply only when the compiler can prove the value didn't change (e.g. not on a mutable var property).",
            "Prefer as? over as to avoid ClassCastException.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "type-checks-casts-q1",
                topicId = "type-checks-casts",
                question = "What is a smart cast?",
                options = listOf(
                    "A cast the compiler performs automatically at runtime",
                    "After an is check, the value is auto-treated as that type without an explicit cast",
                    "A cast that never fails",
                    "A cast that only works on nullable types",
                ),
                correctIndex = 1,
                explanation = "Once the compiler proves a value's type via is, it auto-treats it as that type in scope.",
            ),
            QuizQuestion(
                id = "type-checks-casts-q2",
                topicId = "type-checks-casts",
                question = "What's the difference between as and as??",
                options = listOf(
                    "No difference",
                    "as throws on failure; as? returns null",
                    "as? is faster",
                    "as only works on primitives",
                ),
                correctIndex = 1,
                explanation = "as is unsafe and throws ClassCastException on failure; as? safely returns null instead.",
            ),
            QuizQuestion(
                id = "type-checks-casts-q3",
                topicId = "type-checks-casts",
                question = "Why might a smart cast not apply to a var property?",
                options = listOf(
                    "var properties can't be type-checked with is",
                    "The compiler can't guarantee a mutable value is unchanged between check and use",
                    "Smart casts only work on local variables named x",
                    "It's a compiler bug, not a rule",
                ),
                correctIndex = 1,
                explanation = "A var property could be modified (e.g. from another thread) between the is check and its use, so the compiler won't smart-cast it.",
            ),
        ),
        tutorFocus = "Smart casts are the \"wow\" moment — show that no manual cast is needed after is. Push the safe-cast habit (as? by default). Exercise: write a function handling an Any that returns different results per type via when (x).",
    ),
    CurriculumTopic(
        id = "print-println",
        title = "Printing data",
        category = "Language Basics",
        recap = Recap(
            previousTopicTitle = "Type Checks & Casts",
            recapText = "is checks type and enables smart casts; as? casts safely (null on failure).",
            quickCheckQuestion = "What is a smart cast? as vs as??",
            quickCheckAnswer = "auto-treats value as the checked type; as throws, as? returns null.",
        ),
        explain = "The core output functions are print (no newline) and println (adds a newline). They accept any value and call its toString(). Data classes give a readable representation for free; plain classes print an unhelpful hash unless you override toString(). Read console input with readLine().",
        example = """
            |data class Point(val x: Int, val y: Int)
            |
            |fun main() {
            |    print("no newline ")
            |    println("with newline")
            |    println(Point(1, 2))            // Point(x=1, y=2) via generated toString
            |    println(listOf(1, 2, 3))        // [1, 2, 3]
            |    // val name = readLine()        // reads a line from stdin
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Printing a plain (non-data) class shows a hash — motivate toString().",
            "Templates are usually cleaner than passing many arguments.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "print-println-q1",
                topicId = "print-println",
                question = "What's the difference between print and println?",
                options = listOf(
                    "No difference",
                    "println appends a newline; print does not",
                    "print is for numbers, println for strings",
                    "println is deprecated",
                ),
                correctIndex = 1,
                explanation = "println adds a trailing newline after the output; print does not.",
            ),
            QuizQuestion(
                id = "print-println-q2",
                topicId = "print-println",
                question = "Why does a data class print more nicely than a plain class?",
                options = listOf(
                    "Data classes are stored differently in memory",
                    "Data classes auto-generate toString()",
                    "Plain classes can't override toString()",
                    "println treats data classes specially",
                ),
                correctIndex = 1,
                explanation = "The compiler generates a readable toString() for data classes; plain classes fall back to a hash-based default.",
            ),
            QuizQuestion(
                id = "print-println-q3",
                topicId = "print-println",
                question = "How do you read a line of console input?",
                options = listOf("Console.read()", "readLine()", "System.in()", "input()"),
                correctIndex = 1,
                explanation = "readLine() reads a line of text from standard input.",
            ),
        ),
        tutorFocus = "Quick and practical. Use the plain-class hash output to motivate toString() and data classes later. Exercise: print the same object as a plain class vs a data class and compare.",
    ),
)
