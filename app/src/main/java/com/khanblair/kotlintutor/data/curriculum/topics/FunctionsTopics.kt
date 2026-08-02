package com.khanblair.kotlintutor.data.curriculum.topics

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap

val functionsTopics: List<CurriculumTopic> = listOf(
    CurriculumTopic(
        id = "functions-basics",
        title = "Functions & Parameters",
        category = "Functions",
        recap = Recap(
            previousTopicTitle = "Exceptions",
            recapText = "No checked exceptions; try is an expression; use require/check for validation.",
            quickCheckQuestion = "Does Kotlin force try/catch? What does require throw?",
            quickCheckAnswer = "no; IllegalArgumentException.",
        ),
        explain = "Functions are declared with fun. Parameters are name: Type, and the return type follows the parameter list (: Type). A function returning nothing returns Unit (which you can omit). Single-expression functions use = instead of a block. Kotlin supports default parameter values and named arguments, which together remove the need for many overloads. vararg lets a function accept any number of arguments.",
        example = """
            |fun greet(name: String, greeting: String = "Hello"): String =
            |    "${'$'}greeting, ${'$'}name!"                       // default value + single-expression
            |
            |fun sum(vararg numbers: Int): Int = numbers.sum()   // vararg
            |
            |fun main() {
            |    println(greet("Ada"))                     // uses default → "Hello, Ada!"
            |    println(greet("Ada", greeting = "Hi"))    // named argument
            |    println(sum(1, 2, 3, 4))                  // 10
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Named arguments make calls self-documenting and let you skip earlier defaults.",
            "Default values reduce overloads dramatically.",
            "Only one vararg per function; spread an existing array with *array.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "functions-basics-q1",
                topicId = "functions-basics",
                question = "What type does a function with no explicit return type return?",
                options = listOf("Nothing", "Unit", "Void", "Any?"),
                correctIndex = 1,
                explanation = "Functions with no declared return type implicitly return Unit.",
            ),
            QuizQuestion(
                id = "functions-basics-q2",
                topicId = "functions-basics",
                question = "What do default parameter values let you avoid?",
                options = listOf(
                    "Writing multiple overloads",
                    "Using named arguments",
                    "Declaring a return type",
                    "Using vararg",
                ),
                correctIndex = 0,
                explanation = "Default values mean callers can omit arguments instead of you writing separate overloads.",
            ),
            QuizQuestion(
                id = "functions-basics-q3",
                topicId = "functions-basics",
                question = "How do you pass an existing array to a vararg parameter?",
                options = listOf(
                    "Pass it directly, no special syntax",
                    "Spread it with *array",
                    "Wrap it in listOf(array)",
                    "It's not possible",
                ),
                correctIndex = 1,
                explanation = "The spread operator * expands an array's elements into vararg positions.",
            ),
        ),
        tutorFocus = "Show how default + named args replace overloads. Probe the Unit return concept. Exercise: write one function with defaults that would otherwise require three Java-style overloads, and call it three different ways.",
    ),
    CurriculumTopic(
        id = "lambda-functions",
        title = "Lambda Functions",
        category = "Functions",
        recap = Recap(
            previousTopicTitle = "Functions & Parameters",
            recapText = "fun with typed params; default + named args replace overloads; vararg accepts many args.",
            quickCheckQuestion = "Return type when none is given? Pass an array to a vararg?",
            quickCheckAnswer = "Unit; spread it with *array.",
        ),
        explain = "A lambda is an anonymous function literal written in braces: { x -> x * 2 }. Lambdas are values you can pass around. When a lambda is the last argument to a function, you can move it outside the parentheses (trailing lambda syntax). A single-parameter lambda can use the implicit name it.",
        example = """
            |fun main() {
            |    val double = { x: Int -> x * 2 }
            |    println(double(21))                       // 42
            |
            |    val nums = listOf(1, 2, 3, 4)
            |    println(nums.filter { it % 2 == 0 })      // trailing lambda + it → [2, 4]
            |    println(nums.map { n -> n * n })          // explicit param → [1, 4, 9, 16]
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "it only exists for single-parameter lambdas; name the parameter when it aids clarity or when nesting lambdas.",
            "The last expression in a lambda is its return value (no return keyword).",
            "Trailing-lambda syntax is why filter { … } reads so cleanly.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "lambda-functions-q1",
                topicId = "lambda-functions",
                question = "What is it in a lambda?",
                options = listOf(
                    "A reserved keyword for the current class",
                    "The implicit name of a single lambda parameter",
                    "The return value of the lambda",
                    "A reference to the enclosing function",
                ),
                correctIndex = 1,
                explanation = "it is the implicit name Kotlin gives a lambda's single parameter when you don't name it.",
            ),
            QuizQuestion(
                id = "lambda-functions-q2",
                topicId = "lambda-functions",
                question = "What does a lambda return?",
                options = listOf(
                    "Always Unit",
                    "The value of its last expression",
                    "Whatever is passed to a return statement, always",
                    "Nothing — lambdas can't return values",
                ),
                correctIndex = 1,
                explanation = "A lambda's result is the value of its final expression.",
            ),
            QuizQuestion(
                id = "lambda-functions-q3",
                topicId = "lambda-functions",
                question = "What is trailing-lambda syntax?",
                options = listOf(
                    "Writing the lambda before the function call",
                    "Moving a final lambda argument outside the call's parentheses",
                    "A lambda with no parameters",
                    "A lambda that returns another lambda",
                ),
                correctIndex = 1,
                explanation = "When a lambda is the last parameter, Kotlin lets you write it outside the parentheses, e.g. filter { ... }.",
            ),
        ),
        tutorFocus = "Lambdas unlock the collection API, so nail the syntax and it. Probe the \"last expression is the result\" rule. Exercise: use map and filter with lambdas to transform a list, first with it, then with a named parameter.",
    ),
    CurriculumTopic(
        id = "higher-order-functions",
        title = "Higher-order Functions",
        category = "Functions",
        recap = Recap(
            previousTopicTitle = "Lambda Functions",
            recapText = "Lambdas are { x -> … } values; single param is it; the last expression is the result.",
            quickCheckQuestion = "What is it? What does a lambda return?",
            quickCheckAnswer = "the single parameter; its last expression.",
        ),
        explain = "A higher-order function takes a function as a parameter or returns one. The type of a function value is written (A, B) -> R. This is the foundation of Kotlin's expressive collection and coroutine APIs. You can pass a lambda, an anonymous function, or a function reference (::name).",
        example = """
            |fun applyTwice(x: Int, op: (Int) -> Int): Int = op(op(x))   // takes a function
            |
            |fun makeAdder(amount: Int): (Int) -> Int = { it + amount }  // returns a function
            |
            |fun main() {
            |    println(applyTwice(5) { it + 1 })     // op(op(5)) = 7
            |    println(applyTwice(5, ::square))      // square(square(5)) = 625
            |    val add10 = makeAdder(10)
            |    println(add10(32))                    // 42
            |}
            |
            |fun square(n: Int) = n * n
        """.trimMargin(),
        keyPoints = listOf(
            "Function type syntax: (params) -> ReturnType.",
            "::functionName is a reference to an existing function you can pass around.",
            "Returning functions enables factories and configurable behavior.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "higher-order-functions-q1",
                topicId = "higher-order-functions",
                question = "What makes a function \"higher-order\"?",
                options = listOf(
                    "It's declared at the top level of a file",
                    "It takes and/or returns a function",
                    "It has more than 3 parameters",
                    "It's marked with the inline keyword",
                ),
                correctIndex = 1,
                explanation = "A higher-order function accepts a function as a parameter, returns one, or both.",
            ),
            QuizQuestion(
                id = "higher-order-functions-q2",
                topicId = "higher-order-functions",
                question = "How do you write the type of a function that takes an Int and returns a String?",
                options = listOf("Int -> String", "(Int) -> String", "fun(Int): String", "Function<Int, String>"),
                correctIndex = 1,
                explanation = "Function types are written as (ParamTypes) -> ReturnType.",
            ),
            QuizQuestion(
                id = "higher-order-functions-q3",
                topicId = "higher-order-functions",
                question = "What does ::square mean?",
                options = listOf(
                    "It squares the following number",
                    "A reference to the existing square function, usable as a value",
                    "It declares a new function named square",
                    "It's shorthand for a lambda that squares its input",
                ),
                correctIndex = 1,
                explanation = "The :: operator creates a callable reference to an existing function.",
            ),
        ),
        tutorFocus = "This is where function types click. Draw the (Int) -> Int type explicitly. Exercise: write a higher-order function that takes an operation and applies it to a list, then pass it different lambdas and a :: reference.",
    ),
    CurriculumTopic(
        id = "extension-functions",
        title = "Extension Functions",
        category = "Functions",
        recap = Recap(
            previousTopicTitle = "Higher-order Functions",
            recapText = "Functions that take or return functions; type is (A) -> R; pass lambdas or ::refs.",
            quickCheckQuestion = "Type of an Int→String function? What is ::square?",
            quickCheckAnswer = "(Int) -> String; a reference to square.",
        ),
        explain = "Extension functions let you add new functions to an existing type without inheriting from it or modifying its source. You write the receiver type before the function name: fun String.shout() = uppercase() + \"!\". Inside, this refers to the receiver. Extensions are resolved statically (they don't actually modify the class), and they're everywhere in Kotlin's standard library.",
        example = """
            |fun String.shout(): String = this.uppercase() + "!"
            |
            |fun List<Int>.secondOrNull(): Int? = if (size >= 2) this[1] else null
            |
            |fun main() {
            |    println("hello".shout())              // HELLO!
            |    println(listOf(10, 20, 30).secondOrNull())  // 20
            |    println(listOf(10).secondOrNull())    // null
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Extensions are static, not polymorphic — they don't override member functions. If a member and an extension clash, the member wins.",
            "They can't access private members of the type they extend.",
            "Great for readability and for adding helpers to types you don't own.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "extension-functions-q1",
                topicId = "extension-functions",
                question = "What does an extension function let you do?",
                options = listOf(
                    "Modify a compiled class file directly",
                    "Add a function to an existing type without modifying or subclassing it",
                    "Override any member function of a final class",
                    "Add new properties with backing fields to any class",
                ),
                correctIndex = 1,
                explanation = "Extension functions add callable functions to a type without touching its source or requiring subclassing.",
            ),
            QuizQuestion(
                id = "extension-functions-q2",
                topicId = "extension-functions",
                question = "What does this refer to inside an extension function?",
                options = listOf(
                    "The file the extension is declared in",
                    "The receiver — the instance the function is called on",
                    "The caller's enclosing class",
                    "Nothing — this isn't available in extensions",
                ),
                correctIndex = 1,
                explanation = "Inside an extension, this is the receiver instance the extension was called on.",
            ),
            QuizQuestion(
                id = "extension-functions-q3",
                topicId = "extension-functions",
                question = "If a class has a member function and an extension with the same signature, which is called?",
                options = listOf(
                    "The extension always wins",
                    "The member always wins",
                    "It's a compile error",
                    "Whichever was declared last in the file",
                ),
                correctIndex = 1,
                explanation = "Members always take priority over extension functions with the same signature.",
            ),
        ),
        tutorFocus = "Extensions feel magical — clarify that they're static sugar, not real modification. Probe the member-wins rule. Exercise: add an extension like Int.isEven() or String.wordCount() and use it fluently.",
    ),
    CurriculumTopic(
        id = "standard-functions",
        title = "Standard (scope) Functions",
        category = "Functions",
        recap = Recap(
            previousTopicTitle = "Extension Functions",
            recapText = "Add functions to existing types via a receiver; they're static, so members win over extensions.",
            quickCheckQuestion = "What is this in an extension? Member vs extension clash — who wins?",
            quickCheckAnswer = "the receiver; the member.",
        ),
        explain = "The standard library provides five scope functions that run a block in the context of an object: let, run, with, apply, and also. They differ in how they refer to the object (it vs this) and what they return (the object vs the lambda result):\n\n" +
            "- let — object as it, returns lambda result. Great for null-safe chains.\n" +
            "- run — object as this, returns lambda result.\n" +
            "- with — like run but called as with(obj) { … }.\n" +
            "- apply — object as this, returns the object. Ideal for configuration.\n" +
            "- also — object as it, returns the object. Ideal for side effects.",
        example = """
            |data class Config(var host: String = "", var port: Int = 0)
            |
            |fun main() {
            |    val name: String? = "ada"
            |    name?.let { println("Name is ${'$'}it") }          // runs only if non-null
            |
            |    val config = Config().apply {                  // configure, return the object
            |        host = "localhost"
            |        port = 8080
            |    }
            |    println(config)
            |
            |    val length = "hello".run { length }            // this = "hello", returns Int
            |    println(length)                                // 5
            |}
        """.trimMargin(),
        keyPoints = listOf(
            "Choose by two questions: do you want it or this, and do you want the object back or the lambda result?",
            "apply for setup, also for logging/side effects, let for null-safe transforms.",
            "Overusing scope functions can hurt readability — use them where they clarify.",
        ),
        quiz = listOf(
            QuizQuestion(
                id = "standard-functions-q1",
                topicId = "standard-functions",
                question = "Which scope functions return the object itself?",
                options = listOf("let and run", "apply and also", "with and let", "run and with"),
                correctIndex = 1,
                explanation = "apply and also both return the receiver object, not the lambda's result.",
            ),
            QuizQuestion(
                id = "standard-functions-q2",
                topicId = "standard-functions",
                question = "Which is idiomatic for configuring an object right after creation?",
                options = listOf("let", "run", "apply", "also"),
                correctIndex = 2,
                explanation = "apply is the standard choice for configuration blocks since it returns the configured object.",
            ),
            QuizQuestion(
                id = "standard-functions-q3",
                topicId = "standard-functions",
                question = "Which is commonly used for null-safe execution of a block?",
                options = listOf("also (with ?.also)", "let (with ?.let)", "with", "apply"),
                correctIndex = 1,
                explanation = "?.let { ... } is the idiomatic way to run a block only when a value is non-null.",
            ),
        ),
        tutorFocus = "Give the two-question decision rule (it/this? object/result?) rather than rote memorization. Exercise: rewrite an object-configuration snippet using apply, and a null-check using ?.let.",
    ),
)
