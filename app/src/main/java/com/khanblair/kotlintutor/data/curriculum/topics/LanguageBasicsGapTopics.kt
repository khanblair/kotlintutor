package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

/**
 * Fills the remaining "Language Basics" roadmap leaves not covered by the
 * original 42-topic curriculum. Continues the single Curriculum.topics
 * sequence, bridging from "Build Tools" (the last original topic).
 */
val languageBasicsGapTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "setting-up-environment",
        title = "Setting up the Environment",
        category = "Language Basics",
        recap = Recap(
            previousTopicTitle = "Build Tools",
            recapText = "Gradle and Maven are the two main Kotlin build tools; build.gradle.kts is itself Kotlin.",
            quickCheckQuestion = "What are the two main build tools for Kotlin?",
            quickCheckAnswer = "Gradle and Maven.",
        ),
        explain = "To write and run Kotlin you need a JDK (Java Development Kit) and either an IDE or the standalone Kotlin compiler. Most learners start with IntelliJ IDEA or Android Studio, both of which bundle Kotlin support out of the box. You can also install the Kotlin command-line compiler (`kotlinc`) via a package manager (SDKMAN, Homebrew) to compile and run `.kt` files directly from a terminal, which is handy for quick experiments.",
        example = """
            |// Compile and run a single file from the terminal:
            |// kotlinc hello.kt -include-runtime -d hello.jar
            |// java -jar hello.jar
            |
            |fun main() {
            |    println("Kotlin environment ready")
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "You need a JDK installed — Kotlin compiles to JVM bytecode by default.",
            "IntelliJ IDEA and Android Studio both have first-class Kotlin support built in.",
            "`kotlinc` lets you compile and run Kotlin without a full IDE, useful for quick scripts.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "setting-up-environment-q1",
                topicId = "setting-up-environment",
                question = "What do you need installed to run Kotlin on the JVM?",
                options = listOf("A JDK", "A JavaScript runtime", "A .NET runtime", "Nothing, Kotlin is standalone"),
                correctIndex = 0,
                explanation = "Kotlin/JVM compiles to bytecode that runs on a Java Virtual Machine, so a JDK is required.",
            ),
            QuizQuestion(
                id = "setting-up-environment-q2",
                topicId = "setting-up-environment",
                question = "Which command-line tool compiles a .kt file directly?",
                options = listOf("kotlinc", "ktc", "kbuild", "kotlinrun"),
                correctIndex = 0,
                explanation = "kotlinc is the standalone Kotlin compiler you can install and run from a terminal.",
            ),
            QuizQuestion(
                id = "setting-up-environment-q3",
                topicId = "setting-up-environment",
                question = "Which IDEs have built-in Kotlin support?",
                options = listOf(
                    "IntelliJ IDEA and Android Studio",
                    "Only Eclipse",
                    "Only Visual Studio",
                    "None — a plugin is always required",
                ),
                correctIndex = 0,
                explanation = "Both are JetBrains-lineage IDEs (Android Studio is built on IntelliJ) with Kotlin bundled in.",
            ),
        ),
        tutorFocus = "Keep this practical and brief — it's a setup topic, not a language concept. If the learner already has a working environment, offer to skip ahead. Exercise: have them run kotlinc --version or create a new Kotlin file in their IDE.",
    ),
    CurriculumTopic(
        id = "code-organisation",
        title = "Code Organisation",
        category = "Language Basics",
        recap = Recap(
            previousTopicTitle = "Setting up the Environment",
            recapText = "A JDK plus an IDE (or kotlinc) is all you need; IntelliJ IDEA and Android Studio bundle Kotlin support.",
            quickCheckQuestion = "What do you need installed to run Kotlin on the JVM?",
            quickCheckAnswer = "A JDK.",
        ),
        explain = "A Kotlin source file can contain multiple top-level declarations — functions, classes, properties — without needing a wrapping class (unlike Java, where every file needs a public class). By convention, one file's name matches its main public class, but this isn't enforced. Files are grouped into packages (folders) for namespacing, and a project typically separates `src/main/kotlin` (or `src/main/java`) for source code from `src/test/kotlin` for tests.",
        example = """
            |// File: Shapes.kt — multiple top-level declarations, no wrapper class needed
            |
            |const val PI_APPROX = 3.14159
            |
            |fun circleArea(radius: Double) = PI_APPROX * radius * radius
            |
            |class Square(val side: Double) {
            |    fun area() = side * side
            |}
            |
            |fun main() {
            |    println(circleArea(2.0))
            |    println(Square(3.0).area())
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Kotlin doesn't require one public class per file — top-level functions and properties are fully valid.",
            "Package declarations conventionally mirror the folder structure.",
            "Standard project layout: src/main/kotlin for code, src/test/kotlin for tests.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "code-organisation-q1",
                topicId = "code-organisation",
                question = "Does a Kotlin file need a wrapping public class like Java?",
                options = listOf("Yes, always", "No — top-level functions and properties are allowed", "Only for main()", "Only in Android projects"),
                correctIndex = 1,
                explanation = "Unlike Java, Kotlin files can contain top-level declarations directly, no enclosing class required.",
            ),
            QuizQuestion(
                id = "code-organisation-q2",
                topicId = "code-organisation",
                question = "Where do tests conventionally live in a Kotlin project?",
                options = listOf("src/test/kotlin", "src/main/tests", "test/", "Anywhere, it's not conventional"),
                correctIndex = 0,
                explanation = "The standard Gradle/Maven layout puts tests under src/test/kotlin, mirroring src/main/kotlin.",
            ),
            QuizQuestion(
                id = "code-organisation-q3",
                topicId = "code-organisation",
                question = "What is a package used for?",
                options = listOf("Namespacing declarations, mirroring folder structure", "Compiling faster", "Encrypting source code", "Declaring dependencies"),
                correctIndex = 0,
                explanation = "Packages group related declarations under a namespace, conventionally matching the directory layout.",
            ),
        ),
        tutorFocus = "Contrast with Java's one-public-class-per-file rule since that's the main surprise. Exercise: take several small Java-style single-purpose files and combine them into one Kotlin file with multiple top-level declarations.",
    ),
    CurriculumTopic(
        id = "main-function",
        title = "main Function",
        category = "Language Basics",
        recap = Recap(
            previousTopicTitle = "Code Organisation",
            recapText = "Kotlin files can hold multiple top-level declarations without a wrapping class; packages namespace them.",
            quickCheckQuestion = "Does a Kotlin file need a wrapping public class?",
            quickCheckAnswer = "No.",
        ),
        explain = "Every Kotlin program's entry point is a function named `main`. It can be declared with or without a `String` array parameter for command-line arguments: `fun main()` or `fun main(args: Array<String>)`. It must be a top-level function (not inside a class, unless marked with a companion `@JvmStatic`, which is rarely needed). A project typically has exactly one `main` function per runnable application, though multiple files can each have their own `main` for separate entry points (e.g., scripts, samples).",
        example = """
            |fun main() {
            |    println("No args needed")
            |}
            |
            |// Alternative with command-line arguments:
            |// fun main(args: Array<String>) {
            |//     println("Received ${'$'}{args.size} arguments")
            |// }
        """.trimMargin(),
        keyPoints = listOf(
            "main must be a top-level function to be recognized as an entry point.",
            "The Array<String> parameter is optional if you don't need command-line arguments.",
            "A project can have multiple main functions across different files, each independently runnable.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "main-function-q1",
                topicId = "main-function",
                question = "Is the Array<String> parameter required on main?",
                options = listOf("Yes, always", "No — fun main() alone is valid", "Only in Android", "Only when using Gradle"),
                correctIndex = 1,
                explanation = "Kotlin allows a parameterless fun main() when command-line arguments aren't needed.",
            ),
            QuizQuestion(
                id = "main-function-q2",
                topicId = "main-function",
                question = "Where must the main function be declared?",
                options = listOf("Inside any class", "As a top-level function", "Inside a companion object only", "Inside main.kt specifically"),
                correctIndex = 1,
                explanation = "main is recognized as an entry point when declared at the top level of a file.",
            ),
            QuizQuestion(
                id = "main-function-q3",
                topicId = "main-function",
                question = "Can a project have more than one main function?",
                options = listOf("No, only one is allowed", "Yes, across different files, each independently runnable", "Yes, but only in tests", "Yes, but only one can be compiled"),
                correctIndex = 1,
                explanation = "Different files can each define their own main, giving you multiple independent entry points.",
            ),
        ),
        tutorFocus = "Very short, factual topic. Confirm the learner can run a minimal program. Exercise: write a main that prints a greeting, then a second version that reads and prints command-line arguments.",
    ),
    CurriculumTopic(
        id = "comments",
        title = "Comments",
        category = "Language Basics",
        recap = Recap(
            previousTopicTitle = "main Function",
            recapText = "main is the program's entry point; the Array<String> parameter is optional.",
            quickCheckQuestion = "Is the Array<String> parameter required on main?",
            quickCheckAnswer = "No.",
        ),
        explain = "Kotlin supports single-line comments with `//` and multi-line block comments with `/* … */` (which can be nested, unlike Java). A special form, KDoc (`/** … */`), documents declarations for tooling like Dokka and IDE tooltips, using tags like `@param` and `@return`.",
        example = """
            |// This is a single-line comment
            |
            |/*
            | * This is a block comment,
            | * and /* it can nest */ safely.
            | */
            |
            |/**
            | * Adds two numbers together.
            | * @param a the first number
            | * @param b the second number
            | * @return the sum of [a] and [b]
            | */
            |fun add(a: Int, b: Int): Int = a + b
        """.trimMargin(),
        keyPoints = listOf(
            "// for single-line, /* … */ for block comments — and Kotlin's block comments nest.",
            "/** … */ (KDoc) documents a declaration for tools like Dokka and IDE hover tooltips.",
            "Prefer clear naming over comments explaining *what* code does; reserve comments for *why*.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "comments-q1",
                topicId = "comments",
                question = "Can Kotlin block comments be nested?",
                options = listOf("No, never", "Yes, unlike Java", "Only inside KDoc", "Only in test files"),
                correctIndex = 1,
                explanation = "Kotlin explicitly supports nested /* */ block comments, which Java does not.",
            ),
            QuizQuestion(
                id = "comments-q2",
                topicId = "comments",
                question = "What does a KDoc comment (/** ... */) provide?",
                options = listOf("Faster compilation", "Documentation for tools like Dokka and IDE tooltips", "Runtime debugging output", "Nothing, it's just a longer comment"),
                correctIndex = 1,
                explanation = "KDoc comments are structured documentation consumed by Dokka and shown in IDE tooltips.",
            ),
            QuizQuestion(
                id = "comments-q3",
                topicId = "comments",
                question = "What's generally the better alternative to a comment explaining what code does?",
                options = listOf("A longer comment", "Clear naming", "Removing the code", "A TODO tag"),
                correctIndex = 1,
                explanation = "Well-named functions and variables often make \"what\" comments unnecessary; save comments for the non-obvious \"why\".",
            ),
        ),
        tutorFocus = "Quick topic. Emphasize KDoc's tooling value over plain comments. Exercise: add a KDoc comment with @param and @return to a function the learner already wrote.",
    ),
    CurriculumTopic(
        id = "variables",
        title = "Variables",
        category = "Language Basics",
        recap = Recap(
            previousTopicTitle = "Comments",
            recapText = "// and /* */ for comments (block comments nest); /** */ (KDoc) documents declarations for tooling.",
            quickCheckQuestion = "Can Kotlin block comments be nested?",
            quickCheckAnswer = "Yes.",
        ),
        explain = "A variable is a named storage location. In Kotlin every variable is declared with val or var (see the dedicated val vs var topic), followed by a name and, optionally, an explicit type. Variables must be initialized before use — either at declaration or, for val, later exactly once (useful when the initial value depends on a branch). Uninitialized top-level or class properties need lateinit (for non-null reference types) or a nullable type defaulting to null.",
        example = """
            |fun main() {
            |    val name: String       // declared, not yet initialized
            |    if (System.currentTimeMillis() % 2 == 0L) {
            |        name = "Even run"
            |    } else {
            |        name = "Odd run"
            |    }
            |    println(name)          // definitely initialized by now, compiler verifies this
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "A val can be assigned later than its declaration, but only once — the compiler enforces this.",
            "Variables must be definitely initialized before their first read, or it's a compile error.",
            "lateinit (for var, non-null, non-primitive) defers initialization for properties set up later, e.g. in a setup function.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "variables-q1",
                topicId = "variables",
                question = "Can a val be assigned after its declaration line?",
                options = listOf("No, only at declaration", "Yes, but only once", "Yes, any number of times", "Only if it's nullable"),
                correctIndex = 1,
                explanation = "A val can be assigned later (e.g. in one branch of an if), as long as it's assigned exactly once.",
            ),
            QuizQuestion(
                id = "variables-q2",
                topicId = "variables",
                question = "What happens if you read a variable before it's definitely initialized?",
                options = listOf("It returns null silently", "It's a compile error", "It returns a default value like 0", "It throws at runtime only"),
                correctIndex = 1,
                explanation = "Kotlin's compiler performs definite-assignment analysis and rejects reads before initialization.",
            ),
            QuizQuestion(
                id = "variables-q3",
                topicId = "variables",
                question = "What keyword defers initialization of a non-null var property?",
                options = listOf("lazy", "lateinit", "defer", "late"),
                correctIndex = 1,
                explanation = "lateinit lets you declare a non-null var without an initial value, to be set before first use.",
            ),
        ),
        tutorFocus = "Cover definite-assignment since it surprises learners coming from more permissive languages. Exercise: declare a val without an initializer, assign it conditionally in an if/else, and show the compiler accepts it.",
    ),
    CurriculumTopic(
        id = "integers",
        title = "Integers",
        category = "Language Basics",
        recap = Recap(
            previousTopicTitle = "Variables",
            recapText = "Variables must be definitely initialized before use; a val can be assigned later but only once; lateinit defers var initialization.",
            quickCheckQuestion = "Can a val be assigned after its declaration line?",
            quickCheckAnswer = "Yes, but only once.",
        ),
        explain = "Kotlin has four signed integer types, distinguished by bit width and range: Byte (8-bit, -128..127), Short (16-bit), Int (32-bit, the default), and Long (64-bit, needs an L suffix for literals). Choose the smallest type that comfortably fits your data's range — Int is the default and right choice for most everyday counting and indexing.",
        example = """
            |fun main() {
            |    val small: Byte = 100
            |    val medium: Short = 30_000
            |    val standard: Int = 2_000_000_000
            |    val big: Long = 9_000_000_000L       // needs L — exceeds Int's range
            |
            |    println(Int.MAX_VALUE)                 // 2147483647
            |    println(standard + 1)                  // overflow wraps silently to a negative number!
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Int is the default and used for the vast majority of everyday integer values.",
            "Long literals need an L suffix or the compiler infers Int and may overflow.",
            "Integer overflow wraps around silently in Kotlin — it does not throw by default.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "integers-q1",
                topicId = "integers",
                question = "Which integer type is 64-bit?",
                options = listOf("Int", "Short", "Long", "Byte"),
                correctIndex = 2,
                explanation = "Long is the 64-bit signed integer type.",
            ),
            QuizQuestion(
                id = "integers-q2",
                topicId = "integers",
                question = "What happens when an Int calculation overflows its range?",
                options = listOf("It throws an exception", "It silently wraps around", "It auto-promotes to Long", "It rounds to Int.MAX_VALUE"),
                correctIndex = 1,
                explanation = "Kotlin integers wrap silently on overflow, matching JVM integer arithmetic — no exception is thrown.",
            ),
            QuizQuestion(
                id = "integers-q3",
                topicId = "integers",
                question = "Which type should you default to for everyday integer values?",
                options = listOf("Byte", "Short", "Int", "Long"),
                correctIndex = 2,
                explanation = "Int is the default integer type and fits the overwhelming majority of use cases.",
            ),
        ),
        tutorFocus = "The silent-overflow gotcha is worth a concrete demo (Int.MAX_VALUE + 1). Exercise: predict the output of an overflow expression, then run it to check.",
    ),
    CurriculumTopic(
        id = "unsigned-integers",
        title = "Unsigned Integers",
        category = "Language Basics",
        recap = Recap(
            previousTopicTitle = "Integers",
            recapText = "Byte/Short/Int/Long are signed integer types of increasing width; Int is the default; overflow wraps silently.",
            quickCheckQuestion = "What happens when an Int calculation overflows?",
            quickCheckAnswer = "It silently wraps around.",
        ),
        explain = "Kotlin's unsigned types — UByte, UShort, UInt, ULong — represent only non-negative values, doubling the positive range compared to their signed counterparts of the same width. Literals use a u (or uL for ULong) suffix. They're implemented as inline classes wrapping the signed type, so there's no runtime overhead, but they don't interoperate directly with signed arithmetic — conversions must be explicit.",
        example = """
            |fun main() {
            |    val port: UInt = 8080u
            |    val byteMax: UByte = 255u             // UByte range is 0..255
            |    val big: ULong = 18_000_000_000_000_000_000uL
            |
            |    println(UInt.MAX_VALUE)                 // 4294967295 — double Int's positive range
            |    // val mixed = port + 1                 // won't compile — must be UInt too
            |    println(port + 1u)
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Unsigned types trade negative range for double the positive range at the same bit width.",
            "Literals need a u (or uL) suffix: 8080u, 18_000_000_000uL.",
            "No implicit mixing with signed types — conversions between signed and unsigned must be explicit.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "unsigned-integers-q1",
                topicId = "unsigned-integers",
                question = "What values can a UInt hold?",
                options = listOf("Only 0 and positive values", "Any Int value", "Only negative values", "Only values 0-255"),
                correctIndex = 0,
                explanation = "Unsigned types exclude negative values entirely, doubling the usable positive range.",
            ),
            QuizQuestion(
                id = "unsigned-integers-q2",
                topicId = "unsigned-integers",
                question = "How do you write a UInt literal?",
                options = listOf("With a u suffix, e.g. 8080u", "With a # prefix", "No special syntax needed", "With an unsigned() wrapper"),
                correctIndex = 0,
                explanation = "The u suffix marks an integer literal as unsigned.",
            ),
            QuizQuestion(
                id = "unsigned-integers-q3",
                topicId = "unsigned-integers",
                question = "Can you add a UInt and an Int directly?",
                options = listOf("Yes, Kotlin converts automatically", "No, the conversion must be explicit", "Only if the Int is positive", "Only in unsafe mode"),
                correctIndex = 1,
                explanation = "Signed and unsigned types don't mix implicitly; you must convert one explicitly first.",
            ),
        ),
        tutorFocus = "Use case framing: ports, byte buffers, bit flags — places where negative values are meaningless. Exercise: model a network port as UInt and show why signed Int allows an invalid negative port number.",
    ),
    CurriculumTopic(
        id = "floats",
        title = "Floats",
        category = "Language Basics",
        recap = Recap(
            previousTopicTitle = "Unsigned Integers",
            recapText = "UByte/UShort/UInt/ULong hold only non-negative values; literals need a u/uL suffix; no implicit signed/unsigned mixing.",
            quickCheckQuestion = "How do you write a UInt literal?",
            quickCheckAnswer = "With a u suffix, e.g. 8080u.",
        ),
        explain = "Kotlin has two floating-point types: Float (32-bit, ~7 significant digits) and Double (64-bit, ~15-16 significant digits, the default for decimal literals). Float literals need an f suffix; without it, a decimal literal is always Double. Both follow the IEEE 754 standard, meaning they can't represent most decimal fractions exactly — a classic gotcha for equality comparisons.",
        example = """
            |fun main() {
            |    val price: Float = 9.99f
            |    val precise: Double = 9.99                 // Double is the default
            |
            |    println(0.1 + 0.2)                          // 0.30000000000000004 — floating-point imprecision
            |    println(0.1 + 0.2 == 0.3)                   // false!
            |
            |    val diff = kotlin.math.abs((0.1 + 0.2) - 0.3)
            |    println(diff < 1e-9)                         // true — compare with a tolerance instead
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Double is the default for decimal literals; Float needs an explicit f suffix.",
            "Never compare floating-point values with == directly — use a small tolerance (epsilon) instead.",
            "Use BigDecimal (from java.math) instead of Double/Float when exact decimal precision matters, e.g. money.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "floats-q1",
                topicId = "floats",
                question = "What type is inferred for the literal 9.99 with no suffix?",
                options = listOf("Float", "Double", "Int", "BigDecimal"),
                correctIndex = 1,
                explanation = "Decimal literals default to Double unless given an f suffix.",
            ),
            QuizQuestion(
                id = "floats-q2",
                topicId = "floats",
                question = "Why might 0.1 + 0.2 == 0.3 evaluate to false?",
                options = listOf("It's a Kotlin bug", "Floating-point numbers can't represent most decimals exactly", "0.1 and 0.2 are Int, not Double", "== is broken for Double"),
                correctIndex = 1,
                explanation = "IEEE 754 floating-point representation introduces tiny rounding errors, so exact equality often fails.",
            ),
            QuizQuestion(
                id = "floats-q3",
                topicId = "floats",
                question = "What should you use instead of Double when exact decimal precision matters, e.g. currency?",
                options = listOf("Float", "Long", "BigDecimal", "String"),
                correctIndex = 2,
                explanation = "java.math.BigDecimal represents decimal values exactly, avoiding floating-point rounding errors.",
            ),
        ),
        tutorFocus = "The 0.1 + 0.2 demo is the essential \"aha\" moment — always show it live. Exercise: write an epsilon-based comparison function and use it to safely compare two Doubles.",
    ),
    CurriculumTopic(
        id = "characters",
        title = "Characters",
        category = "Language Basics",
        recap = Recap(
            previousTopicTitle = "Floats",
            recapText = "Double is the default float type; never compare floats with ==; use BigDecimal for exact decimals like money.",
            quickCheckQuestion = "Why might 0.1 + 0.2 == 0.3 be false?",
            quickCheckAnswer = "Floating-point numbers can't represent most decimals exactly.",
        ),
        explain = "Char represents a single 16-bit Unicode character, written in single quotes: 'A'. Unlike Java, Kotlin's Char is not directly treated as a number — you can't do `'A' + 1` and get 'B' without an explicit conversion, though you can compare chars and use arithmetic-like operations via `.code` (its Unicode code point) or the `+`/`-` operators that Char does support for offsetting.",
        example = """
            |fun main() {
            |    val letter: Char = 'K'
            |    val newline = '\n'
            |    val unicodeHeart = '❤'
            |
            |    println(letter.isLetter())     // true
            |    println(letter.code)            // 75 — the Unicode code point
            |    println(letter + 1)             // 'L' — Char supports offsetting by Int
            |    println('9'.digitToInt())       // 9 — parses a digit character to its Int value
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Char literals use single quotes; String literals use double quotes — they are different types.",
            "Char is not implicitly a number, but supports + Int / - Int for offsetting, and .code for its Unicode value.",
            "Useful helpers: isLetter(), isDigit(), isWhitespace(), digitToInt().",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "characters-q1",
                topicId = "characters",
                question = "How do you write a Char literal?",
                options = listOf("Double quotes: \"A\"", "Single quotes: 'A'", "No quotes: A", "Backticks: `A`"),
                correctIndex = 1,
                explanation = "Char literals use single quotes; double quotes are reserved for String.",
            ),
            QuizQuestion(
                id = "characters-q2",
                topicId = "characters",
                question = "What does letter.code return for a Char?",
                options = listOf("Its Unicode code point as an Int", "Its String representation", "A Boolean", "Its ASCII art"),
                correctIndex = 0,
                explanation = ".code exposes the Char's underlying Unicode code point as an Int.",
            ),
            QuizQuestion(
                id = "characters-q3",
                topicId = "characters",
                question = "What does 'K' + 1 evaluate to?",
                options = listOf("A compile error", "'L'", "76", "\"K1\""),
                correctIndex = 1,
                explanation = "Char supports + Int to offset to another Char, so 'K' + 1 gives 'L'.",
            ),
        ),
        tutorFocus = "Contrast with Java/C where chars are more freely numeric. Exercise: write a function that shifts a letter by N positions (a simple Caesar cipher) using Char + Int.",
    ),
    CurriculumTopic(
        id = "strings",
        title = "Strings",
        category = "Language Basics",
        recap = Recap(
            previousTopicTitle = "Characters",
            recapText = "Char uses single quotes, isn't implicitly numeric, but supports + Int offsetting and .code for its Unicode value.",
            quickCheckQuestion = "What does 'K' + 1 evaluate to?",
            quickCheckAnswer = "'L'.",
        ),
        explain = "String is an immutable sequence of Char values. Kotlin strings support indexing (`s[0]`), iteration (`for (c in s)`), templates (`\"\$name\"`), and a large set of standard-library extension functions (trim, split, replace, uppercase, and many more). Because strings are immutable, every \"modifying\" operation (like .uppercase() or .trim()) returns a *new* String rather than mutating the original.",
        example = """
            |fun main() {
            |    val greeting = "Hello, Kotlin"
            |    println(greeting[0])                    // 'H' — indexable like an array
            |    println(greeting.length)                // 13
            |    println(greeting.uppercase())            // "HELLO, KOTLIN" — new string, greeting unchanged
            |    println(greeting.split(", "))            // [Hello, Kotlin]
            |    println(greeting.replace("Kotlin", "World"))
            |
            |    for (c in "abc") print("${'$'}c-")       // a-b-c-
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Strings are immutable — every transforming call returns a new String, the original is untouched.",
            "Strings are indexable and iterable, just like a read-only List<Char>.",
            "Prefer String templates over manual + concatenation for readability.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "strings-q1",
                topicId = "strings",
                question = "Does greeting.uppercase() modify the original String?",
                options = listOf("Yes, in place", "No — it returns a new String", "Only if greeting is a var", "Only in Kotlin 2.0+"),
                correctIndex = 1,
                explanation = "Strings are immutable in Kotlin; transforming functions always return a new String.",
            ),
            QuizQuestion(
                id = "strings-q2",
                topicId = "strings",
                question = "How do you access the first character of a String s?",
                options = listOf("s.first", "s[0]", "s.get()", "s.charAt"),
                correctIndex = 1,
                explanation = "Strings support index access with square brackets, just like an array.",
            ),
            QuizQuestion(
                id = "strings-q3",
                topicId = "strings",
                question = "What does \"a,b,c\".split(\",\") return?",
                options = listOf("A single String \"abc\"", "A List: [a, b, c]", "A Char array", "An error, split needs a Regex"),
                correctIndex = 1,
                explanation = "split() divides a String on the given delimiter and returns a List<String>.",
            ),
        ),
        tutorFocus = "Reinforce immutability — a common bug is calling .trim() and expecting the original variable to change. Exercise: chain several String transformations (trim, lowercase, replace) into one readable expression.",
    ),
    CurriculumTopic(
        id = "booleans",
        title = "Booleans",
        category = "Language Basics",
        recap = Recap(
            previousTopicTitle = "Strings",
            recapText = "Strings are immutable, indexable, and iterable; transforming calls return new Strings.",
            quickCheckQuestion = "Does greeting.uppercase() modify the original?",
            quickCheckAnswer = "No — it returns a new String.",
        ),
        explain = "Boolean has exactly two values: true and false. Kotlin provides the standard logical operators — && (and), || (or), ! (not) — both of which are short-circuiting (the right-hand side isn't evaluated if the left side already determines the result). Comparison operators (==, !=, <, >, <=, >=) all produce Boolean results.",
        example = """
            |fun expensiveCheck(): Boolean {
            |    println("expensiveCheck ran")
            |    return true
            |}
            |
            |fun main() {
            |    val a = true
            |    val b = false
            |    println(a && b)                       // false
            |    println(a || b)                        // true
            |    println(!a)                             // false
            |
            |    if (false && expensiveCheck()) {}       // expensiveCheck never runs — short-circuit
            |    println("done")
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "&& and || short-circuit: the right side only evaluates if needed to determine the result.",
            "Comparison operators (==, <, >, etc.) always produce a Boolean.",
            "Short-circuiting matters for performance and for safely guarding against side effects (e.g. null checks before use).",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "booleans-q1",
                topicId = "booleans",
                question = "In false && expensiveCheck(), does expensiveCheck() run?",
                options = listOf("Yes, always", "No — && short-circuits once the left side is false", "Only if expensiveCheck() is inline", "It runs but its result is ignored"),
                correctIndex = 1,
                explanation = "&& short-circuits: if the left operand is false, the whole expression is already false, so the right side is skipped.",
            ),
            QuizQuestion(
                id = "booleans-q2",
                topicId = "booleans",
                question = "What does the ! operator do?",
                options = listOf("Logical AND", "Logical OR", "Logical NOT", "Not-null assertion only"),
                correctIndex = 2,
                explanation = "! negates a Boolean value (note: !! is the unrelated not-null assertion operator on nullable types).",
            ),
            QuizQuestion(
                id = "booleans-q3",
                topicId = "booleans",
                question = "What type does a comparison like x > 5 produce?",
                options = listOf("Int", "String", "Boolean", "Unit"),
                correctIndex = 2,
                explanation = "All comparison operators produce a Boolean result: true or false.",
            ),
        ),
        tutorFocus = "Short-circuiting is the key mechanic to drill — show the expensiveCheck() example live. Exercise: write a guard condition that uses short-circuiting to avoid a null-pointer-style error, e.g. list.isNotEmpty() && list[0] > 0.",
    ),
    CurriculumTopic(
        id = "arrays",
        title = "Arrays",
        category = "Language Basics",
        recap = Recap(
            previousTopicTitle = "Booleans",
            recapText = "&&/|| short-circuit; ! negates; comparisons produce Boolean.",
            quickCheckQuestion = "Does expensiveCheck() run in false && expensiveCheck()?",
            quickCheckAnswer = "No — && short-circuits.",
        ),
        explain = "Array<T> is a fixed-size, mutable collection of a single element type, created with arrayOf(...) or Array(size) { init }. Kotlin also has specialized primitive array types (IntArray, DoubleArray, BooleanArray, etc.) that avoid boxing overhead — arrayOf(1, 2, 3) creates an Array<Int> (boxed), while intArrayOf(1, 2, 3) creates an IntArray (unboxed primitives), which is more efficient for large numeric datasets. In everyday code, prefer List/MutableList over Array unless you specifically need fixed size or primitive performance.",
        example = """
            |fun main() {
            |    val names = arrayOf("Ada", "Grace", "Linus")
            |    names[0] = "Katherine"                      // arrays are mutable in place
            |    println(names.joinToString())
            |
            |    val nums = IntArray(5) { it * it }           // [0, 1, 4, 9, 16] — primitive, unboxed
            |    println(nums.sum())
            |
            |    // names.add("x")                            // won't compile — Array has no add(), it's fixed-size
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Arrays are fixed-size but mutable in place — you can change elements, not grow or shrink the array.",
            "Use IntArray/DoubleArray/etc. for primitive numeric data to avoid boxing overhead.",
            "Prefer List/MutableList in everyday code; reach for Array mainly for interop or fixed-size performance-sensitive cases.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "arrays-q1",
                topicId = "arrays",
                question = "Can you add a new element to an existing Array?",
                options = listOf("Yes, with .add()", "No — arrays are fixed-size", "Only if declared with var", "Only for IntArray"),
                correctIndex = 1,
                explanation = "Arrays have a fixed size set at creation; you can change elements but not grow or shrink the array.",
            ),
            QuizQuestion(
                id = "arrays-q2",
                topicId = "arrays",
                question = "Why use IntArray instead of Array<Int>?",
                options = listOf("IntArray is resizable, Array<Int> isn't", "IntArray stores unboxed primitives, avoiding boxing overhead", "They are identical", "Array<Int> can't hold negative numbers"),
                correctIndex = 1,
                explanation = "IntArray stores raw primitive ints without boxing each value into an Integer object, which is more memory-efficient.",
            ),
            QuizQuestion(
                id = "arrays-q3",
                topicId = "arrays",
                question = "What's generally recommended for everyday collection needs?",
                options = listOf("Always use Array", "Prefer List/MutableList unless you need fixed size or primitive performance", "Never use Array", "Use Array only for Strings"),
                correctIndex = 1,
                explanation = "List/MutableList are more flexible for everyday code; Array is reserved for specific fixed-size or performance needs.",
            ),
        ),
        tutorFocus = "Clarify \"fixed-size\" vs \"immutable\" — arrays are the former, not the latter. Exercise: create an IntArray of squares using the Array(size){init} constructor, then try (and fail) to add an element to show the fixed-size constraint.",
    ),
)
