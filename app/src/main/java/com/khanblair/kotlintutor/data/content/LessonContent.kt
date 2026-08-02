package com.khanblair.kotlintutor.data.content

import com.khanblair.kotlintutor.model.Lesson

/** Authored lesson content for the v1 core topics (see RoadmapContent.CONTENT_TOPIC_IDS). */
object LessonContent {
    val lessons: List<Lesson> = listOf(
        Lesson(
            topicId = "val-vs-var",
            title = "val vs var",
            sections = listOf(
                "`val` declares a read-only reference — you can only assign to it once, " +
                    "at the point of declaration. `var` declares a mutable reference that can " +
                    "be reassigned as many times as you like.",
                "Prefer `val` by default. It doesn't make the object itself immutable (a " +
                    "`val` list can still have its contents changed if the list type is " +
                    "mutable) — it only prevents reassigning the reference itself.",
                "Example:\nval name = \"Kotlin\"    // cannot reassign\nvar count = 0           // can reassign\ncount = count + 1",
            ),
        ),
        Lesson(
            topicId = "data-types",
            title = "Data Types",
            sections = listOf(
                "Kotlin has built-in types for numbers (Int, Long, Short, Byte, Double, " +
                    "Float), text (String, Char), and true/false values (Boolean). Numeric " +
                    "types also have unsigned variants (UInt, ULong, UShort, UByte).",
                "Every variable has a type, but Kotlin usually infers it from the assigned " +
                    "value, so you rarely need to write it explicitly: val age = 30 infers " +
                    "Int, val price = 9.99 infers Double.",
                "These are full types with member functions, not primitives you box " +
                    "manually — e.g. 42.toString() or \"5\".toInt() both work directly.",
            ),
        ),
        Lesson(
            topicId = "conditional-expressions",
            title = "Conditional Expressions: if / when",
            sections = listOf(
                "In Kotlin, `if` is an expression, not just a statement — it can return a " +
                    "value: val max = if (a > b) a else b.",
                "`when` is Kotlin's flexible replacement for switch statements. It can match " +
                    "exact values, ranges, types, or arbitrary conditions, and — like `if` — " +
                    "can be used as an expression.",
                "Example:\nwhen (x) {\n    1 -> println(\"one\")\n    in 2..10 -> println(\"two to ten\")\n    else -> println(\"other\")\n}",
            ),
        ),
        Lesson(
            topicId = "loops",
            title = "Loops: for / while",
            sections = listOf(
                "`for` loops iterate over anything that provides an iterator: ranges, " +
                    "arrays, collections. for (i in 1..5) { println(i) } prints 1 through 5 " +
                    "inclusive.",
                "`while` and `do-while` loop based on a condition checked before (while) or " +
                    "after (do-while) the loop body runs at least once.",
                "Use `break` to exit a loop early and `continue` to skip to the next " +
                    "iteration. Labeled loops (loop@ for ...) let break/continue target an " +
                    "outer loop from a nested one.",
            ),
        ),
        Lesson(
            topicId = "functions-basics",
            title = "Function Parameters & Return",
            sections = listOf(
                "Functions are declared with `fun`. Parameters have a name and type, and " +
                    "can have default values: fun greet(name: String = \"World\") = " +
                    "\"Hello, ${'$'}name!\".",
                "A function's return type follows the parameter list after a colon. If the " +
                    "body is a single expression, you can omit the braces and use `=`: " +
                    "fun square(x: Int): Int = x * x.",
                "Calling with named arguments makes call sites clearer and lets you skip " +
                    "defaulted parameters in any order: greet(name = \"Kotlin\").",
            ),
        ),
        Lesson(
            topicId = "lists-sets-maps",
            title = "Lists, Sets, Maps",
            sections = listOf(
                "List preserves order and allows duplicates: listOf(1, 2, 2, 3). Set stores " +
                    "unique elements: setOf(1, 2, 2, 3) has 3 elements. Map stores key-value " +
                    "pairs: mapOf(\"a\" to 1, \"b\" to 2).",
                "listOf/setOf/mapOf create read-only collections; mutableListOf/" +
                    "mutableSetOf/mutableMapOf create ones you can add to and remove from " +
                    "after creation.",
                "All three are iterable and support the same rich set of collection " +
                    "operations (map, filter, etc.) covered later in the roadmap under " +
                    "Collection Operations.",
            ),
        ),
        Lesson(
            topicId = "defining-classes",
            title = "Defining Classes",
            sections = listOf(
                "A class is declared with `class Name { ... }`. Properties and constructor " +
                    "parameters can be combined in the primary constructor: class " +
                    "Person(val name: String, var age: Int).",
                "Creating an instance doesn't require `new`: val p = Person(\"Ada\", 30). " +
                    "Properties are accessed directly: p.name, and mutable ones can be " +
                    "reassigned: p.age = 31.",
                "Class bodies can define additional properties, methods, secondary " +
                    "constructors, and initializer blocks (`init { ... }`) that run when an " +
                    "instance is created.",
            ),
        ),
        Lesson(
            topicId = "what-is-null-safety",
            title = "What is Null Safety?",
            sections = listOf(
                "Kotlin's type system distinguishes types that can hold null (String?) from " +
                    "types that cannot (String). Assigning null to a non-nullable type is a " +
                    "compile error, catching a whole class of NullPointerExceptions before " +
                    "the app ever runs.",
                "To work with a nullable value you must handle the null case explicitly — " +
                    "with a safe call (?.), the Elvis operator (?:), a null check, or " +
                    "(rarely) the not-null assertion (!!) which throws if the value is " +
                    "actually null.",
                "Example:\nval name: String? = null\nval length = name?.length ?: 0   // 0 if name is null, otherwise name.length",
            ),
        ),
    )
}
