package com.khanblair.kotlintutor.data.content

import com.khanblair.kotlintutor.model.QuizQuestion

/** Authored quiz questions for the v1 core topics (see RoadmapContent.CONTENT_TOPIC_IDS). */
object QuizContent {
    val questions: List<QuizQuestion> = listOf(
        // val vs var
        QuizQuestion(
            id = "val-vs-var-q1",
            topicId = "val-vs-var",
            question = "What happens if you try to reassign a val after it's initialized?",
            options = listOf(
                "It silently keeps the old value",
                "It compiles but throws at runtime",
                "It fails to compile",
                "It reassigns successfully",
            ),
            correctIndex = 2,
            explanation = "val is a compile-time constraint — reassigning one is a compile error, not a runtime error.",
        ),
        QuizQuestion(
            id = "val-vs-var-q2",
            topicId = "val-vs-var",
            question = "Which statement about val is true?",
            options = listOf(
                "A val list can never have elements added to it",
                "A val reference can't be reassigned, but if it points to a mutable collection, that collection's contents can still change",
                "val and var are identical at runtime",
                "val variables must be initialized to a compile-time constant",
            ),
            correctIndex = 1,
            explanation = "val only fixes the reference itself, not the mutability of the object it points to.",
        ),
        QuizQuestion(
            id = "val-vs-var-q3",
            topicId = "val-vs-var",
            question = "Which is the recommended default in idiomatic Kotlin?",
            options = listOf(
                "var, because it's more flexible",
                "val, unless you have a specific need to reassign",
                "It doesn't matter",
                "var, because val is deprecated",
            ),
            correctIndex = 1,
            explanation = "Prefer val by default; reach for var only when reassignment is actually needed.",
        ),

        // data types
        QuizQuestion(
            id = "data-types-q1",
            topicId = "data-types",
            question = "What type does Kotlin infer for val pi = 3.14?",
            options = listOf("Int", "Float", "Double", "String"),
            correctIndex = 2,
            explanation = "Decimal literals without an 'f' suffix default to Double.",
        ),
        QuizQuestion(
            id = "data-types-q2",
            topicId = "data-types",
            question = "Which type suits a value that should never be negative and needs a 32-bit range?",
            options = listOf("Int", "UInt", "Long", "Byte"),
            correctIndex = 1,
            explanation = "UInt is the unsigned 32-bit integer type — never negative, same width as Int.",
        ),
        QuizQuestion(
            id = "data-types-q3",
            topicId = "data-types",
            question = "How do you convert the String \"42\" into an Int in Kotlin?",
            options = listOf("\"42\".toInt()", "Int(\"42\")", "(Int) \"42\"", "parseInt(\"42\")"),
            correctIndex = 0,
            explanation = "String has a toInt() member function for this conversion.",
        ),

        // conditional expressions
        QuizQuestion(
            id = "conditional-expressions-q1",
            topicId = "conditional-expressions",
            question = "What does this return? val result = if (5 > 3) \"yes\" else \"no\"",
            options = listOf("Nothing, if isn't an expression", "\"yes\"", "\"no\"", "A compile error"),
            correctIndex = 1,
            explanation = "if is an expression in Kotlin, so it evaluates to the chosen branch's value.",
        ),
        QuizQuestion(
            id = "conditional-expressions-q2",
            topicId = "conditional-expressions",
            question = "When using when as a value-producing expression, what's required?",
            options = listOf(
                "Nothing extra",
                "An else branch, unless all cases are already covered (e.g. a sealed type or Boolean)",
                "A default branch",
                "At least 3 branches",
            ),
            correctIndex = 1,
            explanation = "The compiler must be able to prove the when is exhaustive to use it as an expression.",
        ),
        QuizQuestion(
            id = "conditional-expressions-q3",
            topicId = "conditional-expressions",
            question = "Which when branch matches a value between 2 and 10 inclusive?",
            options = listOf("2 to 10 ->", "in 2..10 ->", "range(2,10) ->", "2-10 ->"),
            correctIndex = 1,
            explanation = "`in 2..10 ->` matches any value in that inclusive range.",
        ),

        // loops
        QuizQuestion(
            id = "loops-q1",
            topicId = "loops",
            question = "What does 1..5 produce when used in a for loop?",
            options = listOf("0,1,2,3,4", "1,2,3,4", "1,2,3,4,5", "5,4,3,2,1"),
            correctIndex = 2,
            explanation = "1..5 is an inclusive range, so it yields 1 through 5.",
        ),
        QuizQuestion(
            id = "loops-q2",
            topicId = "loops",
            question = "What's the key difference between while and do-while?",
            options = listOf(
                "do-while always runs the body at least once; while may not run it at all",
                "They are identical",
                "while runs faster",
                "do-while can't use break",
            ),
            correctIndex = 0,
            explanation = "do-while checks its condition after the body runs, so the body always executes once.",
        ),
        QuizQuestion(
            id = "loops-q3",
            topicId = "loops",
            question = "Which keyword skips the rest of the current iteration and moves to the next one?",
            options = listOf("break", "continue", "return", "skip"),
            correctIndex = 1,
            explanation = "continue jumps to the next iteration; break exits the loop entirely.",
        ),

        // functions basics
        QuizQuestion(
            id = "functions-basics-q1",
            topicId = "functions-basics",
            question = "What does fun greet(name: String = \"World\") = \"Hello, ${'$'}name!\" return when called as greet()?",
            options = listOf("\"Hello, !\"", "\"Hello, World!\"", "A compile error, no argument passed", "null"),
            correctIndex = 1,
            explanation = "The default parameter value \"World\" is used when no argument is supplied.",
        ),
        QuizQuestion(
            id = "functions-basics-q2",
            topicId = "functions-basics",
            question = "Which syntax defines a function whose body is a single expression?",
            options = listOf("fun f(): Int { return 1 }", "fun f() = 1", "fun f() -> 1", "fun f(): Int => 1"),
            correctIndex = 1,
            explanation = "The = form assigns the function's body directly to an expression.",
        ),
        QuizQuestion(
            id = "functions-basics-q3",
            topicId = "functions-basics",
            question = "What are named arguments used for?",
            options = listOf(
                "Naming the function itself",
                "Passing arguments by parameter name instead of position",
                "Declaring private functions",
                "Renaming imports",
            ),
            correctIndex = 1,
            explanation = "Named arguments let you specify which parameter you're passing to, in any order.",
        ),

        // lists sets maps
        QuizQuestion(
            id = "lists-sets-maps-q1",
            topicId = "lists-sets-maps",
            question = "How many elements does setOf(1, 2, 2, 3) contain?",
            options = listOf("4", "3", "2", "1"),
            correctIndex = 1,
            explanation = "Sets discard duplicates, so setOf(1, 2, 2, 3) has 3 unique elements.",
        ),
        QuizQuestion(
            id = "lists-sets-maps-q2",
            topicId = "lists-sets-maps",
            question = "Which function creates a collection you can add elements to after creation?",
            options = listOf("listOf", "mutableListOf", "setOf", "mapOf"),
            correctIndex = 1,
            explanation = "mutableListOf returns a MutableList, which supports add/remove.",
        ),
        QuizQuestion(
            id = "lists-sets-maps-q3",
            topicId = "lists-sets-maps",
            question = "How do you create a Map with \"a\" mapped to 1?",
            options = listOf("mapOf(\"a\", 1)", "mapOf(\"a\" to 1)", "mapOf(\"a\" = 1)", "Map(\"a\", 1)"),
            correctIndex = 1,
            explanation = "The `to` infix function creates a Pair, which mapOf accepts as key-value entries.",
        ),

        // defining classes
        QuizQuestion(
            id = "defining-classes-q1",
            topicId = "defining-classes",
            question = "How do you create an instance of class Person(val name: String)?",
            options = listOf("new Person(\"Ada\")", "Person(\"Ada\")", "Person.new(\"Ada\")", "create Person(\"Ada\")"),
            correctIndex = 1,
            explanation = "Kotlin doesn't use the `new` keyword — calling the class name like a function constructs an instance.",
        ),
        QuizQuestion(
            id = "defining-classes-q2",
            topicId = "defining-classes",
            question = "In class Person(val name: String, var age: Int), which property can be reassigned after creation?",
            options = listOf("name", "age", "Both", "Neither"),
            correctIndex = 1,
            explanation = "age is declared with var, so it can be reassigned; name is val and cannot.",
        ),
        QuizQuestion(
            id = "defining-classes-q3",
            topicId = "defining-classes",
            question = "What runs automatically when an instance is created, in declaration order?",
            options = listOf(
                "Only the primary constructor parameters are set, nothing else runs",
                "init blocks and property initializers, in the order they appear in the class body",
                "Only functions named init()",
                "Nothing runs until you call a method",
            ),
            correctIndex = 1,
            explanation = "Property initializers and init blocks run in the order they're written, as part of construction.",
        ),

        // null safety
        QuizQuestion(
            id = "what-is-null-safety-q1",
            topicId = "what-is-null-safety",
            question = "What's the difference between String and String??",
            options = listOf(
                "No difference, just style",
                "String? can hold null, plain String cannot",
                "String? is slower",
                "String? is for Java interop only",
            ),
            correctIndex = 1,
            explanation = "The ? suffix marks a type as nullable; without it, null is a compile error.",
        ),
        QuizQuestion(
            id = "what-is-null-safety-q2",
            topicId = "what-is-null-safety",
            question = "What does name?.length evaluate to when name is null?",
            options = listOf("Throws a NullPointerException", "0", "null", "Compile error"),
            correctIndex = 2,
            explanation = "The safe call operator ?. short-circuits to null instead of throwing when the receiver is null.",
        ),
        QuizQuestion(
            id = "what-is-null-safety-q3",
            topicId = "what-is-null-safety",
            question = "What does the !! operator do?",
            options = listOf(
                "Safely returns null if the value is null",
                "Asserts the value is not null, throwing NullPointerException if it actually is",
                "Converts a nullable type to non-nullable safely",
                "Nothing, it's a typo for !",
            ),
            correctIndex = 1,
            explanation = "!! is the not-null assertion — it throws NullPointerException if the value turns out to be null.",
        ),
    )
}
